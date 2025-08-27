package magicsweepy.iota.optic;

import magicsweepy.iota.kind.tuple.Pair;
import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestOnly
public final class TestProj
{

    private static final ProjL<String, Integer, String> projL = Optics.leftProj();
    private static final ProjR<String, Integer, String> projR = Optics.rightProj();

    @Test
    public void viewPairedValue()
    {
        Pair<String, Integer> alice = Pair.of("alice", 16);
        assertEquals("alice", projL.view(alice));
        assertEquals(16, projR.view(alice));
    }

}
