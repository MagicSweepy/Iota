package magicsweepy.iota.stream;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import magicsweepy.iota.util.Checks;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

/**
 * @param <T> The type of input elements for the new gatherer.
 * @param <A> The type of the state of the returned initializer.
 * @param <R> The type of results for the new gatherer.
 */
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.MODULE)
public final class GathererImpl<T, A, R> implements Gatherer<T, A, R>
{

    private final Supplier<A> initializer;
    private final Gatherer.Integrator<A, T, R> integrator;
    private final BinaryOperator<A> combiner;
    private final BiConsumer<A, Downstream<? super R>> finisher;

    /* package */ GathererImpl<T, A, R> of(@NotNull Supplier<A> initializer,
                                                    @NotNull Gatherer.Integrator<A, T, R> integrator,
                                                    @NotNull BinaryOperator<A> combiner,
                                                    @NotNull BiConsumer<A, Downstream<? super R>> finisher)
    {
        return new GathererImpl<>(Checks.notnull(initializer), Checks.notnull(integrator),
                Checks.notnull(combiner), Checks.notnull(finisher));
    }

    @Override
    public Supplier<A> initializer()
    {
        return this.initializer;
    }

    @Override
    public Gatherer.Integrator<A, T, R> integrator()
    {
        return this.integrator;
    }

    @Override
    public BinaryOperator<A> combiner()
    {
        return this.combiner;
    }

    @Override
    public BiConsumer<A, Downstream<? super R>> finisher()
    {
        return this.finisher;
    }

}
