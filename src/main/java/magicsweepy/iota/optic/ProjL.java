package magicsweepy.iota.optic;

import magicsweepy.iota.kind.tuple.Pair;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class ProjL<F1, G, F2> implements Lens<Pair<F1, G>, Pair<F2, G>, F1, F2>
{

    public static final ProjL<?, ?, ?> INSTANCE = new ProjL<>();

    @Override
    public F1 view(final Pair<F1, G> pair)
    {
        return pair.first();
    }

    @Override
    public Pair<F2, G> update(final F2 newValue, final Pair<F1, G> pair)
    {
        return Pair.of(newValue, pair.second());
    }

    @Override
    public String toString()
    {
        return "projL";
    }

}