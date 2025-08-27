package magicsweepy.iota.optic;

import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.optic.profunctor.Profunctor;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

/**
 * An {@code Adapter} is an optic that represents an isomorphism between two types.
 * <p>
 * It allows you to convert back and forth between the source type {@code S} and the target type {@code A}, as well as
 * between the modified source type {@code T} and the modified target type {@code B}.
 * <p>
 * An {@code Adapter} can be thought of as a pair of functions:
 * <ul>
 *     <li>{@link #from(S)}: Converts a value of type {@code S} to a value of type {@code A}.</li>
 *     <li>{@link #to(B)}: Converts a value of type {@code B} to a value of type {@code T}.</li>
 * </ul>
 * <p>
 * This optic is useful when you have two types that can be converted back and forth without loss of information.
 * It provides a way to work with these types in a composable manner, allowing you to build more complex optics
 * by combining simpler ones.
 *
 * @param <S> The source type.
 * @param <T> The modified source type.
 * @param <A> The target type.
 * @param <B> The modified target type.
 */
@NullMarked
public interface Adapter<S, T, A, B> extends Kind2<Adapter.Mu<A, B>, S, T>, Optic<Profunctor.Mu, S, T, A, B>
{

    final class Mu<A, B> implements Ob2 {}

    static <S, T, A, B> Adapter<S, T, A, B> unbox(final Kind2<Mu<A, B>, S, T> box)
    {
        return (Adapter<S, T, A, B>) box;
    }

    A from(final S s);

    T to(final B b);

    @Override
    default <P extends Ob2> Functoid<Kind2<P, A, B>, Kind2<P, S, T>> eval(final Kind<? extends Profunctor.Mu, P> proofBox)
    {
        final Profunctor<P, ? extends Profunctor.Mu> proof = Profunctor.unbox(proofBox);
        return a -> proof.dimap(a, this::from, this::to);
    }

    final class Instance<A2, B2> implements Profunctor<Mu<A2, B2>, Profunctor.Mu>
    {

        @Override
        public <A, B, C, D> Functoid<Kind2<Adapter.Mu<A2, B2>, A, B>, Kind2<Adapter.Mu<A2, B2>, C, D>> dimap(final Function<C, A> g,
                                                                                                             final Function<B, D> h)
        {
            return a -> Optics.adapter(c -> Adapter.unbox(a).from(g.apply(c)),
                b2 -> h.apply(Adapter.unbox(a).to(b2)));
        }

    }

}
