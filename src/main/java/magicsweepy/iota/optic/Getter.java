package magicsweepy.iota.optic;

import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.optic.profunctor.GetterP;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A {@link Getter} is an optic that allows you to focus on a single value within a structure in a read-only manner.
 * <p>
 * It provides a way to extract a value of type {@code A} from a structure of type {@code S} using {@link #get} method.
 * <p>
 * The {@link Getter} interface also includes an inner static class {@link Instance}, which provides an implementation
 * of the {@link GetterP} profunctor for the {@link Getter} optic. This allows for the transformation of getters
 * through the use of functions, enabling composition and manipulation of getters in a functional programming style.
 *
 * @param <S> The source type.
 * @param <T> The modified source type.
 * @param <A> The target type.
 * @param <B> The modified target type.
 */
@NullMarked
public interface Getter<S, T, A, B> extends Kind2<Getter.Mu<A, B>, S, T>, Optic<GetterP.Mu, S, T, A, B>
{

    final class Mu<A, B> implements Ob2 {}

    static <S, T, A, B> Getter<S, T, A, B> unbox(final Kind2<Mu<A, B>, S, T> box)
    {
        return (Getter<S, T, A, B>) box;
    }

    A get(S s);

    @Override
    default <P extends Ob2> Functoid<Kind2<P, A, B>, Kind2<P, S, T>> eval(final Kind<? extends GetterP.Mu, P> proof)
    {
        final GetterP<P, ?> ops = GetterP.unbox(proof);
        return input -> ops.lmap(ops.secondPhantom(input), this::get);
    }

    final class Instance<A2, B2> implements GetterP<Mu<A2, B2>, GetterP.Mu>
    {

        @Override
        public <A, B, C, D> Functoid<Kind2<Getter.Mu<A2, B2>, A, B>, Kind2<Getter.Mu<A2, B2>, C, D>> dimap(final Function<C, A> g,
                                                                                                           final Function<B, D> h)
        {
            return input -> Optics.getter(g.andThen(Getter.unbox(input)::get));
        }

        @Override
        public <A, B, C, D> Functoid<Supplier<Kind2<Getter.Mu<A2, B2>, A, B>>, Kind2<Getter.Mu<A2, B2>, C, D>> cimap(final Function<C, A> g,
                                                                                                                     final Function<D, B> h)
        {
            return input -> Optics.getter(g.andThen(Getter.unbox(input.get())::get));
        }

    }

}