package magicsweepy.iota.kind.function;

import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface Fun15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R>
{

    R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15);

    default Function<T1, Fun14<T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R>> curry()
    {
        return t1 -> (t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    default BiFunction<T1, T2, Fun13<T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R>> curry2()
    {
        return (t1, t2) -> (t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    default Fun3<T1, T2, T3, Fun12<T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R>> curry3()
    {
        return (t1, t2, t3) -> (t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    default Fun4<T1, T2, T3, T4, Fun11<T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R>> curry4()
    {
        return (t1, t2, t3, t4) -> (t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    default Fun5<T1, T2, T3, T4, T5, Fun10<T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R>> curry5()
    {
        return (t1, t2, t3, t4, t5) -> (t6, t7, t8, t9, t10, t11, t12, t13, t14, t15) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    default Fun6<T1, T2, T3, T4, T5, T6, Fun9<T7, T8, T9, T10, T11, T12, T13, T14, T15, R>> curry6()
    {
        return (t1, t2, t3, t4, t5, t6) -> (t7, t8, t9, t10, t11, t12, t13, t14, t15) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    default Fun7<T1, T2, T3, T4, T5, T6, T7, Fun8<T8, T9, T10, T11, T12, T13, T14, T15, R>> curry7()
    {
        return (t1, t2, t3, t4, t5, t6, t7) -> (t8, t9, t10, t11, t12, t13, t14, t15) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    default Fun8<T1, T2, T3, T4, T5, T6, T7, T8, Fun7<T9, T10, T11, T12, T13, T14, T15, R>> curry8()
    {
        return (t1, t2, t3, t4, t5, t6, t7, t8) -> (t9, t10, t11, t12, t13, t14, t15) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    default Fun9<T1, T2, T3, T4, T5, T6, T7, T8, T9, Fun6<T10, T11, T12, T13, T14, T15, R>> curry9()
    {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> (t10, t11, t12, t13, t14, t15) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    default Fun10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Fun5<T11, T12, T13, T14, T15, R>> curry10()
    {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> (t11, t12, t13, t14, t15) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    default Fun11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, Fun4<T12, T13, T14, T15, R>> curry11()
    {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11) -> (t12, t13, t14, t15) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    default Fun12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, Fun3<T13, T14, T15, R>> curry12()
    {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12) -> (t13, t14, t15) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    default Fun13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, BiFunction<T14, T15, R>> curry13()
    {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13) -> (t14, t15) -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    default Fun14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Function<T15, R>> curry14()
    {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14) -> t15 -> apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

}
