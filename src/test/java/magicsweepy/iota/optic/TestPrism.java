package magicsweepy.iota.optic;

import magicsweepy.iota.kind.either.Either;
import org.jetbrains.annotations.TestOnly;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@TestOnly
public final class TestPrism
{

    static final Prism<String, String, Integer, Integer> intPrism = new Prism<>()
    {

        @NonNull
        @Override
        public Either<String, Integer> match(@NonNull String str)
        {
            try
            {
                return Either.right(Integer.parseInt(str));
            }
            catch (NumberFormatException ignoredException)
            {
                return Either.left(str);
            }
        }

        @NonNull
        @Override
        public String build(@NonNull Integer num)
        {
            return num.toString();
        }

    };

    @Test
    public void matchNumAndStr()
    {
        Either<String, Integer> numEither = intPrism.match("42");
        assertEquals(42, numEither.right().get());

        Either<String, Integer> strEither = intPrism.match("abc");
        assertEquals("abc", strEither.left().get());
    }

    @Test
    public void buildNumAndStr()
    {
        assertEquals("100", intPrism.build(100));
        assertEquals("-5", intPrism.build(-5));
    }

}
