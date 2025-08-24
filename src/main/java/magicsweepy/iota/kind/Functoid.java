package magicsweepy.iota.kind;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

/**
 * The prototypes of the {@link Function}, used to represent it to type level.
 */
@NullMarked
public interface Functoid<A, B> extends Function<A, B>, Kind2<Functoid.Mu, A, B>, Kind<Functoid.MuF<A>, B>
{

    final class Mu implements Ob2 {}

    final class MuF<A> implements Ob {}

    static <A, B> Functoid<A, B> create(final Function<? super A, ? extends B> f)
    {
        return f::apply;
    }

    static <A, B> Functoid<A, B> unbox(final Kind2<Functoid.Mu, A, B> box)
    {
        return (Functoid<A, B>) box;
    }

    static <A, B> Functoid<A, B> unbox(final Kind<Functoid.MuF<A>, B> box)
    {
        return (Functoid<A, B>) box;
    }

    @Override
    B apply(A a);

    // TODO Pro-functor Supported.
    enum Instance implements Kind<Instance.Mu, Functoid.Mu>
    {

        INSTANCE;

        public static final class Mu implements Ob {}

    }

    final class InstanceF<R> implements Representable<Functoid.MuF<R>, R, InstanceF.Mu<R>>
    {

        public static final class Mu<A> implements Representable.Mu {}

        @NonNull
        @Override
        public <T, A> Kind<MuF<R>, A> map(Function<? super T, ? extends A> f,
                                          Kind<MuF<R>, T> fa)
        {
            return Functoid.create(f.compose(Functoid.unbox(fa)));
        }

        @NonNull
        @Override
        public <A> Kind<MuF<R>, A> from(Kind<MuF<R>, A> input)
        {
            return input;
        }

        @NonNull
        @Override
        public <A> Kind<MuF<R>, A> to(Kind<MuF<R>, A> input)
        {
            return input;
        }

    }

}
