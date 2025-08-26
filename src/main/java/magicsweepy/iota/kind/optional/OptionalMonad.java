package magicsweepy.iota.kind.optional;

import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.KindUnwrapException;
import magicsweepy.iota.kind.SemiMonad;
import magicsweepy.iota.kind.ThrowableMonad;
import magicsweepy.iota.kind.Unit;
import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@NullMarked
public class OptionalMonad implements SemiMonad<OptionalKind.Mu>, ThrowableMonad<OptionalKind.Mu, Unit>
{

    /* package */ record Holder<T>(Optional<T> optional) implements OptionalKind<T> {}

    @Override
    public <A> Kind<OptionalKind.Mu, A> of(@Nullable A value)
    {
        return box(Optional.ofNullable(value));
    }

    @Override
    public <A> Kind<OptionalKind.Mu, A> point()
    {
        return box(Optional.empty());
    }

    @Override
    public <A> Kind<OptionalKind.Mu, A> point(A value)
    {
        return box(Optional.of(value));
    }

    @Override
    public <A, B> Kind<OptionalKind.Mu, B> map(Function<? super A, ? extends B> f, Kind<OptionalKind.Mu, A> fa)
    {
        Optional<A> optionalA = unbox(fa);
        Optional<B> optionalB = optionalA.map(f);
        return box(optionalB);
    }

    @Override
    public <A, B> Kind<OptionalKind.Mu, B> flatMap(Function<? super A, ? extends Kind<OptionalKind.Mu, B>> f,
                                                   Kind<OptionalKind.Mu, A> fa)
    {
        Optional<A> optionalA = unbox(fa);
        Optional<B> optionalB = optionalA.flatMap(a -> unbox(f.apply(a)));
        return box(optionalB);
    }

    @Override
    public <A, B> Function<Kind<OptionalKind.Mu, A>, Kind<OptionalKind.Mu, B>> lift(Kind<OptionalKind.Mu, Function<A, B>> f)
    {
        return fa -> {
            Optional<Function<A, B>> fOptional = unbox(f);
            Optional<A> optionalA = unbox(fa);
            Optional<B> optionalB = optionalA.flatMap(a -> fOptional.map(g -> g.apply(a)));
            return box(optionalB);
        };
    }

    @Override
    public <A, B> Kind<OptionalKind.Mu, B> ap(Kind<OptionalKind.Mu, Function<A, B>> f,
                                              Kind<OptionalKind.Mu, A> fa)
    {
        Optional<? extends Function<A, B>> fOptional = unbox(f);
        Optional<A> optionalA = unbox(fa);
        Optional<B> optionalB = fOptional.flatMap(optionalA::map);
        return box(optionalB);
    }

    @Override
    public <A> Kind<OptionalKind.Mu, A> liftError(@Nullable Unit error)
    {
        return box(Optional.empty());
    }

    @Override
    public <A> Kind<OptionalKind.Mu, A> handleError(Kind<OptionalKind.Mu, A> ma,
                                                    Function<? super Unit, ? extends A> handler)
    {
        Optional<A> optional = unbox(ma);
        if (optional.isEmpty())
            return Unchecks.cast(handler.apply(Unit.INSTANCE));
        else
            return ma;
    }

    @Override
    public <A> Kind<OptionalKind.Mu, A> handleErrorWith(Kind<OptionalKind.Mu, A> ma,
                                                        Function<? super Unit, ? extends Kind<OptionalKind.Mu, A>> handler)
    {
        return handleError(ma, Unchecks.cast(handler));
    }

    private <T> Kind<OptionalKind.Mu, T> box(Optional<T> optional)
    {
        return new Holder<>(optional);
    }

    private <T> Optional<T> unbox(@Nullable Kind<OptionalKind.Mu, T> kind)
    {
        if (kind instanceof Holder<?> holder)
            return Unchecks.cast(holder.optional);
        else
            throw new KindUnwrapException("Kind instance is not a correct Holder");
    }

}
