package magicsweepy.iota.optic;

import magicsweepy.iota.kind.either.Either;
import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@TestOnly
public final class TestInj
{

    private static final InjL<String, Integer, Boolean> inj = Optics.leftInj();

    @Test
    public void matchInputLeft()
    {
        Either<String, Integer> input = Either.left("test");
        Either<Either<Boolean, Integer>, String> output = inj.match(input);

        assertEquals("test", output.right().get());
    }

    @Test
    public void matchInputRight()
    {
        Either<String, Integer> input = Either.right(42);
        Either<Either<Boolean, Integer>, String> output = inj.match(input);

        assertEquals(42, output.left().get().right().get().intValue());
    }

}
