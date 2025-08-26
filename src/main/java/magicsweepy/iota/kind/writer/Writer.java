package magicsweepy.iota.kind.writer;

import com.github.bsideup.jabel.Desugar;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Monoid;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.kind.Unit;
import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

@Desugar
public record Writer<M, A>(@NonNull M message, @Nullable A value) implements Kind<Writer.@NonNull Mu<M>, A>
{

    public static final class Mu<T> implements Ob {}

    @NonNull
    public static <M, A> Writer<M, A> create(@NonNull Monoid<M> messageMonoid, @Nullable A value)
    {
        return new Writer<>(messageMonoid.empty(), value);
    }

    @NonNull
    public static <M> Writer<M, Unit> create(@NonNull M message)
    {
        return new Writer<>(message, Unit.INSTANCE);
    }

    @NonNull
    public <B> Writer<M, B> map(@NonNull Function<? super A, ? extends B> f)
    {
        return new Writer<>(message, f.apply(value));
    }

    @NonNull
    public <B> Writer<M, B> flatMap(@NonNull Monoid<M> messageMonoid,
                                    @NonNull Function<? super A, ? extends Writer<M, ? extends B>> f)
    {
        Writer<M, ? extends B> other = f.apply(value);
        M combinedMessage = messageMonoid.combine(message, other.message);
        return new Writer<>(combinedMessage, Unchecks.cast(other.value));
    }

    @Nullable
    public A run()
    {
        return value;
    }

    @NonNull
    public M execute()
    {
        return message;
    }

}
