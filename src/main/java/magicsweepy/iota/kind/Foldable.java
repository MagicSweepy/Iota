package magicsweepy.iota.kind;

import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

@NullMarked
public interface Foldable<T extends Ob>
{

    <A, M> M foldMap(Monoid<M> monoid,
                     Function<? super A, ? extends M> f,
                     Kind<T, A> fa);

}
