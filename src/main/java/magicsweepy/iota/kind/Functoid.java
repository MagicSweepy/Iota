package magicsweepy.iota.kind;

import magicsweepy.iota.kind.tuple.Pair;
import magicsweepy.iota.optic.Monoidal;
import magicsweepy.iota.optic.Wander;
import magicsweepy.iota.optic.profunctor.Mapping;
import magicsweepy.iota.optic.profunctor.MonoidProfunctor;
import magicsweepy.iota.optic.profunctor.Procompose;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The prototypes of the {@link Function}, used to represent it to type level.
 * <p>
 * A {@link Functoid} is a wrapper around a standard Java {@link Function} that allowing it to be used in a higher
 * kinded way. It can be seen as a {@code Profunctor} that is also a {@code Functor} in its second argument.
 *
 * @param <A> The input type of the function.
 * @param <B> The output type of the function.
 */
@NullMarked
public interface Functoid<A, B> extends Function<A, B>, Kind2<Functoid.Mu, A, B>, Kind<Functoid.MuF<A>, B>
{

    final class Mu implements Ob2 {}

    final class MuF<A> implements Ob {}

    static <A, B> Functoid<A, B> create(final Function<? super A, ? extends B> f)
    {
        return f::apply;
    }

    static <A, B> Functoid<A, B> unbox(final Kind2<Functoid.Mu, A, B> box)
    {
        return (Functoid<A, B>) box;
    }

    static <A, B> Functoid<A, B> unbox(final Kind<Functoid.MuF<A>, B> box)
    {
        return (Functoid<A, B>) box;
    }

    @Override
    B apply(A a);

    enum Instance implements Kind<Instance.Mu, Functoid.Mu>,
                             Monoidal<Functoid.Mu, Instance.Mu>,
                             MonoidProfunctor<Mu, Instance.Mu>,
                             Mapping<Mu, Instance.Mu>
    {

        INSTANCE;

        public static final class Mu implements Monoidal.Mu, MonoidProfunctor.Mu, Mapping.Mu {}

        @Override
        public <A, B, C, D> Functoid<Kind2<Functoid.Mu, A, B>, Kind2<Functoid.Mu, C, D>> dimap(final Function<C, A> g,
                                                                                               final Function<B, D> h)
        {
            return f -> create(h.compose(unbox(f)).compose(g));
        }

        @Override
        public <A, B, C, D> Kind2<Functoid.Mu, Pair<A, C>, Pair<B, D>> par(Kind2<Functoid.Mu, A, B> first,
                                                                           Supplier<Kind2<Functoid.Mu, C, D>> second)
        {
            return create(pair -> Pair.of(unbox(first).apply(pair.first()), unbox(second.get()).apply(pair.second())));
        }

        @Override
        public Kind2<Functoid.Mu, Void, Void> empty()
        {
            return create(Function.identity());
        }

        @Override
        public <A, B> Kind2<Functoid.Mu, A, B> zero(Kind2<Functoid.Mu, A, B> f)
        {
            return f;
        }

        @Override
        public <A, B> Kind2<Functoid.Mu, A, B> plus(Kind2<Procompose.Mu<Functoid.Mu, Functoid.Mu>, A, B> input)
        {
            return cap(Procompose.unbox(input));
        }

        private <A, B, C> Kind2<Functoid.Mu, A, B> cap(final Procompose<Functoid.Mu, Functoid.Mu, A, B, C> cmp)
        {
            return create(Functoid.unbox(cmp.second()).compose(Functoid.unbox(cmp.first().get())));
        }

        @Override
        public <A, B, F extends Ob> Kind2<Functoid.Mu, Kind<F, A>, Kind<F, B>> mapping(Functor<F, ?> functor,
                                                                                       Kind2<Functoid.Mu, A, B> input)
        {
            return create(fa -> functor.map(Functoid.unbox(input), fa));
        }

        @Override
        public <S, T, A, B> Kind2<Functoid.Mu, S, T> wander(Wander<S, T, A, B> wander,
                                                            Kind2<Functoid.Mu, A, B> input)
        {
            return create(s -> IdF.get(wander.wander(IdF.Instance.INSTANCE,
                    a -> IdF.create(Functoid.unbox(input).apply(a))).apply(s)));
        }

    }

    final class InstanceF<R> implements Representable<Functoid.MuF<R>, R, InstanceF.Mu<R>>
    {

        public static final class Mu<A> implements Representable.Mu {}

        @NonNull
        @Override
        public <T, A> Kind<MuF<R>, A> map(Function<? super T, ? extends A> f,
                                          Kind<MuF<R>, T> fa)
        {
            return Functoid.create(f.compose(Functoid.unbox(fa)));
        }

        @NonNull
        @Override
        public <A> Kind<MuF<R>, A> from(Kind<MuF<R>, A> input)
        {
            return input;
        }

        @NonNull
        @Override
        public <A> Kind<MuF<R>, A> to(Kind<MuF<R>, A> input)
        {
            return input;
        }

    }

}
