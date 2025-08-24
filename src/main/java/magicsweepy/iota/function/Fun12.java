package magicsweepy.iota.function;

import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface Fun12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R>
{

    R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12);

    default Function<T1, Fun11<T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R>> curry()
    {
        return t1 -> (t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

    default BiFunction<T1, T2, Fun10<T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R>> curry2()
    {
        return (t1, t2) -> (t3, t4, t5, t6, t7, t8, t9, t10, t11, t12) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

    default Fun3<T1, T2, T3, Fun9<T4, T5, T6, T7, T8, T9, T10, T11, T12, R>> curry3()
    {
        return (t1, t2, t3) -> (t4, t5, t6, t7, t8, t9, t10, t11, t12) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

    default Fun4<T1, T2, T3, T4, Fun8<T5, T6, T7, T8, T9, T10, T11, T12, R>> curry4()
    {
        return (t1, t2, t3, t4) -> (t5, t6, t7, t8, t9, t10, t11, t12) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

    default Fun5<T1, T2, T3, T4, T5, Fun7<T6, T7, T8, T9, T10, T11, T12, R>> curry5()
    {
        return (t1, t2, t3, t4, t5) -> (t6, t7, t8, t9, t10, t11, t12) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

    default Fun6<T1, T2, T3, T4, T5, T6, Fun6<T7, T8, T9, T10, T11, T12, R>> curry6()
    {
        return (t1, t2, t3, t4, t5, t6) -> (t7, t8, t9, t10, t11, t12) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

    default Fun7<T1, T2, T3, T4, T5, T6, T7, Fun5<T8, T9, T10, T11, T12, R>> curry7()
    {
        return (t1, t2, t3, t4, t5, t6, t7) -> (t8, t9, t10, t11, t12) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

    default Fun8<T1, T2, T3, T4, T5, T6, T7, T8, Fun4<T9, T10, T11, T12, R>> curry8()
    {
        return (t1, t2, t3, t4, t5, t6, t7, t8) -> (t9, t10, t11, t12) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

    default Fun9<T1, T2, T3, T4, T5, T6, T7, T8, T9, Fun3<T10, T11, T12, R>> curry9()
    {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> (t10, t11, t12) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

    default Fun10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, BiFunction<T11, T12, R>> curry10()
    {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> (t11, t12) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

    default Fun11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, Function<T12, R>> curry11()
    {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11) -> t12 -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

}
