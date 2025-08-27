package magicsweepy.iota.optic;

import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.optic.profunctor.Closed;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

@NullMarked
public interface Grate<S, T, A, B> extends Kind2<Grate.Mu<A, B>, S, T>, Optic<Closed.Mu, S, T, A, B>
{

    final class Mu<A, B> implements Ob2 {}

    static <S, T, A, B> Grate<S, T, A, B> unbox(final Kind2<Mu<A, B>, S, T> box)
    {
        return (Grate<S, T, A, B>) box;
    }

    T grate(final Functoid<Functoid<S, A>, B> f);

    @Override
    default <P extends Ob2> Functoid<Kind2<P, A, B>, Kind2<P, S, T>> eval(final Kind<? extends Closed.Mu, P> proof)
    {
        final Closed<P, ?> ops = Closed.unbox(proof);
        return input -> ops.dimap(ops.close(input), s -> f -> f.apply(s), this::grate);
    }

    final class Instance<A2, B2> implements Closed<Mu<A2, B2>, Closed.Mu>
    {

        @Override
        public <A, B, C, D> Functoid<Kind2<Grate.Mu<A2, B2>, A, B>, Kind2<Grate.Mu<A2, B2>, C, D>> dimap(final Function<C, A> g,
                                                                                                           final Function<B, D> h)
        {
            return input -> Optics.grate(f -> h.apply(Grate.unbox(input)
                    .grate(fa -> f.apply(Functoid.create(fa.compose(g))))));
        }

        @Override
        public <A, B, X> Kind2<Grate.Mu<A2, B2>, Functoid<X, A>, Functoid<X, B>> close(final Kind2<Grate.Mu<A2, B2>, A, B> input)
        {
            final Functoid<Functoid<Functoid<Functoid<X, A>, A>, B>, Functoid<X, B>> func = f1 -> x -> f1.apply(f2 -> f2.apply(x));
            return Optics.grate(func).eval(this).apply(Grate.unbox(input));
        }

    }

}
