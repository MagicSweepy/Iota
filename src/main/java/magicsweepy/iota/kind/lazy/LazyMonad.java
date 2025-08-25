package magicsweepy.iota.kind.lazy;

import com.github.bsideup.jabel.Desugar;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.KindUnwrapException;
import magicsweepy.iota.kind.Monad;
import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

@NullMarked
public class LazyMonad implements Monad<Lazy.Mu>
{

    @Desugar
    /* package */ record Holder<A>(Lazy<A> lazyInstance) implements Kind<Lazy.Mu, A> {}

    @Override
    public <A> Kind<Lazy.Mu, A> of(@Nullable A a)
    {
        return box(Lazy.get(a));
    }

    @Override
    public <A> Kind<Lazy.Mu, A> point(A a)
    {
        return of(a);
    }

    @Override
    public <A, B> Kind<Lazy.Mu, B> map(Function<? super A, ? extends B> f,
                                       Kind<Lazy.Mu, A> fa)
    {
        Lazy<A> lazyA = unbox(fa);
        Lazy<B> lazyB = lazyA.map(f);
        return box(lazyB);
    }

    @Override
    public <A, B> Kind<Lazy.Mu, B> flatMap(Function<? super A, ? extends Kind<Lazy.Mu, B>> f,
                                           Kind<Lazy.Mu, A> fa)
    {
        Lazy<A> lazyA = unbox(fa);
        Lazy<B> lazyB = lazyA.flatMap(a -> unbox(f.apply(a)));
        return box(lazyB);
    }

    @Override
    public <A, B> Function<Kind<Lazy.Mu, A>, Kind<Lazy.Mu, B>> lift(Kind<Lazy.Mu, Function<A, B>> f)
    {
        return fa -> {
            Lazy<Function<A, B>> fLazy = unbox(f);
            Lazy<A> lazyA = unbox(fa);
            Lazy<B> lazyB = lazyA.flatMap(a -> fLazy.map(g -> g.apply(a)));
            return box(lazyB);
        };
    }

    @Override
    public <A, B> Kind<Lazy.Mu, B> ap(Kind<Lazy.Mu, Function<A, B>> f,
                                      Kind<Lazy.Mu, A> fa)
    {
        Lazy<? extends Function<A, B>> fLazy = unbox(f);
        Lazy<A> lazyA = unbox(fa);
        Lazy<B> lazyB = Lazy.create(() -> fLazy.pop().apply(lazyA.pop()));
        return box(lazyB);
    }

    private <A> Kind<Lazy.Mu, A> box(Lazy<A> lazy)
    {
        return new Holder<>(lazy);
    }

    private <A> Lazy<A> unbox(@Nullable Kind<Lazy.Mu, A> kind)
    {
        if (kind instanceof Holder<?> holder)
            return Unchecks.cast(holder.lazyInstance);
        else
            throw new KindUnwrapException("Kind instance is not a correct Holder");
    }

}
