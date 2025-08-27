package magicsweepy.iota.kind;

import magicsweepy.iota.kind.tuple.Pair;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

/**
 * A type that behaves like a cartesian product with respect to some type constructor.
 *
 * @see Pair
 *
 * @param <T>  The type constructor that is cartesian-like.
 * @param <C>  The constant type in the product.
 * @param <Mu> The witness type for this cartesian-like structure.
 */
@NullMarked
public interface CartesianLike<T extends Ob, C, Mu extends CartesianLike.Mu> extends Functor<T, Mu>, Traversable<T, Mu>
{

    interface Mu extends Functor.Mu, Traversable.Mu {}

    static <F extends Ob, C, Mu extends CartesianLike.Mu> CartesianLike<F, C, Mu> unbox(final Kind<Mu, F> box)
    {
        return (CartesianLike<F, C, Mu>) box;
    }

    <A> Kind<Pair.Mu<C>, A> to(final Kind<T, A> input);

    <A> Kind<T, A> from(final Kind<Pair.Mu<C>, A> input);

    @Override
    default <F extends Ob, A, B> Kind<F, Kind<T, B>> traverse(final Applicative<F, ?> ap,
                                                              final Function<A, Kind<F, B>> f,
                                                              final Kind<T, A> input)
    {
        return ap.map(this::from, new Pair.Instance<C>().traverse(ap, f, to(input)));
    }

}
