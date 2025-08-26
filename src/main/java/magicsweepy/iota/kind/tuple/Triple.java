package magicsweepy.iota.kind.tuple;

import com.github.bsideup.jabel.Desugar;
import lombok.Getter;
import magicsweepy.iota.kind.Applicative;
import magicsweepy.iota.kind.CartesianLike;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Monoid;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.kind.Traversable;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Desugar
public record Triple<F, S, T>(@Getter F first, @Getter S second, @Getter T third) implements Kind<Triple.@NonNull Mu<S, T>, F>
{

    public static final class Mu<S, T> implements Ob {}

    public static <F, S, T> Triple<F, S, T> unbox(final Kind<Triple. @NonNull Mu<S, T>, F> box)
    {
        return (Triple<F, S, T>) box;
    }

    public static <F, S, T> Triple<F, S, T> of(final F first, final S second, final T third)
    {
        return new Triple<>(first, second, third);
    }

    public static <F, S, T> Collector<Triple<F, S, T>, ?, Map<F, Pair<S, T>>> toMap()
    {
        return Collectors.toMap(Triple::first, triple -> Pair.of(triple.second(), triple.third()));
    }

    @NonNull
    public Triple<T, S, F> swap()
    {
        return of(third, second, first);
    }

    @NonNull
    public <F2> Triple<F2, S, T> mapFirst(Function<? super F, ? extends F2> f)
    {
        return of(f.apply(first), second, third);
    }

    @NonNull
    public <S2> Triple<F, S2, T> mapSecond(Function<? super S, ? extends S2> f)
    {
        return of(first, f.apply(second), third);
    }

    @NonNull
    public <T2> Triple<F, S, T2> mapThird(Function<? super T, ? extends T2> f)
    {
        return of(first, second, f.apply(third));
    }

    @NullMarked
    public static final class Instance<S2, T2> implements Traversable<Mu<S2, T2>, Instance.Mu<S2, T2>>,
                                                          CartesianLike<Mu<S2, T2>, Pair<S2, T2>, Instance.Mu<S2, T2>>
    {

        public static final class Mu<S2, T2> implements Traversable.Mu, CartesianLike.Mu {}

        @Override
        public <A, B> Kind<Triple.Mu<S2, T2>, B> map(Function<? super A, ? extends B> f,
                                                                  Kind<Triple.Mu<S2, T2>, A> fa)
        {
            return Triple.unbox(fa).mapFirst(f);
        }

        @Override
        public <F extends Ob, A, B> Kind<F, Kind<Triple.Mu<S2, T2>, B>> traverse(Applicative<F, ?> ap,
                                                                                 Function<A, Kind<F, B>> f,
                                                                                 Kind<Triple.Mu<S2, T2>, A> input)
        {
            final Triple<A, S2, T2> triple = Triple.unbox(input);
            return ap.ap(b -> Triple.of(b, triple.second(), triple.third()), f.apply(triple.first()));
        }

        @Override
        public <A, M> M foldMap(Monoid<M> monoid, Function<? super A, ? extends M> f,
                                Kind<Triple.Mu<S2, T2>, A> fa)
        {
            final Triple<A, S2, T2> triple = Triple.unbox(fa);
            return monoid.combine(monoid.empty(), f.apply(triple.first));
        }

        @Override
        public <A> Kind<Triple.Mu<S2, T2>, A> from(Kind<Pair.Mu<Pair<S2, T2>>, A> input)
        {
            final Pair<A, Pair<S2, T2>> pair = Pair.unbox(input);
            return Triple.of(pair.first(), pair.second().first(), pair.second().second());
        }

        @Override
        public <A> Kind<Pair.Mu<Pair<S2, T2>>, A> to(Kind<Triple.Mu<S2, T2>, A> input)
        {
            final Triple<A, S2, T2> triple = Triple.unbox(input);
            return Pair.of(triple.first(), Pair.of(triple.second(), triple.third()));
        }

    }

}
