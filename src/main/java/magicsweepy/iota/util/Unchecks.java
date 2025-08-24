package magicsweepy.iota.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Unchecks
{

    @SuppressWarnings("unchecked")
    public <T> T cast(Object o)
    {
        return (T) o;
    }

}
