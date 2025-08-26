package magicsweepy.iota.kind;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

@NullMarked
public interface Functor<F extends Ob, Mu extends Functor.Mu> extends Mor<F, Mu>
{

    interface Mu extends Mor.Mu {}

    /**
     * Doing map operation to contained values within {@link Functor} context.
     *
     * <pre>{@code
     *     Ta ---> Aa
     *      |      |
     *      T      A
     *      |      |
     *     F<T> -> F<A>
     * }</pre>
     *
     * @param <T> The generic type of the values about {@link Functor} inputs.
     * @param <A> The generic type of the values about {@link Functor} outputs.
     */
    <T, A> @NonNull Kind<F, A> map(final Function<? super T, ? extends A> f,
                                   final Kind<F, T> fa);

}
