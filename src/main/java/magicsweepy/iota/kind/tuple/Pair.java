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
public record Pair<F, S>(@Getter F first, @Getter S second) implements Kind<Pair.@NonNull Mu<S>, F>
{

    public static final class Mu<S> implements Ob {}

    public static <F, S> Pair<F, S> unbox(final Kind<Pair.@NonNull Mu<S>, F> box)
    {
        return (Pair<F, S>) box;
    }

    public static <F, S> Pair<F, S> of(final F first, final S second)
    {
        return new Pair<>(first, second);
    }

    public static <F, S> Collector<Pair<F, S>, ?, Map<F, S>> toMap()
    {
        return Collectors.toMap(Pair::first, Pair::second);
    }

    @NonNull
    public Pair<S, F> swap()
    {
        return of(second, first);
    }

    @NonNull
    public <F2> Pair<F2, S> mapFirst(final Function<? super F, ? extends F2> f)
    {
        return of(f.apply(first), second);
    }

    @NonNull
    public <S2> Pair<F, S2> mapSecond(final Function<? super S, ? extends S2> f)
    {
        return of(first, f.apply(second));
    }

    @NullMarked
    public static final class Instance<S2> implements Traversable<Pair.Mu<S2>, Instance.Mu<S2>>,
                                                      CartesianLike<Pair.Mu<S2>, S2, Instance.Mu<S2>>
    {

        public static final class Mu<S2> implements Traversable.Mu, CartesianLike.Mu {}

        @Override
        public <T, A> Kind<Pair.Mu<S2>, A> map(Function<? super T, ? extends A> f,
                                                             Kind<Pair.Mu<S2>, T> fa)
        {
            return Pair.unbox(fa).mapFirst(f);
        }

        @Override
        public <F extends Ob, A, B> Kind<F, Kind<Pair.Mu<S2>, B>> traverse(Applicative<F, ?> ap,
                                                                           Function<A, Kind<F, B>> f,
                                                                           Kind<Pair.Mu<S2>, A> input)
        {
            final Pair<A, S2> pair = Pair.unbox(input);
            return ap.ap(b -> of(b, pair.second), f.apply(pair.first));
        }

        @Override
        public <A, M> M foldMap(Monoid<M> monoid,
                                Function<? super A, ? extends M> f,
                                Kind<Pair.Mu<S2>, A> fa)
        {
            final Pair<A, S2> pair = Pair.unbox(fa);
            return monoid.combine(monoid.empty(), f.apply(pair.first));
        }

        @Override
        public <A> Kind<Pair.Mu<S2>, A> to(Kind<Pair.Mu<S2>, A> input)
        {
            return input;
        }

        @Override
        public <A> Kind<Pair.Mu<S2>, A> from(Kind<Pair.Mu<S2>, A> input)
        {
            return input;
        }

    }

}
