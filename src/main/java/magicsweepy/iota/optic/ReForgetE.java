package magicsweepy.iota.optic;

import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.kind.either.Either;
import magicsweepy.iota.optic.profunctor.Cocartesian;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

/**
 * A {@link ReForgetE} is a profunctor that forgets its input type and only remembers its output type,
 * but can also depend on the input value being either one of two types.
 * <p>
 * This is the {@link Either} version of the {@link ReForget} optic, also the dual version of {@link ForgetE}.
 *
 * @see Either
 * @see ForgetE
 * @see ReForget
 *
 * @param <R> The type of the input that is forgotten.
 * @param <A> The type of the input that is ignored.
 * @param <B> The type of the output that is remembered.
 */
@NullMarked
public interface ReForgetE<R, A, B> extends Kind2<ReForgetE.Mu<R>, A, B>
{

    final class Mu<R> implements Ob2 {}

    static <R, A, B> ReForgetE<R, A, B> unbox(final Kind2<Mu<R>, A, B> box)
    {
        return (ReForgetE<R, A, B>) box;
    }

    B run(final Either<A, R> r);

    final class Instance<R> implements Cocartesian<Mu<R>, Instance.Mu<R>>,
                                       Kind<Instance.Mu<R>, Mu<R>>
    {

        static final class Mu<R> implements Cocartesian.Mu {}

        @Override
        public <A, B, C, D> Functoid<Kind2<ReForgetE.Mu<R>, A, B>, Kind2<ReForgetE.Mu<R>, C, D>> dimap(final Function<C, A> g,
                                                                                                       final Function<B, D> h)
        {
            return input -> Optics.reForgetE("dimap", e -> {
                final Either<A, R> either = e.mapLeft(g);
                final B b = ReForgetE.unbox(input).run(either);
                return h.apply(b);
            });
        }

        @Override
        public <A, B, C> Kind2<ReForgetE.Mu<R>, Either<A, C>, Either<B, C>> left(final Kind2<ReForgetE.Mu<R>, A, B> input)
        {
            final ReForgetE<R, A, B> reForgetE = ReForgetE.unbox(input);
            return Optics.reForgetE("left", e -> e.map(e2 -> e2.map(
                    a -> Either.left(reForgetE.run(Either.left(a))), Either::right),
                    r -> Either.left(reForgetE.run(Either.right(r)))));
        }

        @Override
        public <A, B, C> Kind2<ReForgetE.Mu<R>, Either<C, A>, Either<C, B>> right(final Kind2<ReForgetE.Mu<R>, A, B> input)
        {
            final ReForgetE<R, A, B> reForgetE = ReForgetE.unbox(input);
            return Optics.reForgetE("right", e -> e.map(e2 -> e2.map(
                    Either::left, a -> Either.right(reForgetE.run(Either.left(a)))),
                    r -> Either.right(reForgetE.run(Either.right(r)))));
        }

    }

}
