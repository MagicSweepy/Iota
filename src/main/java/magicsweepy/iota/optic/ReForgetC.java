package magicsweepy.iota.optic;

import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.kind.either.Either;
import magicsweepy.iota.kind.tuple.Pair;
import magicsweepy.iota.optic.profunctor.AffineP;
import org.jspecify.annotations.NullMarked;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A {@link ReForgetC} is a profunctor that forgets its input type and only remembers its output type,
 * but can also depend on the input value.
 * <p>
 * It is a more general version of {@link ReForget}, allowed to used {@link #construct()} to build {@link #run}.
 *
 * @see ReForget
 *
 * @param <R> The type of the input that is forgotten.
 * @param <A> The type of the input that is ignored.
 * @param <B> The type of the output that is remembered.
 */
@NullMarked
public interface ReForgetC<R, A, B> extends Kind2<ReForgetC.Mu<R>, A, B>
{

    final class Mu<R> implements Ob2 {}

    static <R, A, B> ReForgetC<R, A, B> unbox(final Kind2<Mu<R>, A, B> box)
    {
        return (ReForgetC<R, A, B>) box;
    }

    Either<Function<R, B>, BiFunction<A, R, B>> construct();

    default B run(final A a, final R r)
    {
        return construct().map(f -> f.apply(r), f -> f.apply(a, r));
    }

    final class Instance<R> implements AffineP<Mu<R>, Instance.Mu<R>>, Kind<Instance.Mu<R>, Mu<R>>
    {

        public static final class Mu<R> implements AffineP.Mu {}

        @Override
        public <A, B, C, D> Functoid<Kind2<ReForgetC.Mu<R>, A, B>, Kind2<ReForgetC.Mu<R>, C, D>> dimap(final Function<C, A> g,
                                                                                                       final Function<B, D> h)
        {
            return input -> Optics.reForgetC("dimap", ReForgetC.unbox(input).construct().map(
                (Function<R, B> f) -> Either.left((R r) -> h.apply(f.apply(r))),
                (BiFunction<A, R, B> f) -> Either.right((C c, R r) -> h.apply(f.apply(g.apply(c), r)))));
        }

        @Override
        public <A, B, C> Kind2<ReForgetC.Mu<R>, Pair<A, C>, Pair<B, C>> first(final Kind2<ReForgetC.Mu<R>, A, B> input)
        {
            return Optics.reForgetC("first", ReForgetC.unbox(input).construct().map(
                (Function<R, B> f) -> Either.right((Pair<A, C> p, R r) -> Pair.of(f.apply(r), p.second())),
                (BiFunction<A, R, B> f) -> Either.right((Pair<A, C> p, R r) -> Pair.of(f.apply(p.first(), r), p.second()))));
        }

        @Override
        public <A, B, C> Kind2<ReForgetC.Mu<R>, Pair<C, A>, Pair<C, B>> second(final Kind2<ReForgetC.Mu<R>, A, B> input)
        {
            return Optics.reForgetC("second", ReForgetC.unbox(input).construct().map(
                (Function<R, B> f) -> Either.right((Pair<C, A> p, R r) -> Pair.of(p.first(), f.apply(r))),
                (BiFunction<A, R, B> f) -> Either.right((Pair<C, A> p, R r) -> Pair.of(p.first(), f.apply(p.second(), r)))));
        }

        @Override
        public <A, B, C> Kind2<ReForgetC.Mu<R>, Either<A, C>, Either<B, C>> left(final Kind2<ReForgetC.Mu<R>, A, B> input)
        {
            return Optics.reForgetC("left", ReForgetC.unbox(input).construct().map(
                (Function<R, B> f) -> Either.left((R r) -> Either.left(f.apply(r))),
                (BiFunction<A, R, B> f) -> Either.right((Either<A, C> p, R r) -> p.mapLeft(a -> f.apply(a, r)))));
        }

        @Override
        public <A, B, C> Kind2<ReForgetC.Mu<R>, Either<C, A>, Either<C, B>> right(final Kind2<ReForgetC.Mu<R>, A, B> input)
        {
            return Optics.reForgetC("right", ReForgetC.unbox(input).construct().map(
                (Function<R, B> f) -> Either.left((R r) -> Either.right(f.apply(r))),
                (BiFunction<A, R, B> f) -> Either.right((Either<C, A> p, R r) -> p.mapRight(a -> f.apply(a, r)))));
        }

    }

}
