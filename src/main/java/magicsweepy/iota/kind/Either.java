package magicsweepy.iota.kind;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Either<L, R> implements Kind<Either.@NonNull Mu<R>, L>
{

    public static final class Mu<R> implements Ob {}

    public static <L, R> Either<L, R> unbox(final Kind<Either.@NonNull Mu<R>, L> box)
    {
        return (Either<L, R>) box;
    }

    public abstract <C, D> Either<C, D> mapBoth(final Function<? super L, ? extends C> f1,
                                                final Function<? super R, ? extends D> f2);

    public abstract <T> T map(final Function<? super L, ? extends T> l, Function<? super R, ? extends T> r);

    public abstract Either<L, R> ifLeft(final Consumer<? super L> consumer);

    public abstract Either<L, R> ifRight(final Consumer<? super R> consumer);

    public abstract Optional<L> left();

    public abstract Optional<R> right();

    public <T> Either<T, R> mapLeft(final Function<? super L, ? extends T> l)
    {
        return map(t -> left(l.apply(t)), Either::right);
    }

    public <T> Either<L, T> mapRight(final Function<? super R, ? extends T> l)
    {
        return map(Either::left, t -> right(l.apply(t)));
    }

    public static <L, R> Either<L, R> left(final L value)
    {
        return new Left<>(value);
    }

    public static <L, R> Either<L, R> right(final R value)
    {
        return new Right<>(value);
    }

    public L orThrow()
    {
        return map(l -> l, r -> {
            if (r instanceof Throwable)
                throw new RuntimeException((Throwable) r);
            throw new RuntimeException(r.toString());
        });
    }

    public Either<R, L> swap()
    {
        return map(Either::right, Either::left);
    }

    public <L2> Either<L2, R> flatMap(final Function<L, Either<L2, R>> function)
    {
        return map(function, Either::right);
    }

    public static <U> U unwrap(final Either<? extends U, ? extends U> either)
    {
        return either.map(Function.identity(), Function.identity());
    }

    @Getter
    @AllArgsConstructor
    private static final class Left<L, R> extends Either<L, R>
    {

        private final L value;

        @Override
        public <C, D> Either<C, D> mapBoth(final Function<? super L, ? extends C> f1,
                                           final Function<? super R, ? extends D> f2)
        {
            return new Left<>(f1.apply(value));
        }

        @Override
        public <T> T map(final Function<? super L, ? extends T> l,
                         final Function<? super R, ? extends T> r)
        {
            return l.apply(value);
        }

        @Override
        public Either<L, R> ifLeft(Consumer<? super L> consumer)
        {
            consumer.accept(value);
            return this;
        }

        @Override
        public Either<L, R> ifRight(Consumer<? super R> consumer)
        {
            return this;
        }

        @Override
        public Optional<L> left()
        {
            return Optional.of(value);
        }

        @Override
        public Optional<R> right()
        {
            return Optional.empty();
        }

        @Override
        public String toString()
        {
            return "Left[" + value + "]";
        }

        @Override
        public boolean equals(final Object o)
        {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            final Left<?, ?> left = (Left<?, ?>) o;
            return Objects.equals(value, left.value);
        }

        @Override
        public int hashCode()
        {
            return value.hashCode();
        }

    }

    @Getter
    @AllArgsConstructor
    private static final class Right<L, R> extends Either<L, R>
    {

        private final R value;

        @Override
        public <C, D> Either<C, D> mapBoth(final Function<? super L, ? extends C> f1,
                                           final Function<? super R, ? extends D> f2)
        {
            return new Right<>(f2.apply(value));
        }

        @Override
        public <T> T map(final Function<? super L, ? extends T> l,
                         final Function<? super R, ? extends T> r)
        {
            return r.apply(value);
        }

        @Override
        public Either<L, R> ifLeft(Consumer<? super L> consumer)
        {
            return this;
        }

        @Override
        public Either<L, R> ifRight(Consumer<? super R> consumer)
        {
            consumer.accept(value);
            return this;
        }

        @Override
        public Optional<L> left()
        {
            return Optional.empty();
        }

        @Override
        public Optional<R> right()
        {
            return Optional.of(value);
        }

        @Override
        public String toString()
        {
            return "Right[" + value + "]";
        }

        @Override
        public boolean equals(final Object o)
        {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            final Right<?, ?> right = (Right<?, ?>) o;
            return Objects.equals(value, right.value);
        }

        @Override
        public int hashCode()
        {
            return value.hashCode();
        }

    }

    @NullMarked
    public static final class Instance<R2> implements Applicative<Either.Mu<R2>, Instance.Mu<R2>>,
                                                      Traversable<Either.Mu<R2>, Instance.Mu<R2>>,
                                                      CocartesianLike<Either.Mu<R2>, R2, Instance.Mu<R2>>
    {

        public static final class Mu<R2> implements Applicative.Mu, Traversable.Mu, CocartesianLike.Mu {}

        @Override
        public <T, R> Kind<Either.Mu<R2>, R> map(final Function<? super T, ? extends R> func,
                                                 final Kind<Either.Mu<R2>, T> ts)
        {
            return Either.unbox(ts).mapLeft(func);
        }

        @Override
        public <A> Kind<Either.Mu<R2>, A> point(final A a)
        {
            return left(a);
        }

        @Override
        public <A, R> Function<Kind<Either.Mu<R2>, A>, Kind<Either.Mu<R2>, R>> lift(final Kind<Either.Mu<R2>, Function<A, R>> f)
        {
            return a -> Either.unbox(f).flatMap(g -> Either.unbox(a).mapLeft(g));
        }

        @Override
        public <A, B, R> BiFunction<Kind<Either.Mu<R2>, A>, Kind<Either.Mu<R2>, B>, Kind<Either.Mu<R2>, R>> lift2(final Kind<Either.Mu<R2>, BiFunction<A, B, R>> f)
        {
            return (a, b) -> Either.unbox(f)
                    .flatMap(g -> Either.unbox(a)
                            .flatMap(av -> Either.unbox(b)
                                    .mapLeft(bv -> g.apply(av, bv))));
        }

        @Override
        public <F extends Ob, A, B> Kind<F, Kind<Either.Mu<R2>, B>> traverse(final Applicative<F, ?> applicative, final Function<A, Kind<F, B>> function,
                                                                             final Kind<Either.Mu<R2>, A> input)
        {
            return Either.unbox(input)
                    .map(l -> {
                        final Kind<F, B> b = function.apply(l);
                        return applicative.ap(Either::left, b);
                        }, r -> applicative.point(right(r))
            );
        }

        @Override
        public <A, M> M foldMap(Monoid<M> monoid, Function<? super A, ? extends M> f,
                                Kind<Either.Mu<R2>, A> fa)
        {
            return Either.unbox(fa).map(f, t -> monoid.point());
        }

        @Override
        public <A> Kind<Either.Mu<R2>, A> from(final Kind<Either.Mu<R2>, A> input)
        {
            return input;
        }

        @Override
        public <A> Kind<Either.Mu<R2>, A> to(final Kind<Either.Mu<R2>, A> input)
        {
            return input;
        }

    }

}
