package magicsweepy.iota.optic.profunctor;

import magicsweepy.iota.kind.Functor;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.kind.Ob2;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface Mapping<P extends Ob2, Mu extends Mapping.Mu> extends TraversalP<P, Mu>
{

    interface Mu extends TraversalP.Mu {}

    static <P extends Ob2, Proof extends Mapping.Mu> Mapping<P, Proof> unbox(final Kind<Proof, P> box)
    {
        return (Mapping<P, Proof>) box;
    }

    <A, B, F extends Ob> Kind2<P, Kind<F, A>, Kind<F, B>> mapping(final Functor<F, ?> functor,
                                                                  final Kind2<P, A, B> input);

}