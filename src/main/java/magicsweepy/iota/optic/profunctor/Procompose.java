package magicsweepy.iota.optic.profunctor;

import com.github.bsideup.jabel.Desugar;
import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The composition of two profunctors.
 * <p>
 * Given two profunctors {@code F} and {@code G}, their composition {@code F ⊛ G} is defined as:
 * <pre>{@code
 *     (F ⊛ G)(A, B) = ∃C. F(A, C) × G(C, B)
 * }</pre>
 * where {@code ∃C} denotes the existence of an intermediate type {@code C}, and {@code ×} denotes a product type (pair).
 * <p>
 * This means that an element of {@code F ⊛ G} from {@code A} to {@code B} consists of:
 * <ul>
 *     <li>A value of type {@code F(A, C)} for some intermediate type {@code C}, and</li>
 *     <li>A value of type {@code G(C, B)} for the same intermediate type {@code C}.</li>
 * </ul>
 * <p>
 * The profunctor instance for the composition is defined by mapping over both components using the respective profunctor instances.
 *
 * @see Profunctor
 *
 * @param <F> The first profunctor.
 * @param <G> The second profunctor.
 * @param <A> The input type.
 * @param <B> The output type.
 * @param <C> The intermediate type.
 */
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