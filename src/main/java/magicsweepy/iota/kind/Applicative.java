package magicsweepy.iota.kind;

import magicsweepy.iota.kind.function.Fun10;
import magicsweepy.iota.kind.function.Fun11;
import magicsweepy.iota.kind.function.Fun12;
import magicsweepy.iota.kind.function.Fun13;
import magicsweepy.iota.kind.function.Fun14;
import magicsweepy.iota.kind.function.Fun15;
import magicsweepy.iota.kind.function.Fun16;
import magicsweepy.iota.kind.function.Fun3;
import magicsweepy.iota.kind.function.Fun4;
import magicsweepy.iota.kind.function.Fun5;
import magicsweepy.iota.kind.function.Fun6;
import magicsweepy.iota.kind.function.Fun7;
import magicsweepy.iota.kind.function.Fun8;
import magicsweepy.iota.kind.function.Fun9;
import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

import java.util.function.BiFunction;
import java.util.function.Function;

@NullMarked
public interface Applicative<F extends Ob, Mu extends Applicative.Mu> extends Functor<F, Mu>
{

    interface Mu extends Functor.Mu {}

    static <F extends Ob, Mu extends Applicative.Mu> Applicative<F, Mu> unbox(final Kind<Mu, F> box)
    {
        return Unchecks.cast(box);
    }

    // region Basic Operations

    <A> Kind<F, A> point(@NonNull final A a);

    <A, B> Function<Kind<F, A>, Kind<F, B>> lift(final Kind<F, Function<A, B>> f);

    default <A, B> Kind<F, B> ap(final Kind<F, Function<A, B>> f, final Kind<F, A> fa)
    {
        return lift(f).apply(fa);
    }

    default <A, B> Kind<F, B> ap(final Function<A, B> f, final Kind<F, A> fa)
    {
        return map(f, fa);
    }

    // endregion

    // region Higher Operations

    default <A, B, R> BiFunction<Kind<F, A>,
                                 Kind<F, B>,
                                 Kind<F, R>> lift2(final Kind<F, BiFunction<A, B, R>> f)
    {
        return (fa, fb) -> ap2(f, fa, fb);
    }

    default <T1, T2, T3, R> Fun3<Kind<F, T1>,
                                 Kind<F, T2>,
                                 Kind<F, T3>,
                                 Kind<F, R>> lift3(final Kind<F, Fun3<T1, T2, T3, R>> f)
    {
        return (ft1, ft2, ft3) -> ap3(f, ft1, ft2, ft3);
    }

    default <T1, T2, T3, T4, R> Fun4<Kind<F, T1>,
                                     Kind<F, T2>,
                                     Kind<F, T3>,
                                     Kind<F, T4>,
                                     Kind<F, R>> lift4(final Kind<F, Fun4<T1, T2, T3, T4, R>> f)
    {
        return (ft1, ft2, ft3, ft4) -> ap4(f, ft1, ft2, ft3, ft4);
    }

    default <T1, T2, T3, T4, T5, R> Fun5<Kind<F, T1>,
                                         Kind<F, T2>,
                                         Kind<F, T3>,
                                         Kind<F, T4>,
                                         Kind<F, T5>,
                                         Kind<F, R>> lift5(final Kind<F, Fun5<T1, T2, T3, T4, T5, R>> f)
    {
        return (ft1, ft2, ft3, ft4, ft5) -> ap5(f, ft1, ft2, ft3, ft4, ft5);
    }

    default <T1, T2, T3, T4, T5, T6, R> Fun6<Kind<F, T1>,
                                             Kind<F, T2>,
                                             Kind<F, T3>,
                                             Kind<F, T4>,
                                             Kind<F, T5>,
                                             Kind<F, T6>,
                                             Kind<F, R>> lift6(final Kind<F, Fun6<T1, T2, T3, T4, T5, T6, R>> f)
    {
        return (ft1, ft2, ft3, ft4, ft5, ft6) -> ap6(f, ft1, ft2, ft3, ft4, ft5, ft6);
    }

