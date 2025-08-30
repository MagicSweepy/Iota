package magicsweepy.iota.unsafe;

import lombok.experimental.UtilityClass;
import magicsweepy.iota.IotaMod;
import org.jetbrains.annotations.UnknownNullability;
import sun.misc.Unsafe;

@UtilityClass
/* package */ class UnsafeExposer
{

    @UnknownNullability
    /* package */ Unsafe getInstance()
    {
        try
        {
            var unsafe = Unsafe.class.getDeclaredField("theUnsafe");
            unsafe.setAccessible(true);
            return (Unsafe) unsafe.get(null);
        }
        catch (NoSuchFieldException | IllegalAccessException exception)
        {
            IotaMod.logger.error("Cannot get the class 'Unsafe'!");
            return null;
        }
    }

}
