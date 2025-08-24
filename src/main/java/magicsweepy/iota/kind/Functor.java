package magicsweepy.iota.kind;

import org.jspecify.annotations.NonNull;

import java.util.function.Function;

/**
 * Functor operand {@code F} for two types.
 * <p>
 * A functor will applying a function to values which be contained in {@code F} without any changing.
 *
 * @param <F> The generic type of the type constructor.
 */
public interface Functor<F>
{

    /**
     * B -> F(B)
     * |     |
     * Bb    |
     * |     |
     * Aa    |
     * |     |
     * A -> F(A)
     */
    <A, B> @NonNull Kind<F, B> map(@NonNull Function<? super A, ? extends B> f,
                                   @NonNull Kind<F, A> fa);

}
