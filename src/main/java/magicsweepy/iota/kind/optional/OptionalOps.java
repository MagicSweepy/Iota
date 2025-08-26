package magicsweepy.iota.kind.optional;

import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@UtilityClass
public class OptionalOps
{

    public <U> U orElse(final Optional<? extends U> optional,
                        final U other)
    {
        if (optional.isPresent())
            return optional.get();
        return other;
    }

    public <U> U orElseGet(final Optional<? extends U> optional,
                           final Supplier<? extends U> other)
    {
        if (optional.isPresent())
            return optional.get();
        return other.get();
    }

    public <U> Optional<U> or(final Optional<? extends U> optional,
                              final Supplier<? extends Optional<? extends U>> other)
    {
        if (optional.isPresent())
            return optional.map(u -> u);
        return other.get().map(u -> u);
    }

}
