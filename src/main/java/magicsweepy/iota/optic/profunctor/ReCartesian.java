package magicsweepy.iota.optic.profunctor;

import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.kind.tuple.Pair;
import org.jspecify.annotations.NullMarked;

/**
 * A profunctor that supports the operations to "undo" a product structure on both its input and output.
 * <p>
 * This is useful for optics that need to manipulate pairs of values, such as {@code Lens}es and {@code Prism}s.
 *
 * @see Cartesian
 *
 * @param <P>  The profunctor type constructor.
 * @param <Mu> The witness type for the {@link ReCartesian} profunctor.
 */
@NullMarked
public interface ReCartesian<P extends Ob2, Mu extends ReCartesian.Mu> extends Profunctor<P, Mu>
{

    static <P extends Ob2, Q extends ReCartesian.Mu> ReCartesian<P, Q> unbox(final Kind<Q, P> box)
    {
        return (ReCartesian<P, Q>) box;
    }

    interface Mu extends Profunctor.Mu {}

    <A, B, C> Kind2<P, A, B> unfirst(final Kind2<P, Pair<A, C>, Pair<B, C>> input);

    <A, B, C> Kind2<P, A, B> unsecond(final Kind2<P, Pair<C, A>, Pair<C, B>> input);

}
