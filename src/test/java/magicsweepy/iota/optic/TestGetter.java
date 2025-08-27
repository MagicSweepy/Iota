package magicsweepy.iota.optic;

import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestOnly
public final class TestGetter
{

    static final Getter<String, String, Integer, Integer> strGetter = Optics.getter(String::length);
    static final Getter<Integer, Integer, String, String> intGetter = Optics.getter(Object::toString);
    static final Getter<Long, Long, Long, Long> longGetter = Optics.getter(l -> l + 1);

    @Test
    public void getStringLength()
    {
        assertEquals(5, strGetter.get("hello"));
        assertEquals(0, strGetter.get(""));
        assertEquals(4, strGetter.get("IOTA"));
    }

    @Test
    public void convertIntToStr()
    {
        assertEquals("123", intGetter.get(123));
        assertEquals("0", intGetter.get(0));
        assertEquals("-456", intGetter.get(-456));
    }

    @Test
    public void getAndIncreaseValue()
    {
        assertEquals(2L, longGetter.get(1L));
        assertEquals(0L, longGetter.get(-1L));
        assertEquals(100L, longGetter.get(99L));
    }

}
