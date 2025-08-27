package magicsweepy.iota.unsafe;

import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestOnly
public final class TestAtomicReferences
{

    @Test
    public void getPlainAndSetPlain()
    {
        var result = new AtomicReference<>("init");

        AtomicReferences.setPlain(result, "update");
        assertEquals("update", AtomicReferences.getPlain(result));
    }

    @Test
    public void getPlainWithVolatileSet()
    {
        var result = new AtomicReference<>("init");

        result.set("update");
        assertEquals("update", AtomicReferences.getPlain(result));
    }

    @Test
    public void setPlainWithVolatileGet()
    {
        var result = new AtomicReference<>("init");

        AtomicReferences.setPlain(result, "update");
        assertEquals("update", result.get());
    }

}
