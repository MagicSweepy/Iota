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
 * A {@link ForgetE} is an optic that can be used to "forget" information about a structure while still
 * allowing some form of extraction.
 * <p>
 * It is {@link Either} version of the {@link Forget} optic, which only allows extraction of a single value.
 *
 * @see Forget
 * @see Either
 *
 * @param <R> The type of the value that can be extracted.
 * @param <A> The type of the input value.
 * @param <B> The type of the output value.
 */
@NullMarked
public interface ForgetE<R, A, B> extends Kind2<ForgetE.Mu<R>, A, B>
{

    final class Mu<R> implements Ob2 {}

    static <R, A, B> ForgetE<R, A, B> unbox(final Kind2<Mu<R>, A, B> box)
    {
        return (ForgetE<R, A, B>) box;
    }

    Either<B, R> run(final A a);

    final class Instance<R> implements AffineP<Mu<R>, Instance.Mu<R>>, Kind<Instance.Mu<R>, Mu<R>>
    {

        static final class Mu<R> implements AffineP.Mu {}

        @Override
        public <A, B, C, D> Functoid<Kind2<ForgetE.Mu<R>, A, B>, Kind2<ForgetE.Mu<R>, C, D>> dimap(final Function<C, A> g,
                                                                                                   final Function<B, D> h)
        {
            return input -> Optics.forgetE(c -> ForgetE.unbox(input).run(g.apply(c)).mapLeft(h));
        }

        @Override
        public <A, B, C> Kind2<ForgetE.Mu<R>, Pair<A, C>, Pair<B, C>> first(final Kind2<ForgetE.Mu<R>, A, B> input)
        {
            return Optics.forgetE(p -> ForgetE.unbox(input).run(p.first())
                    .mapLeft(b -> Pair.of(b, p.second())));
        }

        @Override
        public <A, B, C> Kind2<ForgetE.Mu<R>, Pair<C, A>, Pair<C, B>> second(final Kind2<ForgetE.Mu<R>, A, B> input)
        {
            return Optics.forgetE(p -> ForgetE.unbox(input).run(p.second())
                    .mapLeft(b -> Pair.of(p.first(), b)));
        }

        @Override
        public <A, B, C> Kind2<ForgetE.Mu<R>, Either<A, C>, Either<B, C>> left(final Kind2<ForgetE.Mu<R>, A, B> input)
        {
            return Optics.forgetE(e -> e.map(l -> ForgetE.unbox(input).run(l)
                    .mapLeft(Either::left), r -> Either.left(Either.right(r))));
        }

        @Override
        public <A, B, C> Kind2<ForgetE.Mu<R>, Either<C, A>, Either<C, B>> right(final Kind2<ForgetE.Mu<R>, A, B> input)
        {
            return Optics.forgetE(e -> e.map(l -> Either.left(Either.left(l)),
                    r -> ForgetE.unbox(input).run(r).mapLeft(Either::right)));
        }

    }

}
