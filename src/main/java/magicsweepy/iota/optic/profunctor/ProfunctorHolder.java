package magicsweepy.iota.optic.profunctor;

import com.github.bsideup.jabel.Desugar;
import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Functor;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.kind.Ob2;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

@NullMarked
@Desugar
public record ProfunctorHolder<P extends Ob2, F extends Ob, G extends Ob, A, B>(Kind2<P, Kind<F, A>, Kind<G, B>> value)
    implements Kind2<ProfunctorHolder.Mu<P, F, G>, A, B>
{

    public static final class Mu<P extends Ob2, F extends Ob, G extends Ob> implements Ob2 {}

    public static <P extends Ob2, F extends Ob, G extends Ob, A, B> ProfunctorHolder<P, F, G, A, B> unbox(final Kind2<ProfunctorHolder.Mu<P, F, G>, A, B> box)
    {
        return (ProfunctorHolder<P, F, G, A, B>) box;
    }

    public static final class Instance<P extends Ob2, F extends Ob, G extends Ob>
        implements Profunctor<ProfunctorHolder.Mu<P, F, G>, Instance.Mu>, Kind<Instance.Mu, Mu<P, F, G>>
    {

        public static final class Mu implements Profunctor.Mu {}

        private final Profunctor<P, ? extends Profunctor.Mu> profunctor;
        private final Functor<F, ?> fFunctor;
        private final Functor<G, ?> gFunctor;

        public Instance(final Kind<? extends Profunctor.Mu, P> proof,
                        final Functor<F, ?> fFunctor,
                        final Functor<G, ?> gFunctor)
        {
            this.profunctor = Profunctor.unbox(proof);
            this.fFunctor = fFunctor;
            this.gFunctor = gFunctor;
        }

        @Override
        public <A, B, C, D> Functoid<Kind2<ProfunctorHolder.Mu<P, F, G>, A, B>,
                                     Kind2<ProfunctorHolder.Mu<P, F, G>, C, D>> dimap(final Function<C, A> g, final Function<B, D> h)
        {
            return input -> {
                final Kind2<P, Kind<F, A>, Kind<G, B>> value = ProfunctorHolder.unbox(input).value();
                final Kind2<P, Kind<F, C>, Kind<G, D>> newValue = profunctor.dimap(value,
                        c -> fFunctor.map(g, c), b -> gFunctor.map(h, b));
                return new ProfunctorHolder<>(newValue);
            };
        }

    }

}
