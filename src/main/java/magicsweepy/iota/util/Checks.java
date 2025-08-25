package magicsweepy.iota.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

@UtilityClass
public class Checks
{

    /**
     * Checks any nullable {@code Object} is or not {@code null}.
     * <p>
     * This method allowed to use several {@code message} thrown.
     *
     * @param object  The nullable {@code Object}.
     * @param message The messages which will be thrown when {@code Object} is {@code null}.
     * @return        This method has same function with {@link Objects#requireNonNull}, returns {@code Object} itself
     *                and check its nullity, if the {@code Object} is {@code null}, then thrown {@code message}.
     */
    public <T> T notnullT(@Nullable T object, Object... message)
    {
        return Objects.requireNonNull(object, String.format("We have a problem: %s. "
                + "Please report that on our bugtracker unless you are using some old version. "
                + "Thank you.", StringUtils.join(message)));
    }

    /**
     * Checks several nullable {@code Objects} is or not {@code null}.
     * <p>
     * Always default {@code message} thrown via {@link #notnull(String, Object...)}.
     *
     * @param objects The checked {@code Object}s.
     */
    public void notnull(@Nullable Object... objects)
    {
        notnull(null, objects);
    }

    /**
     * Checks several nullable {@code Object}s is or not {@code null}.
     * <p>
     * This method allowed to use several {@code Object}s but not supported multi {@code message} thrown.
     *
     * @param message The message which will be thrown.
     * @param objects The checked {@code Object}s.
     */
    public void notnull(String message, @Nullable Object... objects)
    {
        if (objects == null)
        {
            throw new NullPointerException("Parameter array is null");
        }
        for (int i = 0; i < objects.length; i++)
        {
            if (objects[i] == null)
            {
                String defaultMessage = String.format(
                        "The index '%d' parameter is null in all args '%s'", i, Arrays.toString(objects));
                String thrownMessage = StringUtils.defaultString(message, defaultMessage);
                throw new NullPointerException(thrownMessage);
            }
        }
    }

}
