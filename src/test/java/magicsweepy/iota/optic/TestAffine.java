package magicsweepy.iota.optic;

import magicsweepy.iota.kind.either.Either;
import org.jetbrains.annotations.TestOnly;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@TestOnly
public final class TestAffine
{

    private static final Affine<String, String, Integer, Integer> intAffine = new Affine<>()
    {

        @NonNull
        @Override
        public Either<String, Integer> preview(@NonNull final String s)
        {
            try
            {
                return Either.right(Integer.parseInt(s));
            }
            catch (final NumberFormatException ignoredException)
            {
                return Either.left(s);
            }
        }

        @NonNull
        @Override
        public String set(@NonNull final Integer b, @NonNull final String s)
        {
            return b.toString();
        }

    };

    @Test
    public void previewAndSetNumAndStr()
    {
        String str = "123";
        Either<String, Integer> preview = intAffine.preview(str);
        Integer num = preview.right().get();
        String newStr = intAffine.set(num + 1, str);
        assertEquals("124", newStr);
    }

}
