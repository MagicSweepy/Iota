package magicsweepy.iota.function;

import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface Fun10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R>
{

    R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10);

    default Function<T1, Fun9<T2, T3, T4, T5, T6, T7, T8, T9, T10, R>> curry()
    {
        return t1 -> (t2, t3, t4, t5, t6, t7, t8, t9, t10) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    default BiFunction<T1, T2, Fun8<T3, T4, T5, T6, T7, T8, T9, T10, R>> curry2()
    {
        return (t1, t2) -> (t3, t4, t5, t6, t7, t8, t9, t10) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    default Fun3<T1, T2, T3, Fun7<T4, T5, T6, T7, T8, T9, T10, R>> curry3()
    {
        return (t1, t2, t3) -> (t4, t5, t6, t7, t8, t9, t10) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    default Fun4<T1, T2, T3, T4, Fun6<T5, T6, T7, T8, T9, T10, R>> curry4()
    {
        return (t1, t2, t3, t4) -> (t5, t6, t7, t8, t9, t10) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    default Fun5<T1, T2, T3, T4, T5, Fun5<T6, T7, T8, T9, T10, R>> curry5()
    {
        return (t1, t2, t3, t4, t5) -> (t6, t7, t8, t9, t10) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    default Fun6<T1, T2, T3, T4, T5, T6, Fun4<T7, T8, T9, T10, R>> curry6()
    {
        return (t1, t2, t3, t4, t5, t6) -> (t7, t8, t9, t10) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    default Fun7<T1, T2, T3, T4, T5, T6, T7, Fun3<T8, T9, T10, R>> curry7()
    {
        return (t1, t2, t3, t4, t5, t6, t7) -> (t8, t9, t10) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    default Fun8<T1, T2, T3, T4, T5, T6, T7, T8, BiFunction<T9, T10, R>> curry8()
    {
        return (t1, t2, t3, t4, t5, t6, t7, t8) -> (t9, t10) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    default Fun9<T1, T2, T3, T4, T5, T6, T7, T8, T9, Function<T10, R>> curry9()
    {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> t10 -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

}
