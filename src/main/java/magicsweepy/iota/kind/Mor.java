package magicsweepy.iota.kind;

import magicsweepy.iota.kind.Products.Prod1;
import magicsweepy.iota.kind.Products.Prod10;
import magicsweepy.iota.kind.Products.Prod11;
import magicsweepy.iota.kind.Products.Prod12;
import magicsweepy.iota.kind.Products.Prod13;
import magicsweepy.iota.kind.Products.Prod14;
import magicsweepy.iota.kind.Products.Prod15;
import magicsweepy.iota.kind.Products.Prod16;
import magicsweepy.iota.kind.Products.Prod2;
import magicsweepy.iota.kind.Products.Prod3;
import magicsweepy.iota.kind.Products.Prod4;
import magicsweepy.iota.kind.Products.Prod5;
import magicsweepy.iota.kind.Products.Prod6;
import magicsweepy.iota.kind.Products.Prod7;
import magicsweepy.iota.kind.Products.Prod8;
import magicsweepy.iota.kind.Products.Prod9;
import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface Mor<F extends Ob, Mu extends Mor.Mu> extends Kind<Mor.Mu, F>
{

    interface Mu extends Ob {}

    static <F extends Ob, Mu extends Mor.Mu> Mor<F, Mu> unbox(final Kind<Mu, F> box)
    {
        return Unchecks.cast(box);
    }

    default <T1> Prod1<F, T1> group(final Kind<F, T1> t1)
    {
        return new Prod1<>(t1);
    }

    default <T1, T2> Prod2<F, T1, T2> group(final Kind<F, T1> t1,
                                            final Kind<F, T2> t2)
    {
        return new Prod2<>(t1, t2);
    }

    default <T1, T2, T3> Prod3<F, T1, T2, T3> group(final Kind<F, T1> t1,
                                                    final Kind<F, T2> t2,
                                                    final Kind<F, T3> t3)
    {
        return new Prod3<>(t1, t2, t3);
    }

    default <T1, T2, T3, T4> Prod4<F, T1, T2, T3, T4> group(final Kind<F, T1> t1,
                                                            final Kind<F, T2> t2,
                                                            final Kind<F, T3> t3,
                                                            final Kind<F, T4> t4)
    {
        return new Prod4<>(t1, t2, t3, t4);
    }

    default <T1, T2, T3, T4, T5> Prod5<F, T1, T2, T3, T4, T5> group(final Kind<F, T1> t1,
                                                                    final Kind<F, T2> t2,
                                                                    final Kind<F, T3> t3,
                                                                    final Kind<F, T4> t4,
                                                                    final Kind<F, T5> t5)
    {
        return new Prod5<>(t1, t2, t3, t4, t5);
    }

    default <T1, T2, T3, T4, T5, T6> Prod6<F, T1, T2, T3, T4, T5, T6> group(final Kind<F, T1> t1,
                                                                            final Kind<F, T2> t2,
                                                                            final Kind<F, T3> t3,
                                                                            final Kind<F, T4> t4,
                                                                            final Kind<F, T5> t5,
                                                                            final Kind<F, T6> t6)
    {
        return new Prod6<>(t1, t2, t3, t4, t5, t6);
    }

    default <T1, T2, T3, T4, T5, T6, T7> Prod7<F, T1, T2, T3, T4, T5, T6, T7> group(final Kind<F, T1> t1,
                                                                                    final Kind<F, T2> t2,
                                                                                    final Kind<F, T3> t3,
                                                                                    final Kind<F, T4> t4,
                                                                                    final Kind<F, T5> t5,
                                                                                    final Kind<F, T6> t6,
                                                                                    final Kind<F, T7> t7)
    {
        return new Prod7<>(t1, t2, t3, t4, t5, t6, t7);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8> Prod8<F, T1, T2, T3, T4, T5, T6, T7, T8> group(final Kind<F, T1> t1,
                                                                                            final Kind<F, T2> t2,
                                                                                            final Kind<F, T3> t3,
                                                                                            final Kind<F, T4> t4,
                                                                                            final Kind<F, T5> t5,
                                                                                            final Kind<F, T6> t6,
                                                                                            final Kind<F, T7> t7,
                                                                                            final Kind<F, T8> t8)
    {
        return new Prod8<>(t1, t2, t3, t4, t5, t6, t7, t8);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9> Prod9<F, T1, T2, T3, T4, T5, T6, T7, T8, T9> group(final Kind<F, T1> t1,
                                                                                                    final Kind<F, T2> t2,
                                                                                                    final Kind<F, T3> t3,
                                                                                                    final Kind<F, T4> t4,
                                                                                                    final Kind<F, T5> t5,
                                                                                                    final Kind<F, T6> t6,
                                                                                                    final Kind<F, T7> t7,
                                                                                                    final Kind<F, T8> t8,
                                                                                                    final Kind<F, T9> t9)
    {
        return new Prod9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Prod10<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> group(final Kind<F, T1> t1,
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
        return new Prod10<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Prod11<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> group(final Kind<F, T1> t1,
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
        return new Prod11<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Prod12<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> group(final Kind<F, T1> t1,
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
        return new Prod12<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Prod13<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> group(final Kind<F, T1> t1,
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
        return new Prod13<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Prod14<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> group(final Kind<F, T1> t1,
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
        return new Prod14<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Prod15<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> group(final Kind<F, T1> t1,
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
        return new Prod15<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Prod16<F, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> group(final Kind<F, T1> t1,
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
        return new Prod16<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
    }

}
