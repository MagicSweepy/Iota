package magicsweepy.iota.optic;

import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.kind.tuple.Pair;
import magicsweepy.iota.optic.profunctor.Profunctor;
import org.jspecify.annotations.NullMarked;

import java.util.function.Supplier;

@NullMarked
public interface Monoidal<P extends Ob2, Mu extends Monoidal.Mu> extends Profunctor<P, Mu>
{

    interface Mu extends Profunctor.Mu {}

    static <P extends Ob2, Q extends Monoidal.Mu> Monoidal<P, Q> unbox(final Kind<Q, P> box)
    {
        return (Monoidal<P, Q>) box;
    }

    <A, B, C, D> Kind2<P, Pair<A, C>, Pair<B, D>> par(final Kind2<P, A, B> first,
                                                      final Supplier<Kind2<P, C, D>> second);

    Kind2<P, Void, Void> empty();

}