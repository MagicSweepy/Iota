package magicsweepy.iota.optic;

import magicsweepy.iota.kind.Applicative;
import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Ob;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface Wander<S, T, A, B>
{

    <F extends Ob> Functoid<S, Kind<F, T>> wander(final Applicative<F, ?> applicative, final Functoid<A, Kind<F, B>> input);

}