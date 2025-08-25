package magicsweepy.iota.kind.product;

import magicsweepy.iota.kind.IdF;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface Products
{

    static <T1, T2> Prod2<IdF.Mu, T1, T2> of(final T1 t1,
                                             final T2 t2)
    {
        return new Prod2<>(IdF.create(t1), IdF.create(t2));
    }

    static <T1, T2, T3> Prod3<IdF.Mu, T1, T2, T3> of (final T1 t1,
                                                      final T2 t2,
                                                      final T3 t3)
    {
        return new Prod3<>(IdF.create(t1), IdF.create(t2), IdF.create(t3));
    }

    static <T1, T2, T3, T4> Prod4<IdF.Mu, T1, T2, T3, T4> of(final T1 t1,
                                                             final T2 t2,
                                                             final T3 t3,
                                                             final T4 t4)
    {
        return new Prod4<>(IdF.create(t1), IdF.create(t2), IdF.create(t3), IdF.create(t4));
    }

    static <T1, T2, T3, T4, T5> Prod5<IdF.Mu, T1, T2, T3, T4, T5> of(final T1 t1,
                                                                     final T2 t2,
                                                                     final T3 t3,
                                                                     final T4 t4,
                                                                     final T5 t5)
    {
        return new Prod5<>(IdF.create(t1), IdF.create(t2), IdF.create(t3), IdF.create(t4), IdF.create(t5));
    }

    static <T1, T2, T3, T4, T5, T6> Prod6<IdF.Mu, T1, T2, T3, T4, T5, T6> of(final T1 t1,
                                                                             final T2 t2,
                                                                             final T3 t3,
                                                                             final T4 t4,
                                                                             final T5 t5,
                                                                             final T6 t6)
    {
        return new Prod6<>(IdF.create(t1), IdF.create(t2), IdF.create(t3), IdF.create(t4), IdF.create(t5), IdF.create(t6));
    }

    static <T1, T2, T3, T4, T5, T6, T7> Prod7<IdF.Mu, T1, T2, T3, T4, T5, T6, T7> of(final T1 t1,
                                                                                     final T2 t2,
                                                                                     final T3 t3,
                                                                                     final T4 t4,
                                                                                     final T5 t5,
                                                                                     final T6 t6,
                                                                                     final T7 t7)
    {
        return new Prod7<>(IdF.create(t1), IdF.create(t2), IdF.create(t3), IdF.create(t4), IdF.create(t5), IdF.create(t6), IdF.create(t7));
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8> Prod8<IdF.Mu, T1, T2, T3, T4, T5, T6, T7, T8> of(final T1 t1,
                                                                                             final T2 t2,
                                                                                             final T3 t3,
                                                                                             final T4 t4,
                                                                                             final T5 t5,
                                                                                             final T6 t6,
                                                                                             final T7 t7,
                                                                                             final T8 t8)
    {
        return new Prod8<>(IdF.create(t1), IdF.create(t2), IdF.create(t3), IdF.create(t4), IdF.create(t5), IdF.create(t6), IdF.create(t7), IdF.create(t8));
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Prod9<IdF.Mu, T1, T2, T3, T4, T5, T6, T7, T8, T9> of(final T1 t1,
                                                                                                     final T2 t2,
                                                                                                     final T3 t3,
                                                                                                     final T4 t4,
                                                                                                     final T5 t5,
                                                                                                     final T6 t6,
                                                                                                     final T7 t7,
                                                                                                     final T8 t8,
                                                                                                     final T9 t9)
    {
        return new Prod9<>(IdF.create(t1), IdF.create(t2), IdF.create(t3), IdF.create(t4), IdF.create(t5), IdF.create(t6), IdF.create(t7), IdF.create(t8), IdF.create(t9));
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Prod10<IdF.Mu, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> of(final T1 t1,
                                                                                                                final T2 t2,
                                                                                                                final T3 t3,
                                                                                                                final T4 t4,
                                                                                                                final T5 t5,
                                                                                                                final T6 t6,
                                                                                                                final T7 t7,
                                                                                                                final T8 t8,
                                                                                                                final T9 t9,
                                                                                                                final T10 t10)
    {
        return new Prod10<>(IdF.create(t1), IdF.create(t2), IdF.create(t3), IdF.create(t4), IdF.create(t5), IdF.create(t6), IdF.create(t7), IdF.create(t8), IdF.create(t9), IdF.create(t10));
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Prod11<IdF.Mu, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> of(final T1 t1,
                                                                                                                          final T2 t2,
                                                                                                                          final T3 t3,
                                                                                                                          final T4 t4,
                                                                                                                          final T5 t5,
                                                                                                                          final T6 t6,
                                                                                                                          final T7 t7,
                                                                                                                          final T8 t8,
                                                                                                                          final T9 t9,
                                                                                                                          final T10 t10,
                                                                                                                          final T11 t11)
    {
        return new Prod11<>(IdF.create(t1), IdF.create(t2), IdF.create(t3), IdF.create(t4), IdF.create(t5), IdF.create(t6), IdF.create(t7), IdF.create(t8), IdF.create(t9), IdF.create(t10), IdF.create(t11));
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Prod12<IdF.Mu, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> of(final T1 t1,
                                                                                                                                    final T2 t2,
                                                                                                                                    final T3 t3,
                                                                                                                                    final T4 t4,
                                                                                                                                    final T5 t5,
                                                                                                                                    final T6 t6,
                                                                                                                                    final T7 t7,
                                                                                                                                    final T8 t8,
                                                                                                                                    final T9 t9,
                                                                                                                                    final T10 t10,
                                                                                                                                    final T11 t11,
                                                                                                                                    final T12 t12)
    {
        return new Prod12<>(IdF.create(t1), IdF.create(t2), IdF.create(t3), IdF.create(t4), IdF.create(t5), IdF.create(t6), IdF.create(t7), IdF.create(t8), IdF.create(t9), IdF.create(t10), IdF.create(t11), IdF.create(t12));
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Prod13<IdF.Mu, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> of(final T1 t1,
                                                                                                                                              final T2 t2,
                                                                                                                                              final T3 t3,
                                                                                                                                              final T4 t4,
                                                                                                                                              final T5 t5,
                                                                                                                                              final T6 t6,
                                                                                                                                              final T7 t7,
                                                                                                                                              final T8 t8,
                                                                                                                                              final T9 t9,
                                                                                                                                              final T10 t10,
                                                                                                                                              final T11 t11,
                                                                                                                                              final T12 t12,
                                                                                                                                              final T13 t13)
    {
        return new Prod13<>(IdF.create(t1), IdF.create(t2), IdF.create(t3), IdF.create(t4), IdF.create(t5), IdF.create(t6), IdF.create(t7), IdF.create(t8), IdF.create(t9), IdF.create(t10), IdF.create(t11), IdF.create(t12), IdF.create(t13));
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Prod14<IdF.Mu, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> of(final T1 t1,
                                                                                                                                                        final T2 t2,
                                                                                                                                                        final T3 t3,
                                                                                                                                                        final T4 t4,
                                                                                                                                                        final T5 t5,
                                                                                                                                                        final T6 t6,
                                                                                                                                                        final T7 t7,
                                                                                                                                                        final T8 t8,
                                                                                                                                                        final T9 t9,
                                                                                                                                                        final T10 t10,
                                                                                                                                                        final T11 t11,
                                                                                                                                                        final T12 t12,
                                                                                                                                                        final T13 t13,
                                                                                                                                                        final T14 t14)
    {
        return new Prod14<>(IdF.create(t1), IdF.create(t2), IdF.create(t3), IdF.create(t4), IdF.create(t5), IdF.create(t6), IdF.create(t7), IdF.create(t8), IdF.create(t9), IdF.create(t10), IdF.create(t11), IdF.create(t12), IdF.create(t13), IdF.create(t14));
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Prod15<IdF.Mu, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> of(final T1 t1,
                                                                                                                                                                  final T2 t2,
                                                                                                                                                                  final T3 t3,
                                                                                                                                                                  final T4 t4,
                                                                                                                                                                  final T5 t5,
                                                                                                                                                                  final T6 t6,
                                                                                                                                                                  final T7 t7,
                                                                                                                                                                  final T8 t8,
                                                                                                                                                                  final T9 t9,
                                                                                                                                                                  final T10 t10,
                                                                                                                                                                  final T11 t11,
                                                                                                                                                                  final T12 t12,
                                                                                                                                                                  final T13 t13,
                                                                                                                                                                  final T14 t14,
                                                                                                                                                                  final T15 t15)
    {
        return new Prod15<>(IdF.create(t1), IdF.create(t2), IdF.create(t3), IdF.create(t4), IdF.create(t5), IdF.create(t6), IdF.create(t7), IdF.create(t8), IdF.create(t9), IdF.create(t10), IdF.create(t11), IdF.create(t12), IdF.create(t13), IdF.create(t14), IdF.create(t15));
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Prod16<IdF.Mu, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> of(final T1 t1,
                                                                                                                                                                            final T2 t2,
                                                                                                                                                                            final T3 t3,
                                                                                                                                                                            final T4 t4,
                                                                                                                                                                            final T5 t5,
                                                                                                                                                                            final T6 t6,
                                                                                                                                                                            final T7 t7,
                                                                                                                                                                            final T8 t8,
                                                                                                                                                                            final T9 t9,
                                                                                                                                                                            final T10 t10,
                                                                                                                                                                            final T11 t11,
                                                                                                                                                                            final T12 t12,
                                                                                                                                                                            final T13 t13,
                                                                                                                                                                            final T14 t14,
                                                                                                                                                                            final T15 t15,
                                                                                                                                                                            final T16 t16)
    {
        return new Prod16<>(IdF.create(t1), IdF.create(t2), IdF.create(t3), IdF.create(t4), IdF.create(t5), IdF.create(t6), IdF.create(t7), IdF.create(t8), IdF.create(t9), IdF.create(t10), IdF.create(t11), IdF.create(t12), IdF.create(t13), IdF.create(t14), IdF.create(t15), IdF.create(t16));
    }

}
