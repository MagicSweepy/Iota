package magicsweepy.iota.function;

import java.util.function.Function;

@FunctionalInterface
public interface Fun2<T1, T2, R>
{

    R apply(T1 t1, T2 t2);

    default Function<T1, Function<T2, R>> curry()
    {
        return t1 -> (t2 -> apply(t1, t2));
    }

}
