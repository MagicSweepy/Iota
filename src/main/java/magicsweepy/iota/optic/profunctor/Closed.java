package magicsweepy.iota.optic.profunctor;

import com.google.common.reflect.TypeToken;
import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings("UnstableApiUsage")
public interface Closed<P extends Ob2, Mu extends Closed.Mu> extends Profunctor<P, Mu>
{

    static <P extends Ob2, Proof extends Closed.Mu> Closed<P, Proof> unbox(final Kind<Proof, P> proofBox)
    {
        return (Closed<P, Proof>) proofBox;
    }

    interface Mu extends Profunctor.Mu
    {
        TypeToken<Mu> TYPE = new TypeToken<>() {};
    }

    <A, B, X> Kind2<P, Functoid<X, A>, Functoid<X, B>> close(final Kind2<P, A, B> input);

}
