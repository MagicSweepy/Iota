package magicsweepy.iota.optic;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class IdAdapter<S, T> implements Adapter<S, T, S, T>
{

    static final IdAdapter<?, ?> INSTANCE = new IdAdapter<>();

    @Override
    public S from(final S s)
    {
        return s;
    }

    @Override
    public T to(final T b)
    {
        return b;
    }

    @Override
    public String toString()
    {
        return "id";
    }

}
