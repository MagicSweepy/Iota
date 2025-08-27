package magicsweepy.iota.kind.maybe;

import com.github.bsideup.jabel.Desugar;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.KindUnwrapException;
import magicsweepy.iota.kind.SemiMonad;
import magicsweepy.iota.kind.ThrowableMonad;
import magicsweepy.iota.kind.Unit;
import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

@NullMarked
public class MaybeMonad implements SemiMonad<Maybe.Mu>, ThrowableMonad<Maybe.Mu, Unit>
{

    @Desugar
    record Holder<A>(Maybe<A> maybe) implements Kind<Maybe.Mu, A> {}

    @Override
    public <A> Kind<Maybe.Mu, A> point()
    {
        return box(Maybe.nothing());
    }

    @Override
    public <A> Kind<Maybe.Mu, A> of(@Nullable A value)
    {
        return box(Maybe.just(value));
    }

    @Override
    public <A> Kind<Maybe.Mu, A> point(A value)
    {
        return of(value);
    }

    @Override
    public <A, B> Kind<Maybe.Mu, B> map(Function<? super A, ? extends B> f,
                                        Kind<Maybe.Mu, A> fa)
    {
        Maybe<A> maybeA = unbox(fa);
        Maybe<B> maybeB = maybeA.map(f);
        return box(maybeB);
    }

    @Override
    public <A, B> Kind<Maybe.Mu, B> flatMap(Function<? super A, ? extends Kind<Maybe.Mu, B>> f,
                                            Kind<Maybe.Mu, A> fa)
    {
        Maybe<A> maybeA = unbox(fa);
        Maybe<B> maybeB = maybeA.flatMap(a -> unbox(f.apply(a)));
        return box(maybeB);
    }

    @Override
    public <A, B> Function<Kind<Maybe.Mu, A>, Kind<Maybe.Mu, B>> lift(Kind<Maybe.Mu, Function<A, B>> f)
    {
        return fa -> {
            Maybe<Function<A, B>> fMaybe = unbox(f);
            Maybe<A> maybeA = unbox(fa);
            Maybe<B> maybeB = maybeA.flatMap(a -> fMaybe.map(g -> g.apply(a)));
            return box(maybeB);
        };
    }

    @Override
    public <A, B> Kind<Maybe.Mu, B> ap(Kind<Maybe.Mu, Function<A, B>> f,
                                       Kind<Maybe.Mu, A> fa)
    {
        Maybe<? extends Function<A, B>> fMaybe = unbox(f);
        Maybe<A> maybeA = unbox(fa);
        Maybe<B> maybeB = maybeA.flatMap(a -> fMaybe.map(g -> g.apply(a)));
        return box(maybeB);
    }

    @Override
    public <A> Kind<Maybe.Mu, A> liftError(@Nullable Unit error)
    {
        return Maybe.nothing();
    }

    @Override
    public <A> Kind<Maybe.Mu, A> handleErrorWith(Kind<Maybe.Mu, A> ma,
                                                 Function<? super Unit, ? extends Kind<Maybe.Mu, A>> handler)
    {
        return handler.apply(Unit.INSTANCE);
    }

    private static <A> Kind<Maybe.Mu, A> box(Maybe<A> maybe)
    {
        return new Holder<>(maybe);
    }

    private static <A> Maybe<A> unbox(@Nullable Kind<Maybe.Mu, A> kind)
    {
        if (kind instanceof Holder<?> holder)
            return Unchecks.cast(holder.maybe);
        else
            throw new KindUnwrapException("Kind instance is not a correct Holder");
    }

}
