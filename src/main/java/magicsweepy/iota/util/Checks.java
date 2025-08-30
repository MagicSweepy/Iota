package magicsweepy.iota.util;

import com.google.common.base.Preconditions;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;

@UtilityClass
public class Checks
{

    public void notnull(@Nullable Object... args)
    {
        notnull(null, args);
    }

    public void notnull(@Nullable String message, @Nullable Object... args)
    {
        Preconditions.checkNotNull(args);
        for (Object arg : args)
        {
            if (arg == null)
            {
                String defaultMessage = String.format("The parameter is null in all args '%s'", StringUtils.join(args, ", "));
                throw new NullPointerException(StringUtils.defaultString(message, defaultMessage));
            }
        }
    }

}
