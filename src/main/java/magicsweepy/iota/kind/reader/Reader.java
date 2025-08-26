package magicsweepy.iota.kind.reader;

import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Ob;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

@FunctionalInterface
public interface Reader<R, A> extends Kind<Reader.@NonNull Mu<R>, A>
{

    final class Mu<R> implements Ob {}

    @Nullable
    A read(@NonNull R r);

    @NonNull
    static <R, A> Reader<R, A> of(@NonNull Function<R, A> readFunction)
    {
        return readFunction::apply;
    }

    @NonNull
    static <R, A> Reader<R, A> set(@Nullable A value)
    {
        return r -> value;
    }

    @NonNull
    static <R> Reader<R, R> identity()
    {
        return r -> r;
    }

    @NonNull
    default <B> Reader<R, B> map(@NonNull Function<? super A, ? extends B> f)
    {
        return (R r) -> f.apply(read(r));
    }

    @NonNull
    default <B> Reader<R, B> flatMap(@NonNull Function<? super A, ? extends Reader<R, ? extends B>> f)
    {
        return (R r) -> f.apply(read(r)).read(r);
    }

}
