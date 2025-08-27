package magicsweepy.iota.optic;

import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.kind.either.Either;
import magicsweepy.iota.kind.tuple.Pair;
import magicsweepy.iota.optic.profunctor.AffineP;
import magicsweepy.iota.optic.profunctor.Cartesian;
import magicsweepy.iota.optic.profunctor.Cocartesian;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

/**
 * An {@code Affine} is an optic that represents a focus on at most one part of a data structure.
 * <p>
 * It is similar to a {@link Lens}, but while a lens always focuses on exactly one part, an affine optic may not find
 * the part it is looking for. This makes it suitable for working with optional fields or elements in a data structure.
 * <p>
 * An {@code Affine} can be thought of as a pair of functions:
 * <ul>
 *     <li>{@link #preview(S)}: Attempts to extract the focused part of type {@code A} from the source type {@code S},
 *          returning an {@link Either} that contains either the extracted value or the original structure if the part
 *          is not found.</li>
 *     <li>{@link #set(B, S)}: Updates the focused part with a new value of type {@code B}, producing a new structure
 *          of type {@code T}. If the part was not found, the original structure is returned unchanged.</li>
 * </ul>
 * <p>
 * This optic is useful when you want to work with data structures that may or may not contain certain parts in a
 * composable manner, allowing you to build more complex optics by combining simpler ones.
 *
 * @param <S> The source type.
 * @param <T> The modified source type.
 * @param <A> The target type.
 * @param <B> The modified target type.
 */
@NullMarked
public interface Affine<S, T, A, B> extends Kind2<Affine.Mu<A, B>, S, T>, Optic<AffineP.Mu, S, T, A, B>
{

    final class Mu<A, B> implements Ob2 {}

    static <S, T, A, B> Affine<S, T, A, B> unbox(final Kind2<Mu<A, B>, S, T> box)
    {
        return (Affine<S, T, A, B>) box;
    }

    Either<T, A> preview(final S s);

    T set(final B b, final S s);

    @Override
    default <P extends Ob2> Functoid<Kind2<P, A, B>, Kind2<P, S, T>> eval(final Kind<? extends AffineP.Mu, P> proof)
    {
        final Cartesian<P, ? extends AffineP.Mu> cartesian = Cartesian.unbox(proof);
        final Cocartesian<P, ? extends AffineP.Mu> cocartesian = Cocartesian.unbox(proof);
        return input -> cartesian.dimap(
            cocartesian.left(cartesian.rmap(cartesian.first(input), p -> set(p.first(), p.second()))),
            (S s) -> preview(s).map(Either::right, a -> Either.left(Pair.of(a, s))),
            (Either<T, T> e) -> e.map(Function.identity(), Function.identity()));
    }

    final class Instance<A2, B2> implements AffineP<Mu<A2, B2>, AffineP.Mu>
    {

        @Override
        public <A, B, C, D> Functoid<Kind2<Affine.Mu<A2, B2>, A, B>, Kind2<Affine.Mu<A2, B2>, C, D>> dimap(final Function<C, A> g,
                                                                                                           final Function<B, D> h)
        {
            return affineBox -> Optics.affine(
                (C c) -> Affine.unbox(affineBox).preview(g.apply(c)).mapLeft(h),
                (b2, c) -> h.apply(Affine.unbox(affineBox).set(b2, g.apply(c))));
        }

        @Override
        public <A, B, C> Kind2<Affine.Mu<A2, B2>, Pair<A, C>, Pair<B, C>> first(final Kind2<Affine.Mu<A2, B2>, A, B> input)
        {
            final Affine<A, B, A2, B2> affine = Affine.unbox(input);
            return Optics.affine(
                pair -> affine.preview(pair.first()).mapBoth(b -> Pair.of(b, pair.second()), Function.identity()),
                (b2, pair) -> Pair.of(affine.set(b2, pair.first()), pair.second()));
        }

        @Override
        public <A, B, C> Kind2<Affine.Mu<A2, B2>, Pair<C, A>, Pair<C, B>> second(final Kind2<Affine.Mu<A2, B2>, A, B> input)
        {
            final Affine<A, B, A2, B2> affine = Affine.unbox(input);
            return Optics.affine(
                pair -> affine.preview(pair.second()).mapBoth(b -> Pair.of(pair.first(), b), Function.identity()),
                (b2, pair) -> Pair.of(pair.first(), affine.set(b2, pair.second())));
        }

        @Override
        public <A, B, C> Kind2<Affine.Mu<A2, B2>, Either<A, C>, Either<B, C>> left(final Kind2<Affine.Mu<A2, B2>, A, B> input)
        {
            final Affine<A, B, A2, B2> affine = Affine.unbox(input);
            return Optics.affine(either -> either.map(
                    a -> affine.preview(a).mapLeft(Either::left),
                    c -> Either.left(Either.right(c))),
                (b, either) -> either.map(l -> Either.left(affine.set(b, l)), Either::right));
        }

        @Override
        public <A, B, C> Kind2<Affine.Mu<A2, B2>, Either<C, A>, Either<C, B>> right(final Kind2<Affine.Mu<A2, B2>, A, B> input)
        {
            final Affine<A, B, A2, B2> affine = Affine.unbox(input);
            return Optics.affine(either -> either.map(
                    c -> Either.left(Either.left(c)),
                    a -> affine.preview(a).mapLeft(Either::right)),
                (b, either) -> either.map(Either::left, r -> Either.right(affine.set(b, r))));
        }

    }

}
