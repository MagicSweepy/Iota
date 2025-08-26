package magicsweepy.iota.kind;

import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface Mor2<F extends Ob2, Mu extends Mor2.Mu> extends Kind<Mu, F>
{

    interface Mu extends Ob {}

    static <F extends Ob2, Mu extends Mor2.Mu> Mor2<F, Mu> unbox(final Kind<Mu, F> box)
    {
        return Unchecks.cast(box);
    }

}
