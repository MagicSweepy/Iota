package magicsweepy.iota.kind.either;

import com.github.bsideup.jabel.Desugar;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.KindUnwrapException;
import magicsweepy.iota.kind.ThrowableMonad;
import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public abstract class EitherMonad<L> implements ThrowableMonad<Either.Mu<L>, L>
{

    @Desugar
    /* package */ record Holder<L, R>(Either<L, R> either) implements Kind<Either.Mu<R>, L> {}

    @Override
    public <A> Kind<Either.Mu<L>, A> of(@Nullable A value)
    {
        return box(Either.left(value));
    }

    @Override
    public <A> Kind<Either.Mu<L>, A> point(A value)
    {
        return of(value);
    }

    private <L1, R> Kind<Either.Mu<R>, L1> box(Either<L1, R> either)
    {
        return new Holder<>(either);
    }

    private <L1, R> Either<L1, R> unbox(@Nullable Kind<Either.Mu<R>, L1> kind)
    {
        if (kind instanceof Holder<?, ?> holder)
            return Unchecks.cast(holder);
        else
            throw new KindUnwrapException("Kind instance is not a correct Holder");
    }

}
