package magicsweepy.iota.optic;

import com.github.bsideup.jabel.Desugar;
import lombok.Getter;
import magicsweepy.iota.kind.either.Either;
import magicsweepy.iota.kind.tuple.Pair;
import magicsweepy.iota.util.Unchecks;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

@Desugar
public record KeyableInj<K, A, B>(@Getter K key) implements Prism<Pair<K, ?>, Pair<K, ?>, A, B>
{

    @NonNull
    @Override
    public Either<Pair<K, ?>, A> match(final Pair<K, ?> pair)
    {
        return Objects.equals(key, pair.first()) ? Either.right(Unchecks.cast(pair.second())) : Either.left(pair);
    }

    @NonNull
    @Override
    public Pair<K, ?> build(@NonNull final B b)
    {
        return Pair.of(key, b);
    }

    @NotNull
    @Override
    public String toString()
    {
        return "inj[" + key + "]";
    }

    @Override
    public boolean equals(final Object o)
    {
        return o instanceof KeyableInj<?,?,?> && Objects.equals(((KeyableInj<?, ?, ?>) o).key, key);
    }

}
