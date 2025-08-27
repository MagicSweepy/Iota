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

/**
 * A {@code Profunctor} is a type class that generalizes the concept of a {@code Functor}s to {@code BiFunctor}s.
 * <p>
 * It allows you to map over both the input and output types of a bifunctor. Formally, a Profunctor is defined by the
 * following operations:
 * <ul>
 *     <li><strong>dimap</strong>: This operation takes two functions, one for the input type and one for the output
 *         type, and transforms a value of the profunctor type accordingly.</li>
 * </ul>
 * <p>
 * The laws that a Profunctor must satisfy are:
 * <ul>
 *     <li><strong>Identity Law</strong>: dimap with identity functions should be equivalent to the original profunctor
 *     value. Mathematically: <tt>dimap(id, id) = id</tt></li>
 *     <li><strong>Composition Law</strong>: dimap should preserve function composition. That is, if you first map with
 *     two functions and then with another two functions, it should be the same as mapping once with the composition
 *     of those functions. Mathematically: <tt>dimap(f2, g2) . dimap(f1, g1) = dimap(f1 . f2, g1 . g2)</tt></li>
 * </ul>
 * <p>
 * {@code Profunctor}s are used in various areas of functional programming, including optics (like {@code Lens}s and
 * {@code Prism}s), where they help in abstracting over data transformations in a composable way.
 *
 * @param <P>  The bifunctor type that this {@code Profunctor} instance operates on.
 * @param <Mu> A marker type to distinguish different {@code Profunctor} instances.
 */
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
