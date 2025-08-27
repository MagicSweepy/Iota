package magicsweepy.iota.optic.profunctor;

import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

@NullMarked
public interface GetterP<P extends Ob2, Mu extends GetterP.Mu> extends Profunctor<P, Mu>, Bicontravariant<P, Mu>
{

    interface Mu extends Profunctor.Mu, Bicontravariant.Mu {}

    static <P extends Ob2, Q extends GetterP.Mu> GetterP<P, Q> unbox(final Kind<Q, P> proofBox)
    {
        return (GetterP<P, Q>) proofBox;
    }

    default <A, B, C> Kind2<P, C, A> secondPhantom(final Kind2<P, C, B> input)
    {
        return cimap(() -> rmap(input, b -> (Void) null), Function.identity(), a -> null);
    }

}
