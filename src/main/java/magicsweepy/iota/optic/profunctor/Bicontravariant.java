package magicsweepy.iota.optic.profunctor;

import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Mor2;
import magicsweepy.iota.kind.Ob2;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;
import java.util.function.Supplier;

@NullMarked
interface Bicontravariant<P extends Ob2, Mu extends Bicontravariant.Mu> extends Mor2<P, Mu>
{

    interface Mu extends Mor2.Mu {}

    static <P extends Ob2, Q extends Bicontravariant.Mu> Bicontravariant<P, Q> unbox(final Kind<Q, P> proofBox)
    {
        return (Bicontravariant<P, Q>) proofBox;
    }

    <A, B, C, D> Functoid<Supplier<Kind2<P, A, B>>, Kind2<P, C, D>> cimap(final Function<C, A> g, final Function<D, B> h);

    default <A, B, C, D> Kind2<P, C, D> cimap(final Supplier<Kind2<P, A, B>> arg, final Function<C, A> g, final Function<D, B> h)
    {
        return cimap(g, h).apply(arg);
    }

}
