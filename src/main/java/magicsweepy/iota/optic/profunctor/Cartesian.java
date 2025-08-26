package magicsweepy.iota.optic.profunctor;

import com.google.common.reflect.TypeToken;
import magicsweepy.iota.kind.CartesianLike;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.kind.tuple.Pair;
import org.jspecify.annotations.NullMarked;

/**
 * @see CartesianLike
 */
@SuppressWarnings("UnstableApiUsage")
@NullMarked
public interface Cartesian<P extends Ob2, Mu extends Cartesian.Mu> extends Profunctor<P, Mu>
{

    interface Mu extends Profunctor.Mu
    {
        TypeToken<Mu> TYPE = new TypeToken<>() {};
    }

    static <P extends Ob2, Q extends Cartesian.Mu> Cartesian<P, Q> unbox(final Kind<Q, P> proofBox)
    {
        return (Cartesian<P, Q>) proofBox;
    }

    <A, B, C> Kind2<P, Pair<A, C>, Pair<B, C>> first(final Kind2<P, A, B> input);

    default <A, B, C> Kind2<P, Pair<C, A>, Pair<C, B>> second(final Kind2<P, A, B> input)
    {
        return dimap(first(input), Pair::swap, Pair::swap);
    }

    default FunctorProfunctor<CartesianLike.Mu, P, FunctorProfunctor.Mu<CartesianLike.Mu>> toFP2()
    {
        return new FunctorProfunctor<>()
        {

            @Override
            public <A, B, F extends Ob> Kind2<P, Kind<F, A>, Kind<F, B>> distribute(final Kind<? extends CartesianLike.Mu, F> kind,
                                                                                    final Kind2<P, A, B> input)
            {
                return cap(CartesianLike.unbox(kind), input);
            }

            private <A, B, F extends Ob, C> Kind2<P, Kind<F, A>, Kind<F, B>> cap(final CartesianLike<F, C, ?> cLike,
                                                                                 final Kind2<P, A, B> input)
            {
                return dimap(first(input), p -> Pair.unbox(cLike.to(p)), cLike::from);
            }

        };
    }

}
