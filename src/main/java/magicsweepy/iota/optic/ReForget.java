package magicsweepy.iota.optic;

import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.kind.either.Either;
import magicsweepy.iota.kind.tuple.Pair;
import magicsweepy.iota.optic.profunctor.Cocartesian;
import magicsweepy.iota.optic.profunctor.ReCartesian;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

/**
 * A {@link ReForget} is a profunctor that forgets its input type and only remembers its output type.
 * <p>
 * It is the dual version of {@link Forget}.
 *
 * @see Forget
 * @see ReCartesian
 *
 * @param <R> The type of the input that is forgotten.
 * @param <A> The type of the input that is ignored.
 * @param <B> The type of the output that is remembered.
 */
@NullMarked
public interface ReForget<R, A, B> extends Kind2<ReForget.Mu<R>, A, B>
{

    final class Mu<R> implements Ob2 {}

    static <R, A, B> ReForget<R, A, B> unbox(final Kind2<Mu<R>, A, B> box)
    {
        return (ReForget<R, A, B>) box;
    }

    B run(final R r);

    final class Instance<R> implements ReCartesian<Mu<R>, Instance.Mu<R>>,
                                       Cocartesian<Mu<R>, Instance.Mu<R>>,
                                       Kind<Instance.Mu<R>, Mu<R>>
    {
        static final class Mu<R> implements ReCartesian.Mu, Cocartesian.Mu {}

        @Override
        public <A, B, C, D> Functoid<Kind2<ReForget.Mu<R>, A, B>, Kind2<ReForget.Mu<R>, C, D>> dimap(final Function<C, A> g,
                                                                                                     final Function<B, D> h)
        {
            return input -> Optics.reForget(r -> h.apply(ReForget.unbox(input).run(r)));
        }

        @Override
        public <A, B, C> Kind2<ReForget.Mu<R>, A, B> unfirst(final Kind2<ReForget.Mu<R>, Pair<A, C>, Pair<B, C>> input)
        {
            return Optics.reForget(r -> ReForget.unbox(input).run(r).first());
        }

        @Override
        public <A, B, C> Kind2<ReForget.Mu<R>, A, B> unsecond(final Kind2<ReForget.Mu<R>, Pair<C, A>, Pair<C, B>> input)
        {
            return Optics.reForget(r -> ReForget.unbox(input).run(r).second());
        }

        @Override
        public <A, B, C> Kind2<ReForget.Mu<R>, Either<A, C>, Either<B, C>> left(final Kind2<ReForget.Mu<R>, A, B> input)
        {
            return Optics.reForget(r -> Either.left(ReForget.unbox(input).run(r)));
        }

        @Override
        public <A, B, C> Kind2<ReForget.Mu<R>, Either<C, A>, Either<C, B>> right(final Kind2<ReForget.Mu<R>, A, B> input)
        {
            return Optics.reForget(r -> Either.right(ReForget.unbox(input).run(r)));
        }

    }

}
