package magicsweepy.iota.optic.profunctor;

import com.google.common.reflect.TypeToken;
import magicsweepy.iota.kind.Applicative;
import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.kind.Traversable;
import magicsweepy.iota.kind.either.Either;
import magicsweepy.iota.kind.tuple.Pair;
import magicsweepy.iota.optic.Wander;
import org.jspecify.annotations.NullMarked;

@SuppressWarnings("UnstableApiUsage")
@NullMarked
public interface TraversalP<P extends Ob2, Mu extends TraversalP.Mu> extends AffineP<P, Mu>
{

    static <P extends Ob2, Proof extends TraversalP.Mu> TraversalP<P, Proof> unbox(final Kind<Proof, P> proofBox)
    {
        return (TraversalP<P, Proof>) proofBox;
    }

    interface Mu extends AffineP.Mu
    {
        TypeToken<Mu> TYPE_TOKEN = new TypeToken<>() {};
    }

    <S, T, A, B> Kind2<P, S, T> wander(final Wander<S, T, A, B> wander, final Kind2<P, A, B> input);

    default <T extends Ob, A, B> Kind2<P, Kind<T, A>, Kind<T, B>> traverse(final Traversable<T, ?> traversable,
                                                                           final Kind2<P, A, B> input)
    {
        return wander(new Wander<>()
        {

            @Override
            public <F extends Ob> Functoid<Kind<T, A>, Kind<F, Kind<T, B>>> wander(final Applicative<F, ?> applicative,
                                                                                   final Functoid<A, Kind<F, B>> function)
            {
                return ta -> traversable.<F, A, B>traverse(applicative, function, ta);
            }

        }, input);
    }

    @Override
    default <A, B, C> Kind2<P, Pair<A, C>, Pair<B, C>> first(final Kind2<P, A, B> input)
    {
        return dimap(traverse(new Pair.Instance<>(), input), box -> box, Pair::unbox);
    }

    @Override
    default <A, B, C> Kind2<P, Either<A, C>, Either<B, C>> left(final Kind2<P, A, B> input)
    {
        return dimap(traverse(new Either.Instance<>(), input), box -> box, Either::unbox);
    }

    default FunctorProfunctor<Traversable.Mu, P, FunctorProfunctor.Mu<Traversable.Mu>> toFP3()
    {
        return new FunctorProfunctor<>()
        {

            @Override
            public <A, B, F extends Ob> Kind2<P, Kind<F, A>, Kind<F, B>> distribute(final Kind<? extends Traversable.Mu, F> proof,
                                                                                    final Kind2<P, A, B> input)
            {
                return traverse(Traversable.unbox(proof), input);
            }

        };
    }

}
