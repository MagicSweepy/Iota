package magicsweepy.iota.kind.maybe;

import lombok.AllArgsConstructor;
import magicsweepy.iota.kind.Applicative;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Monoid;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.kind.Traversable;
import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@NullMarked
public abstract class Maybe<T> implements Kind<Maybe.Mu, T>
{

    public static final class Mu implements Ob {}

    public static <T> Maybe<T> unbox(final Kind<Maybe.Mu, T> box)
    {
        return (Maybe<T>) box;
    }

    public abstract T orElse(T defaultValue);

    public abstract T orElseGet(Supplier<? extends T> getter);

    public abstract <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X;

    public abstract <U> Maybe<U> map(Function<? super T, ? extends U> mapper);

    public abstract <U> Maybe<U> flatMap(Function<T, Maybe<U>> mapper);

    public abstract Optional<T> getValue();

    public abstract Maybe<T> ifJust(Consumer<? super T> consumer);
    public abstract Maybe<T> ifNothing(Runnable runnable);

    public static <T> Maybe<T> just(T value) {
        return new Just<>(value);
    }

    public static <T> Maybe<T> nothing()
    {
        return Unchecks.cast(Nothing.INSTANCE);
    }

    @AllArgsConstructor
    public static final class Just<T> extends Maybe<T>
    {

        private final T value;

        @Override
        public T orElse(T defaultValue)
        {
            return value;
        }

        @Override
        public T orElseGet(Supplier<? extends T> getter)
        {
            return value;
        }

        @Override
        public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X
        {
            return value;
        }

        @Override
        public <U> Maybe<U> map(Function<? super T, ? extends U> mapper)
        {
            return new Just<>(mapper.apply(value));
        }

        @Override
        public <U> Maybe<U> flatMap(Function<T, Maybe<U>> mapper)
        {
            return mapper.apply(value);
        }

        @Override
        public Optional<T> getValue()
        {
            return Optional.of(value);
        }

        @Override
        public Maybe<T> ifJust(Consumer<? super T> consumer)
        {
            consumer.accept(value);
            return this;
        }

        @Override
        public Maybe<T> ifNothing(Runnable runnable)
        {
            return this;
        }

    }

    public static final class Nothing<T> extends Maybe<T>
    {

        private static final Nothing<?> INSTANCE = new Nothing<>();

        @Override
        public T orElse(T defaultValue)
        {
            return defaultValue;
        }

        @Override
        public T orElseGet(Supplier<? extends T> getter)
        {
            return getter.get();
        }

        @Override
        public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X
        {
            throw exceptionSupplier.get();
        }

        @Override
        public Optional<T> getValue()
        {
            return Optional.empty();
        }

        @Override
        public <U> Maybe<U> map(Function<? super T, ? extends U> mapper)
        {
            return nothing();
        }

        @Override
        public <U> Maybe<U> flatMap(Function<T, Maybe<U>> mapper)
        {
            return nothing();
        }

        @Override
        public Maybe<T> ifJust(Consumer<? super T> consumer)
        {
            return this;
        }

        @Override
        public Maybe<T> ifNothing(Runnable runnable)
        {
            runnable.run();
            return this;
        }

    }

    public static final class Instance implements Applicative<Mu, Instance.Mu>, Traversable<Mu, Instance.Mu>
    {

        public static final class Mu implements Applicative.Mu, Traversable.Mu {}

        @Override
        public <A> Kind<Maybe.Mu, A> point(@NonNull A a)
        {
            return new Just<>(a);
        }

        @Override
        public <A, B> Function<Kind<Maybe.Mu, A>, Kind<Maybe.Mu, B>> lift(Kind<Maybe.Mu, Function<A, B>> f)
        {
            return a -> Maybe.unbox(f).flatMap(g -> Maybe.unbox(a).map(g));
        }

        @Override
        public <F extends Ob, A, B> Kind<F, Kind<Maybe.Mu, B>> traverse(Applicative<F, ?> ap,
                                                                        Function<A, Kind<F, B>> f,
                                                                        Kind<Maybe.Mu, A> input)
        {
            Maybe<A> maybe = Maybe.unbox(input);
            if (maybe instanceof Just<A> just)
                return ap.map(Just::new, f.apply(just.value));
            else
                return ap.point(nothing());
        }

        @Override
        public <A, M> M foldMap(Monoid<M> monoid, Function<? super A, ? extends M> f, Kind<Maybe.Mu, A> fa)
        {
            return Maybe.unbox(fa).map(f).orElse(Unchecks.cast(monoid.empty()));
        }

        @Override
        public <T, A> Kind<Maybe.Mu, A> map(Function<? super T, ? extends A> f, Kind<Maybe.Mu, T> fa)
        {
            return Maybe.unbox(fa).map(f);
        }

    }

}
