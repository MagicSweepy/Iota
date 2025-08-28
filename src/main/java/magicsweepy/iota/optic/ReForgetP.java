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
 * A {@link ReForgetP} is a profunctor that forgets its input type and only remembers its output type,
 * but can also depend on an additional context parameter to retrieve forgotten information.
 * <p>
 * This profunctor can be used to model optics that can forget some information while still allowing
 * retrieval of that information using an additional context parameter.
 * <p>
 * This is the {@link Pair} version of the {@link ReForget} optic.
 *
 * @see Pair
 * @see ReForget
 *
 * @param <R> The type of the additional context that can be used to retrieve forgotten information.
 * @param <A> The input type of the profunctor.
 * @param <B> The output type of the profunctor.
 */
@NullMarked
public interface ReForgetP<R, A, B> extends Kind2<ReForgetP.Mu<R>, A, B>
{

    final class Mu<R> implements Ob2 {}

    static <R, A, B> ReForgetP<R, A, B> unbox(final Kind2<Mu<R>, A, B> box)
    {
        return (ReForgetP<R, A, B>) box;
    }

    B run(final A a, final R r);

    final class Instance<R> implements AffineP<Mu<R>, Instance.Mu<R>>, Kind<Instance.Mu<R>, Mu<R>>
    {

        static final class Mu<R> implements AffineP.Mu {}

        @Override
        public <A, B, C, D> Functoid<Kind2<ReForgetP.Mu<R>, A, B>, Kind2<ReForgetP.Mu<R>, C, D>> dimap(final Function<C, A> g,
                                                                                                       final Function<B, D> h)
        {
            return input -> Optics.reForgetP("dimap", (c, r) -> {
                final A a = g.apply(c);
                final B b = ReForgetP.unbox(input).run(a, r);
                return h.apply(b);
            });
        }

        @Override
        public <A, B, C> Kind2<ReForgetP.Mu<R>, Either<A, C>, Either<B, C>> left(final Kind2<ReForgetP.Mu<R>, A, B> input)
        {
            return Optics.reForgetP("left", (e, r) -> e.mapLeft(a -> ReForgetP.unbox(input).run(a, r)));
        }

        @Override
        public <A, B, C> Kind2<ReForgetP.Mu<R>, Either<C, A>, Either<C, B>> right(final Kind2<ReForgetP.Mu<R>, A, B> input)
        {
            return Optics.reForgetP("right", (e, r) -> e.mapRight(a -> ReForgetP.unbox(input).run(a, r)));
        }

        @Override
        public <A, B, C> Kind2<ReForgetP.Mu<R>, Pair<A, C>, Pair<B, C>> first(final Kind2<ReForgetP.Mu<R>, A, B> input)
        {
            return Optics.reForgetP("first", (p, r) -> Pair.of(ReForgetP.unbox(input).run(p.first(), r), p.second()));
        }

        @Override
        public <A, B, C> Kind2<ReForgetP.Mu<R>, Pair<C, A>, Pair<C, B>> second(final Kind2<ReForgetP.Mu<R>, A, B> input)
        {
            return Optics.reForgetP("second", (p, r) -> Pair.of(p.first(), ReForgetP.unbox(input).run(p.second(), r)));
        }

    }

}
