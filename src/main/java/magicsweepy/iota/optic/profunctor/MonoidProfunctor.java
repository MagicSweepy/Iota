package magicsweepy.iota.optic.profunctor;

import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import org.jspecify.annotations.NullMarked;

import java.util.function.Supplier;

@NullMarked
public interface MonoidProfunctor<P extends Ob2, Mu extends MonoidProfunctor.Mu> extends Profunctor<P, Mu>
{

    interface Mu extends Profunctor.Mu {}

    <A, B> Kind2<P, A, B> zero(final Kind2<Functoid.Mu, A, B> func);

    <A, B> Kind2<P, A, B> plus(final Kind2<Procompose.Mu<P, P>, A, B> input);

    default <A, B, C> Kind2<P, A, C> compose(final Kind2<P, B, C> first,
                                            final Supplier<Kind2<P, A, B>> second)
    {
        return plus(new Procompose<>(second, first));
    }

}
