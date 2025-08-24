package magicsweepy.iota.kind;

import org.jspecify.annotations.NullMarked;

/**
 * @see Monad
 */
@NullMarked
public interface SemiMonad<F extends Ob> extends Monad<F>
{

    <A> Kind<F, A> point();

}
