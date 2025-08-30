package magicsweepy.iota.unsafe;

import lombok.experimental.UtilityClass;
import magicsweepy.iota.util.Checks;
import org.jspecify.annotations.NonNull;

import java.lang.reflect.Field;

@UtilityClass
public class ObjectOps
{

    /**
     * Allocates an instance of the specified class without invoking its constructor.
     *
     * @param clazz The class to allocate an instance of.
     * @return      The new instance of the specified class.
     *
     * @param <T>   The type of the class.
     *
     * @throws RuntimeException If the instance cannot be allocated.
     */
    public <T> T allocate(@NonNull Class<T> clazz)
    {
        Checks.notnull(clazz);
        try
        {
            return clazz.cast(UnsafeExposer.getInstance().allocateInstance(clazz));
        }
        catch (InstantiationException exception)
        {
            throw new RuntimeException("Failed to allocate instance for a class: '" + clazz.getSimpleName() + "'");
        }
    }

    /**
     * Sets the value of a field in the specified target object using low-level operations.
     *
     * @param target    The object whose field is to be modified.
     * @param fieldName The name of the field to be modified.
     * @param value     The new value to set for the field.
     *
     * @throws RuntimeException If the field does not exist or cannot be accessed.
     */
    public void setValue(Object target, String fieldName, Object value)
    {
        try
        {
            Field field = target.getClass().getDeclaredField(fieldName);
            long offset = UnsafeExposer.getInstance().objectFieldOffset(field);
            UnsafeExposer.getInstance().putObject(target, offset, value);
        }
        catch (NoSuchFieldException exception)
        {
            throw new RuntimeException("Failed to set value for field: '" + fieldName + "'");
        }
    }

}
