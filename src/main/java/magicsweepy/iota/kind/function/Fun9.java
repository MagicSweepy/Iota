package magicsweepy.iota.kind.function;

import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface Fun9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R>
{

    R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9);

    default Function<T1, Fun8<T2, T3, T4, T5, T6, T7, T8, T9, R>> curry()
    {
        return t1 -> (t2, t3, t4, t5, t6, t7, t8, t9) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    default BiFunction<T1, T2, Fun7<T3, T4, T5, T6, T7, T8, T9, R>> curry2()
    {
        return (t1, t2) -> (t3, t4, t5, t6, t7, t8, t9) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    default Fun3<T1, T2, T3, Fun6<T4, T5, T6, T7, T8, T9, R>> curry3()
    {
        return (t1, t2, t3) -> (t4, t5, t6, t7, t8, t9) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    default Fun4<T1, T2, T3, T4, Fun5<T5, T6, T7, T8, T9, R>> curry4()
    {
        return (t1, t2, t3, t4) -> (t5, t6, t7, t8, t9) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    default Fun5<T1, T2, T3, T4, T5, Fun4<T6, T7, T8, T9, R>> curry5()
    {
        return (t1, t2, t3, t4, t5) -> (t6, t7, t8, t9) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    default Fun6<T1, T2, T3, T4, T5, T6, Fun3<T7, T8, T9, R>> curry6()
    {
        return (t1, t2, t3, t4, t5, t6) -> (t7, t8, t9) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    default Fun7<T1, T2, T3, T4, T5, T6, T7, BiFunction<T8, T9, R>> curry7()
    {
        return (t1, t2, t3, t4, t5, t6, t7) -> (t8, t9) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    default Fun8<T1, T2, T3, T4, T5, T6, T7, T8, Function<T9, R>> curry8()
    {
        return (t1, t2, t3, t4, t5, t6, t7, t8) -> t9 -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

}
