package magicsweepy.iota.optic.profunctor;

import com.google.common.reflect.TypeToken;
import magicsweepy.iota.kind.CocartesianLike;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.kind.either.Either;
import org.jspecify.annotations.NullMarked;

/**
 * A profunctor that supports the operations of a cocartesian category.
 *
 * @see CocartesianLike
 *
 * @param <P>  The profunctor type constructor.
 * @param <Mu> The witness type for this profunctor.
 */
@SuppressWarnings("UnstableApiUsage")
@NullMarked
public interface Cocartesian<P extends Ob2, Mu extends Cocartesian.Mu> extends Profunctor<P, Mu>
{

    interface Mu extends Profunctor.Mu
    {
        TypeToken<Mu> TYPE = new TypeToken<>() {};

    }

    static <P extends Ob2, Q extends Cocartesian.Mu> Cocartesian<P, Q> unbox(final Kind<Q, P> box)
    {
        return (Cocartesian<P, Q>) box;
    }

    <A, B, C> Kind2<P, Either<A, C>, Either<B, C>> left(final Kind2<P, A, B> input);

    default <A, B, C> Kind2<P, Either<C, A>, Either<C, B>> right(final Kind2<P, A, B> input)
    {
        return dimap(left(input), Either::swap, Either::swap);
    }

    default FunctorProfunctor<CocartesianLike.Mu, P, FunctorProfunctor.Mu<CocartesianLike.Mu>> toFP()
    {
        return new FunctorProfunctor<>()
        {

            @Override
            public <A, B, F extends Ob> Kind2<P, Kind<F, A>, Kind<F, B>> distribute(final Kind<? extends CocartesianLike.Mu, F> proof,
                                                                                    final Kind2<P, A, B> input)
            {
                return cap(CocartesianLike.unbox(proof), input);
            }

            private <A, B, F extends Ob, C> Kind2<P, Kind<F, A>, Kind<F, B>> cap(final CocartesianLike<F, C, ?> cLike,
                                                                                 final Kind2<P, A, B> input)
            {
                return dimap(left(input), e -> Either.unbox(cLike.to(e)), cLike::from);
            }

        };
    }

}
