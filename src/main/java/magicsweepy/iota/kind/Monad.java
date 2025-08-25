package magicsweepy.iota.kind;

import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

@SuppressWarnings("NullableProblems")
@NullUnmarked
public interface Monad<F extends Ob> extends Applicative<F, Monad.Mu>
{

    interface Mu extends Applicative.Mu {}

    <A> Kind<F, A> of(@Nullable A value);

    <T1, T2> Kind<F, T2> flatMap(Function<? super T1, ? extends Kind<F, T2>> f, Kind<F, T1> fa);

}
