package magicsweepy.iota.optic.profunctor;

import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Mor2;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.kind.Ob2;
import org.jspecify.annotations.NullMarked;

/**
 * @see Profunctor
 * @see Mor2
 */
@NullMarked
public interface FunctorProfunctor<T extends Ob, P extends Ob2, Mu extends FunctorProfunctor.Mu<T>> extends Mor2<P, Mu>
{

    interface Mu<T extends Ob> extends Mor2.Mu {}

    static <T extends Ob, P extends Ob2, Mu extends FunctorProfunctor.Mu<T>> FunctorProfunctor<T, P, Mu> unbox(final Kind<Mu, P> box)
    {
        return (FunctorProfunctor<T, P, Mu>) box;
    }

    <A, B, F extends Ob> Kind2<P, Kind<F, A>, Kind<F, B>> distribute(final Kind<? extends T, F> kind, final Kind2<P, A, B> input);

}