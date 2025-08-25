package magicsweepy.iota.kind;

/**
 * Marked a {@link Kind} object not be wrapped to its instance in type level.
 * <p>
 * Usually used for some nullity throwing in {@link Kind} operations or {@link Monad} operations.
 */
public class KindUnwrapException extends IllegalArgumentException
{

    public KindUnwrapException(String message)
    {
        super(message);
    }

    public KindUnwrapException(Throwable throwable)
    {
        super(throwable);
    }

    public KindUnwrapException(String message, Throwable throwable)
    {
        super(message, throwable);
    }

}
