package magicsweepy.iota.kind.either;

import com.github.bsideup.jabel.Desugar;
import lombok.Getter;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.KindUnwrapException;
import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NullMarked;

@NullMarked
@Desugar
public record EitherKind<L, R>(@Getter Either<L, R> either) implements Kind<Either.Mu<L>, R>
{

    public static <L, R> Kind<Either.Mu<L>, R> widen(Either<L, R> either)
    {
        return new EitherKind<>(either);
    }

    public static <L, R> Either<L, R> narrow(Kind<Either.Mu<L>, R> kind)
    {
        if (kind instanceof EitherKind<?, ?> eitherKind)
        {
            return Unchecks.cast(eitherKind.either());
        }
        else
        {
            throw new KindUnwrapException("Kind instance is not an EitherKind at '" + kind.getClass().getName() + "'");
        }
    }

}
