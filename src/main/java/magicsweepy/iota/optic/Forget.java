package magicsweepy.iota.optic;

import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.kind.either.Either;
import magicsweepy.iota.kind.tuple.Pair;
import magicsweepy.iota.optic.profunctor.Cartesian;
import magicsweepy.iota.optic.profunctor.ReCocartesian;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

/**
 * A {@link Forget} optic is used to "forget" the focus of an optic, effectively allowing you to extract information
 * from the source type without modifying it.
 * <p>
 * It is often used in scenarios where you want to derive some value from a complex structure without changing the
 * structure itself. The {@code Forget} optic can be particularly useful in functional programming and optics libraries
 * for tasks such as data extraction, transformation, and analysis.
 *
 * @param <R> The type of the result produced by the {@code Forget} optic.
 * @param <A> The source type from which the result is derived.
 * @param <B> The target type, which is not used in the {@code Forget} optic but is part of its signature.
 */
@NullMarked
public interface Forget<R, A, B> extends Kind2<Forget.Mu<R>, A, B>
{

    final class Mu<R> implements Ob2 {}

    static <R, A, B> Forget<R, A, B> unbox(final Kind2<Mu<R>, A, B> box)
    {
        return (Forget<R, A, B>) box;
    }

    R run(final A a);

    final class Instance<R> implements Cartesian<Mu<R>, Instance.Mu<R>>,
                                       ReCocartesian<Mu<R>, Instance.Mu<R>>,
                                       Kind<Instance.Mu<R>, Mu<R>>
    {

        public static final class Mu<R> implements Cartesian.Mu, ReCocartesian.Mu {}

        @Override
        public <A, B, C, D> Functoid<Kind2<Forget.Mu<R>, A, B>, Kind2<Forget.Mu<R>, C, D>> dimap(final Function<C, A> g,
                                                                                                 final Function<B, D> h)
        {
            return input -> Optics.forget(c -> Forget.unbox(input).run(g.apply(c)));
        }

        @Override
        public <A, B, C> Kind2<Forget.Mu<R>, Pair<A, C>, Pair<B, C>> first(final Kind2<Forget.Mu<R>, A, B> input)
        {
            return Optics.forget(p -> Forget.unbox(input).run(p.first()));
        }

        @Override
        public <A, B, C> Kind2<Forget.Mu<R>, Pair<C, A>, Pair<C, B>> second(final Kind2<Forget.Mu<R>, A, B> input)
        {
            return Optics.forget(p -> Forget.unbox(input).run(p.second()));
        }

        @Override
        public <A, B, C> Kind2<Forget.Mu<R>, A, B> unleft(final Kind2<Forget.Mu<R>, Either<A, C>, Either<B, C>> input)
        {
            return Optics.forget(a -> Forget.unbox(input).run(Either.left(a)));
        }

        @Override
        public <A, B, C> Kind2<Forget.Mu<R>, A, B> unright(final Kind2<Forget.Mu<R>, Either<C, A>, Either<C, B>> input)
        {
            return Optics.forget(a -> Forget.unbox(input).run(Either.right(a)));
        }

    }

}
