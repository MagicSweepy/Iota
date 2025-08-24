package magicsweepy.iota.kind;

import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

@NullMarked
public interface Traversable<T extends Ob, Mu extends Traversable.Mu> extends Functor<T, Mu>, Foldable<T>
{

    interface Mu extends Functor.Mu {}

    static <F extends Ob, Mu extends Traversable.Mu> Traversable<F, Mu> unbox(final Kind<Mu, F> box)
    {
        return Unchecks.cast(box);
    }

    <F extends Ob, A, B> Kind<F, Kind<T, B>> traverse(final Applicative<F, ?> ap,
                                                      final Function<A, Kind<F, B>> f,
                                                      final Kind<T, A> input);

    default <F extends Ob, A> Kind<F, Kind<T, A>> flip(final Applicative<F, ?> ap,
                                                       final Kind<T, Kind<F, A>> input)
    {
        return traverse(ap, Function.identity(), input);
    }

}
