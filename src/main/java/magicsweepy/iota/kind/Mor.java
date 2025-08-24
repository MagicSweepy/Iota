package magicsweepy.iota.kind;

import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface Mor<F extends Ob, Mu extends Mor.Mu> extends Kind<Mor.Mu, F>
{

    interface Mu extends Ob {}

    static <F extends Ob, Mu extends Mor.Mu> Mor<F, Mu> unbox(final Kind<Mu, F> box)
    {
        return Unchecks.cast(box);
    }

}
