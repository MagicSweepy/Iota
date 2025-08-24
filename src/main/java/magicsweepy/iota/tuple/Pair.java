package magicsweepy.iota.tuple;

import com.github.bsideup.jabel.Desugar;
import lombok.Getter;
import magicsweepy.iota.kind.Kind;

import java.util.function.Function;

/**
 * {@code F -> Mu<S>(F)}
 */
@Desugar
public record Pair<F, S>(@Getter F first, @Getter S second) implements Kind<Pair.Mu<S>, F>
{

    public static final class Mu<S> {}

    public static <F, S> Pair<F, S> unbox(final Kind<Pair.Mu<S>, F> box)
    {
        return (Pair<F, S>) box;
    }

    public static <F, S> Pair<F, S> of(final F first, final S second)
    {
        return new Pair<>(first, second);
    }

    public Pair<S, F> swap()
    {
        return of(second, first);
    }

    public <F2> Pair<F2, S> mapFirst(final Function<? super F, ? extends F2> function)
    {
        return of(function.apply(first), second);
    }

    public <S2> Pair<F, S2> mapSecond(final Function<? super S, ? extends S2> function)
    {
        return of(first, function.apply(second));
    }

    // TODO Traversable Instance?

}
