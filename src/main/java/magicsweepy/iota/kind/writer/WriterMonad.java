package magicsweepy.iota.kind.writer;

import com.github.bsideup.jabel.Desugar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.KindUnwrapException;
import magicsweepy.iota.kind.Monad;
import magicsweepy.iota.kind.Monoid;
import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

@NullMarked
@Getter
@AllArgsConstructor
public final class WriterMonad<M> implements Monad<Writer.Mu<M>>
{

    private final Monoid<M> monoid;

    @Desugar
    /* package */ record Holder<M, A>(Writer<M, A> writer) implements Kind<Writer.Mu<M>, A> {}

    @Override
    public <A> Kind<Writer.Mu<M>, A> of(@Nullable A value)
    {
        return box(Writer.create(monoid, value));
    }

    @Override
    public <A> Kind<Writer.Mu<M>, A> point(@NonNull A value)
    {
        return of(value);
    }

    @Override
    public <A, B> Kind<Writer.Mu<M>, B> map(Function<? super A, ? extends B> f,
                                            Kind<Writer.Mu<M>, A> fa)
    {
        Writer<M, A> writerA = unbox(fa);
        Writer<M, B> writerB = writerA.map(f);
        return box(writerB);
    }

    @Override
    public <A, B> Kind<Writer.Mu<M>, B> flatMap(Function<? super A, ? extends Kind<Writer.Mu<M>, B>> f,
                                                Kind<Writer.Mu<M>, A> fa)
    {
        Writer<M, A> writerA = unbox(fa);
        Writer<M, B> writerB = writerA.flatMap(this.monoid, a -> unbox(f.apply(a)));
        return box(writerB);
    }

    @Override
    public <A, B> Function<Kind<Writer.Mu<M>, A>, Kind<Writer.Mu<M>, B>> lift(Kind<Writer.Mu<M>, Function<A, B>> f)
    {
        return fa -> {
            Writer<M, Function<A, B>> fWriter = unbox(f);
            Writer<M, A> writerA = unbox(fa);
            Writer<M, B> writerB = writerA.flatMap(monoid, a -> fWriter.map(g -> g.apply(a)));
            return box(writerB);
        };
    }

    private <W, A> Kind<Writer.Mu<W>, A> box(Writer<W, A> writer)
    {
        return new Holder<>(writer);
    }

    private <W, A> Writer<W, A> unbox(Kind<Writer.Mu<W>, A> kind)
    {
        if (kind instanceof Holder<?, ?> holder)
            return Unchecks.cast(holder);
        else
            throw new KindUnwrapException("Kind instance is not a correct Holder");
    }

}
