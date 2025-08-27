package magicsweepy.iota.optic;

import magicsweepy.iota.kind.tuple.Pair;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class ProjR<F, G, G2> implements Lens<Pair<F, G>, Pair<F, G2>, G, G2>
{

    public static final ProjR<?, ?, ?> INSTANCE = new ProjR<>();

    @Override
    public G view(final Pair<F, G> pair)
    {
        return pair.second();
    }

    @Override
    public Pair<F, G2> update(final G2 newValue, final Pair<F, G> pair)
    {
        return Pair.of(pair.first(), newValue);
    }

    @Override
    public String toString()
    {
        return "projR";
    }

}
