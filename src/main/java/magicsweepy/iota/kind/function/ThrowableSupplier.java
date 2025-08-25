package magicsweepy.iota.kind.function;

import org.jspecify.annotations.Nullable;

/**
 * A {@code Supplier} implementation with throwable {@link #get} method.
 *
 * @see java.util.function.Supplier
 *
 * @param <T> The generic type of value which supplied by this {@code Supplier}.
 */
@FunctionalInterface
public interface ThrowableSupplier<T>
{

    @Nullable
    T get() throws Throwable;

}
