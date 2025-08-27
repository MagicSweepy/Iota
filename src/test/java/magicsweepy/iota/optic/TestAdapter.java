package magicsweepy.iota.optic;

import org.jetbrains.annotations.TestOnly;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestOnly
public final class TestAdapter
{

    private static final Adapter<String, String, Integer, Integer> intAdapter = new Adapter<>()
    {

        @NonNull
        @Override
        public Integer from(@NonNull final String s)
        {
            return Integer.parseInt(s);
        }

        @NonNull
        @Override
        public String to(@NonNull final Integer b)
        {
            return b.toString();
        }

    };

    @Test
    public void adaptNumAndStr()
    {
        String str = "123";
        Integer num = intAdapter.from(str);
        String newStr = intAdapter.to(num + 1);
        assertEquals("124", newStr);
    }

}
