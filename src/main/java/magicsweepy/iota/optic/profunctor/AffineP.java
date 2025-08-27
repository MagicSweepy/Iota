package magicsweepy.iota.optic.profunctor;

import com.google.common.reflect.TypeToken;
import magicsweepy.iota.kind.Ob2;
import org.jspecify.annotations.NullMarked;

@SuppressWarnings("UnstableApiUsage")
@NullMarked
public interface AffineP<P extends Ob2, Mu extends AffineP.Mu> extends Cartesian<P, Mu>, Cocartesian<P, Mu>
{

    interface Mu extends Cartesian.Mu, Cocartesian.Mu
    {

        TypeToken<Mu> TYPE = new TypeToken<>() {};

    }

}
