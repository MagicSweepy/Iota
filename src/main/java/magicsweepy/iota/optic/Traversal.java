package magicsweepy.iota.optic;

import magicsweepy.iota.kind.Applicative;
import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.optic.profunctor.TraversalP;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

@NullMarked
public interface Traversal<S, T, A, B> extends Wander<S, T, A, B>, Kind2<Traversal.Mu<A, B>, S, T>, Optic<TraversalP.Mu, S, T, A, B>
{

    final class Mu<A, B> implements Ob2 {}

    static <S, T, A, B> Traversal<S, T, A, B> unbox(final Kind2<Mu<A, B>, S, T> box)
    {
        return (Traversal<S, T, A, B>) box;
    }

    @Override
    default <P extends Ob2> Functoid<Kind2<P, A, B>, Kind2<P, S, T>> eval(final Kind<? extends TraversalP.Mu, P> proof)
    {
        final TraversalP<P, ? extends TraversalP.Mu> proof1 = TraversalP.unbox(proof);
        return input -> proof1.wander(this, input);
    }

    final class Instance<A2, B2> implements TraversalP<Mu<A2, B2>, TraversalP.Mu>
    {

        @Override
        public <A, B, C, D> Functoid<Kind2<Traversal.Mu<A2, B2>, A, B>, Kind2<Traversal.Mu<A2, B2>, C, D>> dimap(final Function<C, A> g,
                                                                                                                 final Function<B, D> h)
        {
            return tr -> new Traversal<>()
            {

                @Override
                public <F extends Ob> Functoid<C, Kind<F, D>> wander(final Applicative<F, ?> applicative,
                                                                     final Functoid<A2, Kind<F, B2>> input)
                {
                    return c -> applicative.map(h, Traversal.unbox(tr).wander(applicative, input).apply(g.apply(c)));
                }

            };
        }

        @Override
        public <S, T, A, B> Kind2<Traversal.Mu<A2, B2>, S, T> wander(final Wander<S, T, A, B> wander,
                                                                     final Kind2<Traversal.Mu<A2, B2>, A, B> input)
        {
            return new Traversal<>()
            {

                @Override
                public <F extends Ob> Functoid<S, Kind<F, T>> wander(final Applicative<F, ?> applicative,
                                                                     final Functoid<A2, Kind<F, B2>> function)
                {
                    return wander.wander(applicative, unbox(input).wander(applicative, function));
                }

            };
        }

    }

}
