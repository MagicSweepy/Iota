package magicsweepy.iota.optic.profunctor;

import com.google.common.reflect.TypeToken;
import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Mor2;
import magicsweepy.iota.kind.Ob2;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("UnstableApiUsage")
@NullMarked
public interface Profunctor<P extends Ob2, Mu extends Profunctor.Mu> extends Mor2<P, Mu>
{

    interface Mu extends Mor2.Mu
    {
        TypeToken<Mu> TYPE = new TypeToken<>() {};
    }

    static <P extends Ob2, Q extends Profunctor.Mu> Profunctor<P, Q> unbox(final Kind<Q, P> box)
    {
        return (Profunctor<P, Q>) box;
    }

    /**
     * <pre>{@code
     *      C - g -> A
     *  K2  |        | K2
     *      D <- h - B
     * }</pre>
     */
    <A, B, C, D> Functoid<Kind2<P, A, B>, Kind2<P, C, D>> dimap(final Function<C, A> g, final Function<B, D> h);

    default <A, B, C, D> Kind2<P, C, D> dimap(final Kind2<P, A, B> arg, final Function<C, A> g, final Function<B, D> h)
    {
        return dimap(g, h).apply(arg);
    }

    default <A, B, C, D> Kind2<P, C, D> dimap(final Supplier<Kind2<P, A, B>> arg,
                                              final Function<C, A> g, final Function<B, D> h)
    {
        return dimap(g, h).apply(arg.get());
    }

    /**
     * <pre>{@code
     *     A - K2 -> B
     *   g |         | K2
     *     C --------|
     * }</pre>
     */
    default <A, B, C> Kind2<P, C, B> lmap(final Kind2<P, A, B> input, final Function<C, A> g)
    {
        return dimap(input, g, Function.identity());
    }

    /**
     * <pre>{@code
     *     A - K2 -> B
     *  K2 |         | h
     *     |-------- D
     * }</pre>
     */
    default <A, B, D> Kind2<P, A, D> rmap(final Kind2<P, A, B> input, final Function<B, D> h)
    {
        return dimap(input, Function.identity(), h);
    }

}
