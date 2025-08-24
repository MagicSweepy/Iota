package magicsweepy.iota.kind;

import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface Representable<T extends Ob, C, Mu extends Representable.Mu> extends Functor<T, Mu>
{

    interface Mu extends Functor.Mu {}

    static <F extends Ob, C, Mu extends Representable.Mu> Representable<F, C, Mu> unbox(final Kind<Mu, F> box)
    {
        return Unchecks.cast(box);
    }

    <A> Kind<T, A> from(final Kind<Functoid.MuF<C>, A> input);

    <A> Kind<Functoid.MuF<C>, A> to(final Kind<T, A> input);

}
