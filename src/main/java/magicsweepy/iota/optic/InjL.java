package magicsweepy.iota.optic;

import magicsweepy.iota.kind.either.Either;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class InjL<F1, G, F2> implements Prism<Either<F1, G>, Either<F2, G>, F1, F2>
{

    public static final InjL<?, ?, ?> INSTANCE = new InjL<>();

    @Override
    public Either<Either<F2, G>, F1> match(Either<F1, G> either)
    {
        return either.map(Either::right, g -> Either.left(Either.right(g)));
    }

    @Override
    public Either<F2, G> build(F2 f)
    {
        return Either.left(f);
    }

    @Override
    public String toString()
    {
        return "injL";
    }

}
