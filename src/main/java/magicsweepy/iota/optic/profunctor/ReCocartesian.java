package magicsweepy.iota.optic.profunctor;

import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.kind.either.Either;
import org.jspecify.annotations.NullMarked;

/**
 * A profunctor that supports the operations to "undo" a coproduct structure on both its input and output.
 * <p>
 * This is useful for optics that need to manipulate values that can be one of several types, such as {@code Prism}s
 * and {@code Affine}s.
 *
 * @see Cocartesian
 *
 * @param <P>  The profunctor type constructor.
 * @param <Mu> The witness type for the {@link ReCocartesian} profunctor.
 */
@NullMarked
public interface ReCocartesian<P extends Ob2, Mu extends ReCocartesian.Mu> extends Profunctor<P, Mu>
{

    interface Mu extends Profunctor.Mu {}

    static <P extends Ob2, Q extends ReCocartesian.Mu> ReCocartesian<P, Q> unbox(final Kind<Q, P> box)
    {
        return (ReCocartesian<P, Q>) box;
    }

    <A, B, C> Kind2<P, A, B> unleft(final Kind2<P, Either<A, C>, Either<B, C>> input);

    <A, B, C> Kind2<P, A, B> unright(final Kind2<P, Either<C, A>, Either<C, B>> input);

}
