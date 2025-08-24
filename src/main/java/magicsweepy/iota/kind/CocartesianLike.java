package magicsweepy.iota.kind;

import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

@NullMarked
public interface CocartesianLike<T extends Ob, C, Mu extends CocartesianLike.Mu> extends Functor<T, Mu>, Traversable<T, Mu>
{

    interface Mu extends Functor.Mu, Traversable.Mu {}

    static <F extends Ob, C, Mu extends CocartesianLike.Mu> CocartesianLike<F, C, Mu> unbox(final Kind<Mu, F> box)
    {
        return Unchecks.cast(box);
    }

    <A> Kind<T, A> from(final Kind<Either.Mu<C>, A> input);

    <A> Kind<Either.Mu<C>, A> to(final Kind<T, A> input);

    @Override
    default <F extends Ob, A, B> Kind<F, Kind<T, B>> traverse(final Applicative<F, ?> ap,
                                                              final Function<A, Kind<F, B>> f,
                                                              final Kind<T, A> input)
    {
        return ap.map(this::from, new Either.Instance<C>().traverse(ap, f, to(input)));
    }

}
