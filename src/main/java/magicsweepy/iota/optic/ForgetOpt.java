package magicsweepy.iota.optic;

import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.kind.either.Either;
import magicsweepy.iota.kind.tuple.Pair;
import magicsweepy.iota.optic.profunctor.AffineP;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;
import java.util.function.Function;

/**
 * A {@link ForgetOpt} is an optic that can be used to "forget" information about a structure while still
 * allowing some form of extraction.
 * <p>
 * It is {@link Optional} version of the {@link Forget} optic, which allows extraction of an optional value.
 *
 * @see Forget
 * @see Optional
 *
 * @param <R> The type of the value that can be extracted.
 * @param <A> The type of the input value.
 * @param <B> The type of the output value.
 */
@NullMarked
public interface ForgetOpt<R, A, B> extends Kind2<ForgetOpt.Mu<R>, A, B>
{

    final class Mu<R> implements Ob2 {}

    static <R, A, B> ForgetOpt<R, A, B> unbox(final Kind2<Mu<R>, A, B> box)
    {
        return (ForgetOpt<R, A, B>) box;
    }

    Optional<R> run(final A a);

    final class Instance<R> implements AffineP<Mu<R>, Instance.Mu<R>>, Kind<Instance.Mu<R>, Mu<R>>
    {

        public static final class Mu<R> implements AffineP.Mu {}

        @Override
        public <A, B, C, D> Functoid<Kind2<ForgetOpt.Mu<R>, A, B>, Kind2<ForgetOpt.Mu<R>, C, D>> dimap(final Function<C, A> g,
                                                                                                       final Function<B, D> h)
        {
            return input -> Optics.forgetOpt(c -> ForgetOpt.unbox(input).run(g.apply(c)));
        }

        @Override
        public <A, B, C> Kind2<ForgetOpt.Mu<R>, Pair<A, C>, Pair<B, C>> first(final Kind2<ForgetOpt.Mu<R>, A, B> input)
        {
            return Optics.forgetOpt(p -> ForgetOpt.unbox(input).run(p.first()));
        }

        @Override
        public <A, B, C> Kind2<ForgetOpt.Mu<R>, Pair<C, A>, Pair<C, B>> second(final Kind2<ForgetOpt.Mu<R>, A, B> input)
        {
            return Optics.forgetOpt(p -> ForgetOpt.unbox(input).run(p.second()));
        }

        @Override
        public <A, B, C> Kind2<ForgetOpt.Mu<R>, Either<A, C>, Either<B, C>> left(final Kind2<ForgetOpt.Mu<R>, A, B> input)
        {
            return Optics.forgetOpt(e -> e.left().flatMap(ForgetOpt.unbox(input)::run));
        }

        @Override
        public <A, B, C> Kind2<ForgetOpt.Mu<R>, Either<C, A>, Either<C, B>> right(final Kind2<ForgetOpt.Mu<R>, A, B> input)
        {
            return Optics.forgetOpt(e -> e.right().flatMap(ForgetOpt.unbox(input)::run));
        }

    }

}
