package magicsweepy.iota.kind.reader;

import com.github.bsideup.jabel.Desugar;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.KindUnwrapException;
import magicsweepy.iota.kind.Monad;
import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

@NullMarked
public final class ReaderMonad<R> implements Monad<Reader.Mu<R>>
{

    @Desugar
    /* package */ record Holder<R, A>(Reader<R, A> reader) implements Kind<Reader.Mu<R>, A> {}

    @Override
    public <A> Kind<Reader.Mu<R>, A> of(@Nullable A value)
    {
        return box(Reader.set(value));
    }

    @Override
    public <A> Kind<Reader.Mu<R>, A> point(A value)
    {
        return of(value);
    }

    @Override
    public <A, B> Kind<Reader.Mu<R>, B> map(Function<? super A, ? extends B> f,
                                            Kind<Reader.Mu<R>, A> fa)
    {
        Reader<R, A> readerA = unbox(fa);
        Reader<R, B> readerB = readerA.map(f);
        return box(readerB);
    }

    @Override
    public <A, B> Kind<Reader.Mu<R>, B> flatMap(Function<? super A, ? extends Kind<Reader.Mu<R>, B>> f,
                                                Kind<Reader.Mu<R>, A> fa)
    {
        Reader<R, A> readerA = unbox(fa);
        Reader<R, B> readerB = readerA.flatMap(a -> unbox(f.apply(a)));
        return box(readerB);
    }

    @Override
    public <A, B> Function<Kind<Reader.Mu<R>, A>, Kind<Reader.Mu<R>, B>> lift(Kind<Reader.Mu<R>, Function<A, B>> f)
    {
        return fa -> {
            Reader<R, Function<A, B>> fReader = unbox(f);
            Reader<R, A> readerA = unbox(fa);
            Reader<R, B> readerB = readerA.flatMap(a -> fReader.map(g -> g.apply(a)));
            return box(readerB);
        };
    }

    private <A> Kind<Reader.Mu<R>, A> box(Reader<R, A> reader)
    {
        return new Holder<>(reader);
    }

    private <A> Reader<R, A> unbox(@Nullable Kind<Reader.Mu<R>, A> kind)
    {
        if (kind instanceof Holder<?, ?> holder)
            return Unchecks.cast(holder);
        else
            throw new KindUnwrapException("Kind instance is not a correct Holder");
    }

}
