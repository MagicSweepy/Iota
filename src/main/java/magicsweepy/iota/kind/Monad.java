package magicsweepy.iota.kind;

import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

@NullMarked
public interface Monad<F extends Ob> extends Applicative<F, Monad.Mu>
{

    interface Mu extends Applicative.Mu {}

    <T1, T2> Kind<F, T2> flatMap(Function<? super T1, ? extends Kind<F, T2>> f, Kind<F, T1> fa);

}
