package magicsweepy.iota.unsafe;

import lombok.experimental.UtilityClass;
import magicsweepy.iota.IotaMod;
import magicsweepy.iota.util.Checks;
import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.atomic.AtomicReference;

@UtilityClass
public class AtomicReferences
{

    private long value;

    static
    {
        try
        {
            Class<?> atomicReference = Class.forName("java.util.concurrent.atomic.AtomicReference");
            value = UnsafeExposer.getInstance().objectFieldOffset(atomicReference.getDeclaredField("value"));
        }
        catch (ClassNotFoundException | NoSuchFieldException exception)
        {
            IotaMod.logger.error("Cannot access 'AtomicReference' class and get the property 'value'!");
        }
    }

    /**
     * Sets the value to {@code newValue}, with memory semantics of setting as if the variable was declared
     * non-{@code volatile} and non-{@code final}.
     */
    public <V> void setPlain(@NonNull AtomicReference<V> reference, V newValue)
    {
        Checks.notnull(reference);
        UnsafeExposer.getInstance().putObject(reference, value, newValue);
    }

    /**
     * Returns the current value, with memory semantics of reading as if the variable was declared non-{@code volatile}.
     */
    public <V> V getPlain(@NonNull AtomicReference<V> reference)
    {
        Checks.notnull(reference);
        return Unchecks.cast(UnsafeExposer.getInstance().getObject(reference, value));
    }

}
