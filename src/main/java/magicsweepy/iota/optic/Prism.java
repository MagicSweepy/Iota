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
 * A {@link Prism} is an optic used to focus on a part of a data structure that may or may not be present.
 * <p>
 * It is often used with sum types (e.g., union types, sealed classes) to access one of the possible variants.
 * <p>
 * A {@link Prism} can be thought of as a combination of a partial getter and a constructor:
 * <ul>
 *     <li>The {@link #match(S)} method attempts to extract a value of type {@code A} from a value of type {@code S}.
 *     If the value is not present, it returns a value of type {@code T} instead.</li>
 *     <li>The {@link #build(B)} method constructs a value of type {@code T} from a value of type {@code B}.</li>
 * </ul>
 *
 * @param <S> The source type from which we want to extract a value.
 * @param <T> The target type to which we can construct a value.
 * @param <A> The type of the value that can be extracted (the focus).
 * @param <B> The type of the value used to construct the target.
 */
@NullMarked
public interface Prism<S, T, A, B> extends Kind2<Prism.Mu<A, B>, S, T>, Optic<Cocartesian.Mu, S, T, A, B>
{

    final class Mu<A, B> implements Ob2 {}

    static <S, T, A, B> Prism<S, T, A, B> unbox(final Kind2<Mu<A, B>, S, T> box)
    {
        return (Prism<S, T, A, B>) box;
    }

    Either<T, A> match(final S s);

    T build(final B b);

    @Override
    default <P extends Ob2> Functoid<Kind2<P, A, B>, Kind2<P, S, T>> eval(final Kind<? extends Cocartesian.Mu, P> proof)
    {
        final Cocartesian<P, ? extends Cocartesian.Mu> cocartesian = Cocartesian.unbox(proof);
        return input -> cocartesian.dimap(cocartesian.right(input), this::match,
            (Either<T, B> a) -> a.map(Function.identity(), this::build));
    }

    final class Instance<A2, B2> implements Cocartesian<Mu<A2, B2>, Cocartesian.Mu>
    {

        @Override
        public <A, B, C, D> Functoid<Kind2<Prism.Mu<A2, B2>, A, B>, Kind2<Prism.Mu<A2, B2>, C, D>> dimap(final Function<C, A> g,
                                                                                                         final Function<B, D> h)
        {
            return prismBox -> Optics.prism(
                    (C c) -> Prism.unbox(prismBox).match(g.apply(c)).mapLeft(h),
                    (B2 b) -> h.apply(Prism.unbox(prismBox).build(b)));
        }

        @Override
        public <A, B, C> Kind2<Prism.Mu<A2, B2>, Either<A, C>, Either<B, C>> left(final Kind2<Prism.Mu<A2, B2>, A, B> input)
        {
            final Prism<A, B, A2, B2> prism = Prism.unbox(input);
            return Optics.prism((Either<A, C> either) -> either.map(
                    a -> prism.match(a).mapLeft(Either::left),
                    c -> Either.left(Either.right(c))),
                (B2 b) -> Either.left(prism.build(b)));
        }

        @Override
        public <A, B, C> Kind2<Prism.Mu<A2, B2>, Either<C, A>, Either<C, B>> right(final Kind2<Prism.Mu<A2, B2>, A, B> input)
        {
            final Prism<A, B, A2, B2> prism = Prism.unbox(input);
            return Optics.prism((Either<C, A> either) -> either.map(
                    c -> Either.left(Either.left(c)),
                    a -> prism.match(a).mapLeft(Either::right)),
                (B2 b) -> Either.right(prism.build(b)));
        }

    }

}
