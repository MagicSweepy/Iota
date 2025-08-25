package magicsweepy.iota.kind;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

/**
 * @param <F> The generic type of the monad.
 * @param <E> The generic type of the error value.
 */
@NullMarked
public interface ThrowableMonad<F extends Ob, E> extends Monad<F>
{

    <A> Kind<F, A> liftError(@Nullable E error);

    <A> Kind<F, A> handleErrorWith(Kind<F, A> ma,
                                   Function<? super E, ? extends Kind<F, A>> handler);

    default <A> Kind<F, A> handleError(Kind<F, A> ma,
                                       Function<? super E, ? extends A> handler)
    {
        return handleErrorWith(ma, error -> of(handler.apply(error)));
    }

    default <A> Kind<F, A> recoverWith(Kind<F, A> ma,
                                       Kind<F, A> fallback)
    {
        return handleErrorWith(ma, error -> fallback);
    }

    default <A> Kind<F, A> recover(Kind<F, A> ma,
                                   @Nullable A value)
    {
        return handleError(ma, error -> value);
    }

}
