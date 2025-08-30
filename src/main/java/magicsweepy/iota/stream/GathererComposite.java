package magicsweepy.iota.stream;

import magicsweepy.iota.util.Checks;
import org.jspecify.annotations.NonNull;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

public final class GathererComposite<T, A, R, AA, RR> implements Gatherer<T, Object, RR>
{

    private final Gatherer<T, A, ? extends R> left;
    private final Gatherer<? super R, AA, ? extends RR> right;
    private volatile GathererImpl<T, Object, RR> impl;

    public static <T, A, R, AA, RR> GathererComposite<T, A, R, AA, RR> of(@NonNull Gatherer<T, A, ? extends R> left,
                                                                                   @NonNull Gatherer<? super R, AA, ? extends RR> right)
    {
        Checks.notnull(left, right);
        return new GathererComposite<>(left, right);
    }

    /* package */ GathererComposite(Gatherer<T, A, ? extends R> left,
                                    Gatherer<? super R, AA, ? extends RR> right)
    {
        this.left = left;
        this.right = right;
    }

    @SuppressWarnings("unchecked")
    private GathererImpl<T, Object, RR> impl()
    {
        GathererImpl<T, Object, RR> result = impl;
        if (result == null)
        {
            synchronized (this)
            {
                result = impl;
                if (result == null)
                    impl = result = (GathererImpl<T, Object, RR>) impl(left, right);
            }
        }
        return result;
    }

    @Override
    public Supplier<Object> initializer()
    {
        return impl().initializer();
    }

    @Override
    public Integrator<Object, T, RR> integrator()
    {
        return impl().integrator();
    }

    @Override
    public BinaryOperator<Object> combiner()
    {
        return impl().combiner();
    }

    @Override
    public BiConsumer<Object, Downstream<? super RR>> finisher()
    {
        return impl().finisher();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <RRR> Gatherer<T, ?, RRR> andThen(@NonNull Gatherer<? super RR, ?, ? extends RRR> that)
    {
        if (that instanceof GathererComposite c)
        {
            return left.andThen(right.andThen(c.left).andThen(c.right));
        }
        else
        {
            return left.andThen(right.andThen(that));
        }
    }

    public static <T, A, R, AA, RR> GathererImpl<T, ?, RR> impl(Gatherer<T, A, R> left,
                                                                Gatherer<? super R, AA, RR> right)
    {
        final Supplier<A> leftInitializer = left.initializer();
        final Gatherer.Integrator<A, T, R> leftIntegrator = left.integrator();
        final BinaryOperator<A> leftCombiner = left.combiner();
        final BiConsumer<A, Downstream<? super R>> leftFinisher = left.finisher();

        final Supplier<AA> rightInitializer = right.initializer();
        final Gatherer.Integrator<AA, ? super R, RR> rightIntegrator = right.integrator();
        final BinaryOperator<AA> rightCombiner = right.combiner();
        final BiConsumer<AA, Downstream<? super RR>> rightFinisher = right.finisher();

        final boolean leftStateless = leftInitializer == Gatherer.<A>defaultInitializer();
        final boolean rightStateless = rightInitializer == Gatherer.<AA>defaultInitializer();

        final boolean leftGreedy = leftIntegrator instanceof Gatherer.Integrator.Greedy;
        final boolean rightGreedy = rightIntegrator instanceof Gatherer.Integrator.Greedy;

        if (leftStateless && rightStateless && leftGreedy && rightGreedy)
        {
            return new GathererImpl<>(
                    Gatherer.defaultInitializer(),
                    Gatherer.Integrator.ofGreedy((Integrator.Greedy<Void, T, RR>) (unused, element, downstream) -> leftIntegrator.integrate(null, element,
                            r -> rightIntegrator.integrate(null, r, downstream))),
                    (leftCombiner == Gatherer.<A>defaultCombiner() || rightCombiner == Gatherer.<AA>defaultCombiner())
                            ? Gatherer.defaultCombiner()
                            : GathererValue.DEFAULT.statelessCombiner,
                    (leftFinisher == Gatherer.<A, R>defaultFinisher() && rightFinisher == Gatherer.<AA, RR>defaultFinisher())
                            ? Gatherer.defaultFinisher()
                            : (unused, downstream) -> {
                                if (leftFinisher != Gatherer.<A, R>defaultFinisher())
                                {
                                    leftFinisher.accept(null,
                                            r -> rightIntegrator.integrate(null, r, downstream));
                                }
                                if (rightFinisher != Gatherer.<AA, RR>defaultFinisher())
                                {
                                    rightFinisher.accept(null, downstream);
                                }
                            }
            );
        }
        else
        {
            class State
            {
                final A leftState;
                final AA rightState;
                boolean leftProceed;
                boolean rightProceed;

                private State(A leftState, AA rightState, boolean leftProceed, boolean rightProceed)
                {
                    this.leftState = leftState;
                    this.rightState = rightState;
                    this.leftProceed = leftProceed;
                    this.rightProceed = rightProceed;
                }

                State()
                {
                    this(leftStateless ? null : leftInitializer.get(),
                            rightStateless ? null : rightInitializer.get(),
                            true, true);
                }

                State joinLeft(State right)
                {
                    return new State(
                            leftStateless ? null : leftCombiner.apply(this.leftState, right.leftState),
                            rightStateless ? null : rightCombiner.apply(this.rightState, right.rightState),
                            this.leftProceed && right.leftProceed,
                            this.rightProceed && right.rightProceed);
                }

                boolean integrate(T t, Gatherer.Downstream<? super RR> c)
                {
                    return (leftIntegrator.integrate(leftState, t, r -> rightIntegrate(r, c)) || leftGreedy || (leftProceed = false)) && (rightGreedy || rightProceed);
                }

                void finish(Gatherer.Downstream<? super RR> c)
                {
                    if (leftFinisher != Gatherer.<A, R>defaultFinisher()) {
                        leftFinisher.accept(leftState, r -> rightIntegrate(r, c));
                    }
                    if (rightFinisher != Gatherer.<AA, RR>defaultFinisher())
                    {
                        rightFinisher.accept(rightState, c);
                    }
                }

                public boolean rightIntegrate(R r, Gatherer.Downstream<? super RR> downstream)
                {
                    return (rightGreedy || rightProceed)
                            && (rightIntegrator.integrate(rightState, r, downstream)
                            || rightGreedy
                            || (rightProceed = false));
                }
            }

            return new GathererImpl<>(
                    State::new,
                    (leftGreedy && rightGreedy)
                            ? Gatherer.Integrator.ofGreedy((Integrator.Greedy<State, T, RR>) State::integrate)
                            : Gatherer.Integrator.of((Integrator<State, T, RR>) State::integrate),
                    (leftCombiner == Gatherer.<A>defaultCombiner() || rightCombiner == Gatherer.<AA>defaultCombiner())
                            ? Gatherer.defaultCombiner()
                            : State::joinLeft,
                    (leftFinisher == Gatherer.<A, R>defaultFinisher() && rightFinisher == Gatherer.<AA, RR>defaultFinisher())
                            ? Gatherer.defaultFinisher()
                            : (state, downstream) -> state.finish(downstream)
            );
        }
    }

}
