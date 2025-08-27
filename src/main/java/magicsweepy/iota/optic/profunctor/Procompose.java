package magicsweepy.iota.optic.profunctor;

import com.github.bsideup.jabel.Desugar;
import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;
import java.util.function.Supplier;

@NullMarked
@Desugar
public record Procompose<F extends Ob2,
                         G extends Ob2, A, B, C>(Supplier<Kind2<F, A, C>> first,
                                                 Kind2<G, C, B> second) implements Kind2<Procompose.Mu<F, G>, A, B>
{

    public static final class Mu<F extends Ob2, G extends Ob2> implements Ob2 {}

    public static <F extends Ob2, G extends Ob2, A, B> Procompose<F, G, A, B, ?> unbox(final Kind2<Mu<F, G>, A, B> box)
    {
        return (Procompose<F, G, A, B, ?>) box;
    }

    /* package */ static final class ProfunctorInstance<F extends Ob2,
                                                        G extends Ob2> implements Profunctor<Mu<F, G>, Profunctor.Mu>
    {

        private final Profunctor<F, Mu> p1;
        private final Profunctor<G, Mu> p2;

        /* package */ ProfunctorInstance(final Profunctor<F, Mu> p1, final Profunctor<G, Mu> p2)
        {
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public <A, B, C, D> Functoid<Kind2<Procompose.Mu<F, G>, A, B>,
                                           Kind2<Procompose.Mu<F, G>, C, D>> dimap(final Function<C, A> g,
                                                                                   final Function<B, D> h)
        {
            return cmp -> cap(Procompose.unbox(cmp), g, h);
        }

        private <A, B, C, D, E> Kind2<Procompose.Mu<F, G>, C, D> cap(final Procompose<F, G, A, B, E> cmp,
                                                                     final Function<C, A> g,
                                                                     final Function<B, D> h)
        {
            return new Procompose<>(() -> p1.dimap(g, Function.<E>identity()).apply(cmp.first.get()),
                    p2.dimap(Function.<E>identity(), h).apply(cmp.second));
        }

    }

}