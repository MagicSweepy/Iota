package magicsweepy.iota.kind;

import com.github.bsideup.jabel.Desugar;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

import java.util.function.BiFunction;
import java.util.function.Function;

@NullMarked
@Desugar
public record IdF<A>(@Getter A value) implements Kind<IdF.Mu, A>
{

    public static final class Mu implements Ob {}

    public static <A> A get(final Kind<Mu, A> box)
    {
        return ((IdF<A>) box).value;
    }

    public static <A> IdF<A> create(final A a)
    {
        return new IdF<>(a);
    }

    public enum Instance implements Functor<Mu, Applicative.Mu>, Applicative<Mu, Instance.Mu>
    {

        INSTANCE;

        public static final class Mu implements Functor.Mu, Applicative.Mu {}

        @Override
        public <T, R> Kind<IdF.Mu, R> map(final Function<? super T, ? extends R> f,
                                          final Kind<IdF.Mu, T> fa)
        {
            final IdF<T> idF = (IdF<T>) fa;
            return new IdF<>(f.apply(idF.value));
        }

        @Override
        public <A> Kind<IdF.Mu, A> point(final A a)
        {
            return create(a);
        }

        @Override
        public <A, R> Function<Kind<IdF.Mu, A>, Kind<IdF.Mu, R>> lift(final Kind<IdF.Mu, Function<A, R>> f)
        {
            return a -> create(get(f).apply(get(a)));
        }

        @Override
        public <A, B, R> BiFunction<Kind<IdF.Mu, A>, Kind<IdF.Mu, B>, Kind<IdF.Mu, R>> lift2(final Kind<IdF.Mu, BiFunction<A, B, R>> f)
        {
            return (a, b) -> create(get(f).apply(get(a), get(b)));
        }

    }

}
