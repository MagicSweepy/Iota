package magicsweepy.iota.optic;

import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.kind.either.Either;
import magicsweepy.iota.kind.tuple.Pair;
import magicsweepy.iota.optic.profunctor.AffineP;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

/**
 * A profunctor that represents a "reversible forgetful" optic with additional context.
 * <p>
 * This profunctor can be used to model optics that can forget some information while still allowing
 * retrieval of that information using an additional context parameter.
 * <p>
 * Accepted both {@link Pair} and {@link Either} with {@link ReForget} (i.e. {@link ReForgetE} + {@link ReForgetP}).
 *
 * @see Either
 * @see Pair
 * @see ReForget
 * @see ReForgetE
 * @see ReForgetP
 *
 * @param <R> The type of the additional context that can be used to retrieve forgotten information.
 * @param <A> The input type of the profunctor.
 * @param <B> The output type of the profunctor.
 */
@NullMarked
public interface ReForgetEP<R, A, B> extends Kind2<ReForgetEP.Mu<R>, A, B>
{

    final class Mu<R> implements Ob2 {}

    static <R, A, B> ReForgetEP<R, A, B> unbox(final Kind2<Mu<R>, A, B> box)
    {
        return (ReForgetEP<R, A, B>) box;
    }

    B run(final Either<A, Pair<A, R>> e);

    final class Instance<R> implements AffineP<Mu<R>, Instance.Mu<R>>, Kind<Instance.Mu<R>, Mu<R>>
    {

        static final class Mu<R> implements AffineP.Mu {}

        @Override
        public <A, B, C, D> Functoid<Kind2<ReForgetEP.Mu<R>, A, B>, Kind2<ReForgetEP.Mu<R>, C, D>> dimap(final Function<C, A> g,
                                                                                                         final Function<B, D> h)
        {
            return input -> Optics.reForgetEP("dimap", e -> {
                final Either<A, Pair<A, R>> either = e.mapBoth(g, p -> Pair.of(g.apply(p.first()), p.second()));
                final B b = ReForgetEP.unbox(input).run(either);
                return h.apply(b);
            });
        }

        @Override
        public <A, B, C> Kind2<ReForgetEP.Mu<R>, Either<A, C>, Either<B, C>> left(final Kind2<ReForgetEP.Mu<R>, A, B> input)

        {
            final ReForgetEP<R, A, B> reForgetEP = ReForgetEP.unbox(input);
            return Optics.reForgetEP("left", e -> e.map(
                    e2 -> e2.mapLeft(a -> reForgetEP.run(Either.left(a))),
                    (Pair<Either<A, C>, R> p) -> p.first()
                            .mapLeft(a -> reForgetEP.run(Either.right(Pair.of(a, p.second()))))));
        }

        @Override
        public <A, B, C> Kind2<ReForgetEP.Mu<R>, Either<C, A>, Either<C, B>> right(final Kind2<ReForgetEP.Mu<R>, A, B> input)
        {
            final ReForgetEP<R, A, B> reForgetEP = ReForgetEP.unbox(input);
            return Optics.reForgetEP("right", e -> e.map(
                    e2 -> e2.mapRight(a -> reForgetEP.run(Either.left(a))),
                    (Pair<Either<C, A>, R> p) -> p.first()
                            .mapRight(a -> reForgetEP.run(Either.right(Pair.of(a, p.second()))))));
        }

        @Override
        public <A, B, C> Kind2<ReForgetEP.Mu<R>, Pair<A, C>, Pair<B, C>> first(final Kind2<ReForgetEP.Mu<R>, A, B> input)
        {
            final ReForgetEP<R, A, B> reForgetEP = ReForgetEP.unbox(input);
            return Optics.reForgetEP("first", e -> e.map(
                    p -> Pair.of(reForgetEP.run(Either.left(p.first())), p.second()),
                    (Pair<Pair<A, C>, R> p) -> Pair.of(reForgetEP.run(Either.right(Pair.of(p.first().first(),
                            p.second()))), p.first().second())));
        }

        @Override
        public <A, B, C> Kind2<ReForgetEP.Mu<R>, Pair<C, A>, Pair<C, B>> second(final Kind2<ReForgetEP.Mu<R>, A, B> input)
        {
            final ReForgetEP<R, A, B> reForgetEP = ReForgetEP.unbox(input);
            return Optics.reForgetEP("second",
                e -> e.map(p -> Pair.of(p.first(),
                                reForgetEP.run(Either.left(p.second()))),
                    (Pair<Pair<C, A>, R> p) -> Pair.of(p.first().first(), reForgetEP.run(Either.right(Pair.of(
                            p.first().second(), p.second()))))));
        }

    }

}
