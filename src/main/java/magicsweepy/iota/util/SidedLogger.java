package magicsweepy.iota.util;

import lombok.experimental.Delegate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class SidedLogger implements Logger
{

    protected final String CLIENT_SUFFIX = ":C";
    protected final String SERVER_SUFFIX = ":S";

    private final Logger clientLogger;
    private final Logger serverLogger;

    public SidedLogger(String name)
    {
        this.clientLogger = LogManager.getLogger(name + CLIENT_SUFFIX);
        this.serverLogger = LogManager.getLogger(name + SERVER_SUFFIX);
    }

    @SuppressWarnings("unused")
    @Delegate
    private Logger logger()
    {
        return Platform.isClient() ? this.clientLogger : this.serverLogger;
    }

}