    default <T1, T2, T3, T4, T5, T6, T7, R> Fun7<Kind<F, T1>,
                                                 Kind<F, T2>,
                                                 Kind<F, T3>,
                                                 Kind<F, T4>,
                                                 Kind<F, T5>,
                                                 Kind<F, T6>,
                                                 Kind<F, T7>,
                                                 Kind<F, R>> lift7(final Kind<F, Fun7<T1, T2, T3, T4, T5, T6, T7, R>> f)
    {
        return (ft1, ft2, ft3, ft4, ft5, ft6, ft7) -> ap7(f, ft1, ft2, ft3, ft4, ft5, ft6, ft7);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, R> Fun8<Kind<F, T1>,
                                                     Kind<F, T2>,
                                                     Kind<F, T3>,
                                                     Kind<F, T4>,
                                                     Kind<F, T5>,
                                                     Kind<F, T6>,
                                                     Kind<F, T7>,
                                                     Kind<F, T8>,
                                                     Kind<F, R>> lift8(final Kind<F, Fun8<T1, T2, T3, T4, T5, T6, T7, T8, R>> f)
    {
        return (ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8) -> ap8(f, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Fun9<Kind<F, T1>,
                                                         Kind<F, T2>,
                                                         Kind<F, T3>,
                                                         Kind<F, T4>,
                                                         Kind<F, T5>,
                                                         Kind<F, T6>,
                                                         Kind<F, T7>,
                                                         Kind<F, T8>,
                                                         Kind<F, T9>,
                                                         Kind<F, R>> lift9(final Kind<F, Fun9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R>> f)
    {
        return (ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9) -> ap9(f, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> Fun10<Kind<F, T1>,
                                                               Kind<F, T2>,
                                                               Kind<F, T3>,
                                                               Kind<F, T4>,
                                                               Kind<F, T5>,
                                                               Kind<F, T6>,
                                                               Kind<F, T7>,
                                                               Kind<F, T8>,
                                                               Kind<F, T9>,
                                                               Kind<F, T10>,
                                                               Kind<F, R>> lift10(final Kind<F, Fun10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R>> f)
    {
        return (ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10) -> ap10(f, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> Fun11<Kind<F, T1>,
                                                                    Kind<F, T2>,
                                                                    Kind<F, T3>,
                                                                    Kind<F, T4>,
                                                                    Kind<F, T5>,
                                                                    Kind<F, T6>,
                                                                    Kind<F, T7>,
                                                                    Kind<F, T8>,
                                                                    Kind<F, T9>,
                                                                    Kind<F, T10>,
                                                                    Kind<F, T11>,
                                                                    Kind<F, R>> lift11(final Kind<F, Fun11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R>> f)
    {
        return (ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11) -> ap11(f, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> Fun12<Kind<F, T1>,
                                                                         Kind<F, T2>,
                                                                         Kind<F, T3>,
                                                                         Kind<F, T4>,
                                                                         Kind<F, T5>,
                                                                         Kind<F, T6>,
                                                                         Kind<F, T7>,
                                                                         Kind<F, T8>,
                                                                         Kind<F, T9>,
                                                                         Kind<F, T10>,
                                                                         Kind<F, T11>,
                                                                         Kind<F, T12>,
                                                                         Kind<F, R>> lift12(final Kind<F, Fun12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R>> f)
    {
        return (ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11, ft12) -> ap12(f, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11, ft12);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> Fun13<Kind<F, T1>,
                                                                              Kind<F, T2>,
                                                                              Kind<F, T3>,
                                                                              Kind<F, T4>,
                                                                              Kind<F, T5>,
                                                                              Kind<F, T6>,
                                                                              Kind<F, T7>,
                                                                              Kind<F, T8>,
                                                                              Kind<F, T9>,
                                                                              Kind<F, T10>,
                                                                              Kind<F, T11>,
                                                                              Kind<F, T12>,
                                                                              Kind<F, T13>,
                                                                              Kind<F, R>> lift13(final Kind<F, Fun13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R>> f)
    {
        return (ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11, ft12, ft13) -> ap13(f, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11, ft12, ft13);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> Fun14<Kind<F, T1>,
                                                                                   Kind<F, T2>,
                                                                                   Kind<F, T3>,
                                                                                   Kind<F, T4>,
                                                                                   Kind<F, T5>,
                                                                                   Kind<F, T6>,
                                                                                   Kind<F, T7>,
                                                                                   Kind<F, T8>,
                                                                                   Kind<F, T9>,
                                                                                   Kind<F, T10>,
                                                                                   Kind<F, T11>,
                                                                                   Kind<F, T12>,
                                                                                   Kind<F, T13>,
                                                                                   Kind<F, T14>,
                                                                                   Kind<F, R>> lift14(final Kind<F, Fun14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R>> f)
    {
        return (ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11, ft12, ft13, ft14) -> ap14(f, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11, ft12, ft13, ft14);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> Fun15<Kind<F, T1>,
                                                                                        Kind<F, T2>,
                                                                                        Kind<F, T3>,
                                                                                        Kind<F, T4>,
                                                                                        Kind<F, T5>,
                                                                                        Kind<F, T6>,
                                                                                        Kind<F, T7>,
                                                                                        Kind<F, T8>,
                                                                                        Kind<F, T9>,
                                                                                        Kind<F, T10>,
                                                                                        Kind<F, T11>,
                                                                                        Kind<F, T12>,
                                                                                        Kind<F, T13>,
                                                                                        Kind<F, T14>,
                                                                                        Kind<F, T15>,
                                                                                        Kind<F, R>> lift15(final Kind<F, Fun15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R>> f)
    {
        return (ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11, ft12, ft13, ft14, ft15) -> ap15(f, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11, ft12, ft13, ft14, ft15);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> Fun16<Kind<F, T1>,
                                                                                             Kind<F, T2>,
                                                                                             Kind<F, T3>,
                                                                                             Kind<F, T4>,
                                                                                             Kind<F, T5>,
                                                                                             Kind<F, T6>,
                                                                                             Kind<F, T7>,
                                                                                             Kind<F, T8>,
                                                                                             Kind<F, T9>,
                                                                                             Kind<F, T10>,
                                                                                             Kind<F, T11>,
                                                                                             Kind<F, T12>,
                                                                                             Kind<F, T13>,
                                                                                             Kind<F, T14>,
                                                                                             Kind<F, T15>,
                                                                                             Kind<F, T16>,
                                                                                             Kind<F, R>> lift16(final Kind<F, Fun16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R>> f)
    {
        return (ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11, ft12, ft13, ft14, ft15, ft16) -> ap16(f, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, ft11, ft12, ft13, ft14, ft15, ft16);
    }

    default <T1, T2, R> Kind<F, R> ap2(final Kind<F, BiFunction<T1, T2, R>> f,
                                       final Kind<F, T1> t1,
                                       final Kind<F, T2> t2)
    {
        final Function<BiFunction<T1, T2, R>, // TODO Change it to Fun2#curry when all Function struct be unified.
                Function<T1, Function<T2, R>>> curry = g -> a1 -> b1 -> g.apply(a1, b1);
        return ap(ap(map(curry, f), t1), t2);
    }

    default <T1, T2, T3, R> Kind<F, R> ap3(final Kind<F, Fun3<T1, T2, T3, R>> f,
                                           final Kind<F, T1> t1,
                                           final Kind<F, T2> t2,
                                           final Kind<F, T3> t3)
    {
        return ap2(ap(map(Fun3::curry, f), t1), t2, t3);
    }

    default <T1, T2, T3, T4, R> Kind<F, R> ap4(final Kind<F, Fun4<T1, T2, T3, T4, R>> f,
                                               final Kind<F, T1> t1,
                                               final Kind<F, T2> t2,
                                               final Kind<F, T3> t3,
                                               final Kind<F, T4> t4)
    {
        return ap2(ap2(map(Fun4::curry2, f), t1, t2), t3, t4);
    }

    default <T1, T2, T3, T4, T5, R> Kind<F, R> ap5(final Kind<F, Fun5<T1, T2, T3, T4, T5, R>> f,
                                                   final Kind<F, T1> t1,
                                                   final Kind<F, T2> t2,
                                                   final Kind<F, T3> t3,
                                                   final Kind<F, T4> t4,
                                                   final Kind<F, T5> t5)
    {
        return ap3(ap2(map(Fun5::curry2, f), t1, t2), t3, t4, t5);
    }

    default <T1, T2, T3, T4, T5, T6, R> Kind<F, R> ap6(final Kind<F, Fun6<T1, T2, T3, T4, T5, T6, R>> f,
                                                       final Kind<F, T1> t1,
                                                       final Kind<F, T2> t2,
                                                       final Kind<F, T3> t3,
                                                       final Kind<F, T4> t4,
                                                       final Kind<F, T5> t5,
                                                       final Kind<F, T6> t6)
    {
        return ap3(ap3(map(Fun6::curry3, f), t1, t2, t3), t4, t5, t6);
    }

    default <T1, T2, T3, T4, T5, T6, T7, R> Kind<F, R> ap7(final Kind<F, Fun7<T1, T2, T3, T4, T5, T6, T7, R>> f,
                                                           final Kind<F, T1> t1,
                                                           final Kind<F, T2> t2,
                                                           final Kind<F, T3> t3,
                                                           final Kind<F, T4> t4,
                                                           final Kind<F, T5> t5,
                                                           final Kind<F, T6> t6,
                                                           final Kind<F, T7> t7)
    {
        return ap4(ap3(map(Fun7::curry3, f), t1, t2, t3), t4, t5, t6, t7);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, R> Kind<F, R> ap8(final Kind<F, Fun8<T1, T2, T3, T4, T5, T6, T7, T8, R>> f,
                                                               final Kind<F, T1> t1,
                                                               final Kind<F, T2> t2,
                                                               final Kind<F, T3> t3,
                                                               final Kind<F, T4> t4,
                                                               final Kind<F, T5> t5,
                                                               final Kind<F, T6> t6,
                                                               final Kind<F, T7> t7,
                                                               final Kind<F, T8> t8)
    {
        return ap4(ap4(map(Fun8::curry4, f), t1, t2, t3, t4), t5, t6, t7, t8);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Kind<F, R> ap9(final Kind<F, Fun9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R>> f,
                                                                   final Kind<F, T1> t1,
                                                                   final Kind<F, T2> t2,
                                                                   final Kind<F, T3> t3,
                                                                   final Kind<F, T4> t4,
                                                                   final Kind<F, T5> t5,
                                                                   final Kind<F, T6> t6,
                                                                   final Kind<F, T7> t7,
                                                                   final Kind<F, T8> t8,
                                                                   final Kind<F, T9> t9)
    {
        return ap5(ap4(map(Fun9::curry4, f), t1, t2, t3, t4), t5, t6, t7, t8, t9);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> Kind<F, R> ap10(final Kind<F, Fun10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R>> f,
                                                                         final Kind<F, T1> t1,
                                                                         final Kind<F, T2> t2,
                                                                         final Kind<F, T3> t3,
                                                                         final Kind<F, T4> t4,
                                                                         final Kind<F, T5> t5,
                                                                         final Kind<F, T6> t6,
                                                                         final Kind<F, T7> t7,
                                                                         final Kind<F, T8> t8,
                                                                         final Kind<F, T9> t9,
                                                                         final Kind<F, T10> t10)
    {
        return ap5(ap5(map(Fun10::curry5, f), t1, t2, t3, t4, t5), t6, t7, t8, t9, t10);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> Kind<F, R> ap11(final Kind<F, Fun11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R>> f,
                                                                              final Kind<F, T1> t1,
                                                                              final Kind<F, T2> t2,
                                                                              final Kind<F, T3> t3,
                                                                              final Kind<F, T4> t4,
                                                                              final Kind<F, T5> t5,
                                                                              final Kind<F, T6> t6,
                                                                              final Kind<F, T7> t7,
                                                                              final Kind<F, T8> t8,
                                                                              final Kind<F, T9> t9,
                                                                              final Kind<F, T10> t10,
                                                                              final Kind<F, T11> t11)
    {
        return ap6(ap5(map(Fun11::curry5, f), t1, t2, t3, t4, t5), t6, t7, t8, t9, t10, t11);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> Kind<F, R> ap12(final Kind<F, Fun12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R>> f,
                                                                                   final Kind<F, T1> t1,
                                                                                   final Kind<F, T2> t2,
                                                                                   final Kind<F, T3> t3,
                                                                                   final Kind<F, T4> t4,
                                                                                   final Kind<F, T5> t5,
                                                                                   final Kind<F, T6> t6,
                                                                                   final Kind<F, T7> t7,
                                                                                   final Kind<F, T8> t8,
                                                                                   final Kind<F, T9> t9,
                                                                                   final Kind<F, T10> t10,
                                                                                   final Kind<F, T11> t11,
                                                                                   final Kind<F, T12> t12)
    {
        return ap6(ap6(map(Fun12::curry6, f), t1, t2, t3, t4, t5, t6), t7, t8, t9, t10, t11, t12);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> Kind<F, R> ap13(final Kind<F, Fun13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R>> f,
                                                                                        final Kind<F, T1> t1,
                                                                                        final Kind<F, T2> t2,
                                                                                        final Kind<F, T3> t3,
                                                                                        final Kind<F, T4> t4,
                                                                                        final Kind<F, T5> t5,
                                                                                        final Kind<F, T6> t6,
                                                                                        final Kind<F, T7> t7,
                                                                                        final Kind<F, T8> t8,
                                                                                        final Kind<F, T9> t9,
                                                                                        final Kind<F, T10> t10,
                                                                                        final Kind<F, T11> t11,
                                                                                        final Kind<F, T12> t12,
                                                                                        final Kind<F, T13> t13) {
        return ap7(ap6(map(Fun13::curry6, f), t1, t2, t3, t4, t5, t6), t7, t8, t9, t10, t11, t12, t13);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> Kind<F, R> ap14(final Kind<F, Fun14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R>> f,
                                                                                             final Kind<F, T1> t1,
                                                                                             final Kind<F, T2> t2,
                                                                                             final Kind<F, T3> t3,
                                                                                             final Kind<F, T4> t4,
                                                                                             final Kind<F, T5> t5,
                                                                                             final Kind<F, T6> t6,
                                                                                             final Kind<F, T7> t7,
                                                                                             final Kind<F, T8> t8,
                                                                                             final Kind<F, T9> t9,
                                                                                             final Kind<F, T10> t10,
                                                                                             final Kind<F, T11> t11,
                                                                                             final Kind<F, T12> t12,
                                                                                             final Kind<F, T13> t13,
                                                                                             final Kind<F, T14> t14)
    {
        return ap7(ap7(map(Fun14::curry7, f), t1, t2, t3, t4, t5, t6, t7), t8, t9, t10, t11, t12, t13, t14);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> Kind<F, R> ap15(final Kind<F, Fun15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R>> f,
                                                                                                  final Kind<F, T1> t1,
                                                                                                  final Kind<F, T2> t2,
                                                                                                  final Kind<F, T3> t3,
                                                                                                  final Kind<F, T4> t4,
                                                                                                  final Kind<F, T5> t5,
                                                                                                  final Kind<F, T6> t6,
                                                                                                  final Kind<F, T7> t7,
                                                                                                  final Kind<F, T8> t8,
                                                                                                  final Kind<F, T9> t9,
                                                                                                  final Kind<F, T10> t10,
                                                                                                  final Kind<F, T11> t11,
                                                                                                  final Kind<F, T12> t12,
                                                                                                  final Kind<F, T13> t13,
                                                                                                  final Kind<F, T14> t14,
                                                                                                  final Kind<F, T15> t15)
    {
        return ap8(ap7(map(Fun15::curry7, f), t1, t2, t3, t4, t5, t6, t7), t8, t9, t10, t11, t12, t13, t14, t15);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> Kind<F, R> ap16(final Kind<F, Fun16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R>> f,
                                                                                                       final Kind<F, T1> t1,
                                                                                                       final Kind<F, T2> t2,
                                                                                                       final Kind<F, T3> t3,
                                                                                                       final Kind<F, T4> t4,
                                                                                                       final Kind<F, T5> t5,
                                                                                                       final Kind<F, T6> t6,
                                                                                                       final Kind<F, T7> t7,
                                                                                                       final Kind<F, T8> t8,
                                                                                                       final Kind<F, T9> t9,
                                                                                                       final Kind<F, T10> t10,
                                                                                                       final Kind<F, T11> t11,
                                                                                                       final Kind<F, T12> t12,
                                                                                                       final Kind<F, T13> t13,
                                                                                                       final Kind<F, T14> t14,
                                                                                                       final Kind<F, T15> t15,
                                                                                                       final Kind<F, T16> t16)
    {
        return ap8(ap8(map(Fun16::curry8, f), t1, t2, t3, t4, t5, t6, t7, t8), t9, t10, t11, t12, t13, t14, t15, t16);
    }

    default <A, B, R> Kind<F, R> apply2(final BiFunction<A, B, R> f,
                                        final Kind<F, A> a,
                                        final Kind<F, B> b)
    {
        return ap2(point(f), a, b);
    }

    default <T1, T2, T3, R> Kind<F, R> apply3(final Fun3<T1, T2, T3, R> f,
                                              final Kind<F, T1> t1,
                                              final Kind<F, T2> t2,
                                              final Kind<F, T3> t3)
    {
        return ap3(point(f), t1, t2, t3);
    }

    default <T1, T2, T3, T4, R> Kind<F, R> apply4(final Fun4<T1, T2, T3, T4, R> f,
                                                  final Kind<F, T1> t1,
                                                  final Kind<F, T2> t2,
                                                  final Kind<F, T3> t3,
                                                  final Kind<F, T4> t4)
    {
        return ap4(point(f), t1, t2, t3, t4);
    }

    default <T1, T2, T3, T4, T5, R> Kind<F, R> apply5(final Fun5<T1, T2, T3, T4, T5, R> f,
                                                      final Kind<F, T1> t1,
                                                      final Kind<F, T2> t2,
                                                      final Kind<F, T3> t3,
                                                      final Kind<F, T4> t4,
                                                      final Kind<F, T5> t5)
    {
        return ap5(point(f), t1, t2, t3, t4, t5);
    }

    default <T1, T2, T3, T4, T5, T6, R> Kind<F, R> apply6(final Fun6<T1, T2, T3, T4, T5, T6, R> f,
                                                          final Kind<F, T1> t1,
                                                          final Kind<F, T2> t2,
                                                          final Kind<F, T3> t3,
                                                          final Kind<F, T4> t4,
                                                          final Kind<F, T5> t5,
                                                          final Kind<F, T6> t6)
    {
        return ap6(point(f), t1, t2, t3, t4, t5, t6);
    }

    default <T1, T2, T3, T4, T5, T6, T7, R> Kind<F, R> apply7(final Fun7<T1, T2, T3, T4, T5, T6, T7, R> f,
                                                              final Kind<F, T1> t1,
                                                              final Kind<F, T2> t2,
                                                              final Kind<F, T3> t3,
                                                              final Kind<F, T4> t4,
                                                              final Kind<F, T5> t5,
                                                              final Kind<F, T6> t6,
                                                              final Kind<F, T7> t7)
    {
        return ap7(point(f), t1, t2, t3, t4, t5, t6, t7);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, R> Kind<F, R> apply8(final Fun8<T1, T2, T3, T4, T5, T6, T7, T8, R> f,
                                                                  final Kind<F, T1> t1,
                                                                  final Kind<F, T2> t2,
                                                                  final Kind<F, T3> t3,
                                                                  final Kind<F, T4> t4,
                                                                  final Kind<F, T5> t5,
                                                                  final Kind<F, T6> t6,
                                                                  final Kind<F, T7> t7,
                                                                  final Kind<F, T8> t8)
    {
        return ap8(point(f), t1, t2, t3, t4, t5, t6, t7, t8);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Kind<F, R> apply9(final Fun9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> f,
                                                                      final Kind<F, T1> t1,
                                                                      final Kind<F, T2> t2,
                                                                      final Kind<F, T3> t3,
                                                                      final Kind<F, T4> t4,
                                                                      final Kind<F, T5> t5,
                                                                      final Kind<F, T6> t6,
                                                                      final Kind<F, T7> t7,
                                                                      final Kind<F, T8> t8,
                                                                      final Kind<F, T9> t9)
    {
        return ap9(point(f), t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> Kind<F, R> apply10(final Fun10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> f,
                                                                            final Kind<F, T1> t1,
                                                                            final Kind<F, T2> t2,
                                                                            final Kind<F, T3> t3,
                                                                            final Kind<F, T4> t4,
                                                                            final Kind<F, T5> t5,
                                                                            final Kind<F, T6> t6,
                                                                            final Kind<F, T7> t7,
                                                                            final Kind<F, T8> t8,
                                                                            final Kind<F, T9> t9,
                                                                            final Kind<F, T10> t10)
    {
        return ap10(point(f), t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> Kind<F, R> apply11(final Fun11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> f,
                                                                                 final Kind<F, T1> t1,
                                                                                 final Kind<F, T2> t2,
                                                                                 final Kind<F, T3> t3,
                                                                                 final Kind<F, T4> t4,
                                                                                 final Kind<F, T5> t5,
                                                                                 final Kind<F, T6> t6,
                                                                                 final Kind<F, T7> t7,
                                                                                 final Kind<F, T8> t8,
                                                                                 final Kind<F, T9> t9,
                                                                                 final Kind<F, T10> t10,
                                                                                 final Kind<F, T11> t11)
    {
        return ap11(point(f), t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> Kind<F, R> apply12(final Fun12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> f,
                                                                                      final Kind<F, T1> t1,
                                                                                      final Kind<F, T2> t2,
                                                                                      final Kind<F, T3> t3,
                                                                                      final Kind<F, T4> t4,
                                                                                      final Kind<F, T5> t5,
                                                                                      final Kind<F, T6> t6,
                                                                                      final Kind<F, T7> t7,
                                                                                      final Kind<F, T8> t8,
                                                                                      final Kind<F, T9> t9,
                                                                                      final Kind<F, T10> t10,
                                                                                      final Kind<F, T11> t11,
                                                                                      final Kind<F, T12> t12)
    {
        return ap12(point(f), t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> Kind<F, R> apply13(final Fun13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> f,
                                                                                           final Kind<F, T1> t1,
                                                                                           final Kind<F, T2> t2,
                                                                                           final Kind<F, T3> t3,
                                                                                           final Kind<F, T4> t4,
                                                                                           final Kind<F, T5> t5,
                                                                                           final Kind<F, T6> t6,
                                                                                           final Kind<F, T7> t7,
                                                                                           final Kind<F, T8> t8,
                                                                                           final Kind<F, T9> t9,
                                                                                           final Kind<F, T10> t10,
                                                                                           final Kind<F, T11> t11,
                                                                                           final Kind<F, T12> t12,
                                                                                           final Kind<F, T13> t13)
    {
        return ap13(point(f), t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> Kind<F, R> apply14(final Fun14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> f,
                                                                                                final Kind<F, T1> t1,
                                                                                                final Kind<F, T2> t2,
                                                                                                final Kind<F, T3> t3,
                                                                                                final Kind<F, T4> t4,
                                                                                                final Kind<F, T5> t5,
                                                                                                final Kind<F, T6> t6,
                                                                                                final Kind<F, T7> t7,
                                                                                                final Kind<F, T8> t8,
                                                                                                final Kind<F, T9> t9,
                                                                                                final Kind<F, T10> t10,
                                                                                                final Kind<F, T11> t11,
                                                                                                final Kind<F, T12> t12,
                                                                                                final Kind<F, T13> t13,
                                                                                                final Kind<F, T14> t14)
    {
        return ap14(point(f), t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> Kind<F, R> apply15(final Fun15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> f,
                                                                                                     final Kind<F, T1> t1,
                                                                                                     final Kind<F, T2> t2,
                                                                                                     final Kind<F, T3> t3,
                                                                                                     final Kind<F, T4> t4,
                                                                                                     final Kind<F, T5> t5,
                                                                                                     final Kind<F, T6> t6,
                                                                                                     final Kind<F, T7> t7,
                                                                                                     final Kind<F, T8> t8,
                                                                                                     final Kind<F, T9> t9,
                                                                                                     final Kind<F, T10> t10,
                                                                                                     final Kind<F, T11> t11,
                                                                                                     final Kind<F, T12> t12,
                                                                                                     final Kind<F, T13> t13,
                                                                                                     final Kind<F, T14> t14,
                                                                                                     final Kind<F, T15> t15)
    {
        return ap15(point(f), t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> Kind<F, R> apply16(final Fun16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> f,
                                                                                                          final Kind<F, T1> t1,
                                                                                                          final Kind<F, T2> t2,
                                                                                                          final Kind<F, T3> t3,
                                                                                                          final Kind<F, T4> t4,
                                                                                                          final Kind<F, T5> t5,
                                                                                                          final Kind<F, T6> t6,
                                                                                                          final Kind<F, T7> t7,
                                                                                                          final Kind<F, T8> t8,
                                                                                                          final Kind<F, T9> t9,
                                                                                                          final Kind<F, T10> t10,
                                                                                                          final Kind<F, T11> t11,
                                                                                                          final Kind<F, T12> t12,
                                                                                                          final Kind<F, T13> t13,
                                                                                                          final Kind<F, T14> t14,
                                                                                                          final Kind<F, T15> t15,
                                                                                                          final Kind<F, T16> t16)
    {
        return ap16(point(f), t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
    }

    // endregion

}
