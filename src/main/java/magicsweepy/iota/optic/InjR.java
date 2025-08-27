package magicsweepy.iota.optic;

import magicsweepy.iota.kind.either.Either;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class InjR<F, G1, G2> implements Prism<Either<F, G1>, Either<F, G2>, G1, G2>
{

    public static final InjR<?, ?, ?> INSTANCE = new InjR<>();

    @Override
    public Either<Either<F, G2>, G1> match(final Either<F, G1> either)
    {
        return either.map(f -> Either.left(Either.left(f)), Either::right);
    }

    @Override
    public Either<F, G2> build(final G2 g)
    {
        return Either.right(g);
    }

    @Override
    public String toString()
    {
        return "injR";
    }

}
