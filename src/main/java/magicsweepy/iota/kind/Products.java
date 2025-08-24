package magicsweepy.iota.kind;

import com.github.bsideup.jabel.Desugar;
import magicsweepy.iota.function.Fun10;
import magicsweepy.iota.function.Fun11;
import magicsweepy.iota.function.Fun12;
import magicsweepy.iota.function.Fun13;
import magicsweepy.iota.function.Fun14;
import magicsweepy.iota.function.Fun15;
import magicsweepy.iota.function.Fun16;
import magicsweepy.iota.function.Fun3;
import magicsweepy.iota.function.Fun4;
import magicsweepy.iota.function.Fun5;
import magicsweepy.iota.function.Fun6;
import magicsweepy.iota.function.Fun7;
import magicsweepy.iota.function.Fun8;
import magicsweepy.iota.function.Fun9;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Products
{

    static <T1, T2> Prod2<IdF.@NonNull Mu, T1, T2> of(final T1 t1, final T2 t2)
    {
        return new Prod2<>(IdF.create(t1), IdF.create(t2));
    }

    @NullMarked
    @Desugar
    record Prod1<F extends Ob, T1>(Kind<F, T1> t1)
    {

        public <T2> Prod2<F, T1, T2> and(final Kind<F, T2> t2)
        {
            return new Prod2<>(t1, t2);
        }

        public <T2, T3> Prod3<F, T1, T2, T3> and(final Prod2<F, T2, T3> p)
        {
            return new Prod3<>(t1, p.t1, p.t2);
        }

        public <T2, T3, T4> Prod4<F, T1, T2, T3, T4> and(final Prod3<F, T2, T3, T4> p)
        {
            return new Prod4<>(t1, p.t1, p.t2, p.t3);
        }

        public <T2, T3, T4, T5> Prod5<F, T1, T2, T3, T4, T5> and(final Prod4<F, T2, T3, T4, T5> p)
        {
            return new Prod5<>(t1, p.t1, p.t2, p.t3, p.t4);
        }

        public <T2, T3, T4, T5, T6> Prod6<F, T1, T2, T3, T4, T5, T6> and(final Prod5<F, T2, T3, T4, T5, T6> p)
        {
            return new Prod6<>(t1, p.t1, p.t2, p.t3, p.t4, p.t5);
        }

        public <T2, T3, T4, T5, T6, T7> Prod7<F, T1, T2, T3, T4, T5, T6, T7> and(final Prod6<F, T2, T3, T4, T5, T6, T7> p)
        {
            return new Prod7<>(t1, p.t1, p.t2, p.t3, p.t4, p.t5, p.t6);
        }

        public <T2, T3, T4, T5, T6, T7, T8> Prod8<F, T1, T2, T3, T4, T5, T6, T7, T8> and(final Prod7<F, T2, T3, T4, T5, T6, T7, T8> p)
        {
            return new Prod8<>(t1, p.t1, p.t2, p.t3, p.t4, p.t5, p.t6, p.t7);
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> ap,
                                    final Function<T1, R> f)
        {
            return apply(ap, ap.point(f));
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> ap,
                                    final Kind<F, Function<T1, R>> f)
        {
            return ap.ap(f, t1);
        }

    }

    @NullMarked
    @Desugar
    record Prod2<F extends Ob, T1, T2>(Kind<F, T1> t1,
                                       Kind<F, T2> t2)
    {

        public <T3> Prod3<F, T1, T2, T3> and(final Kind<F, T3> t3)
        {
            return new Prod3<>(t1, t2, t3);
        }

        public <T3, T4> Prod4<F, T1, T2, T3, T4> and(final Prod2<F, T3, T4> p)
        {
            return new Prod4<>(t1, t2, p.t1, p.t2);
        }

        public <T3, T4, T5> Prod5<F, T1, T2, T3, T4, T5> and(final Prod3<F, T3, T4, T5> p)
        {
            return new Prod5<>(t1, t2, p.t1, p.t2, p.t3);
        }

        public <T3, T4, T5, T6> Prod6<F, T1, T2, T3, T4, T5, T6> and(final Prod4<F, T3, T4, T5, T6> p)
        {
            return new Prod6<>(t1, t2, p.t1, p.t2, p.t3, p.t4);
        }

        public <T3, T4, T5, T6, T7> Prod7<F, T1, T2, T3, T4, T5, T6, T7> and(final Prod5<F, T3, T4, T5, T6, T7> p)
        {
            return new Prod7<>(t1, t2, p.t1, p.t2, p.t3, p.t4, p.t5);
        }

        public <T3, T4, T5, T6, T7, T8> Prod8<F, T1, T2, T3, T4, T5, T6, T7, T8> and(final Prod6<F, T3, T4, T5, T6, T7, T8> p)
        {
            return new Prod8<>(t1, t2, p.t1, p.t2, p.t3, p.t4, p.t5, p.t6);
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final BiFunction<T1, T2, R> f)
        {
            return apply(instance, instance.point(f));
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Kind<F, BiFunction<T1, T2, R>> f)
        {
            return instance.ap2(f, t1, t2);
        }

    }

    @NullMarked
    @Desugar
    record Prod3<F extends Ob, T1, T2, T3>(Kind<F, T1> t1,
                                           Kind<F, T2> t2,
                                           Kind<F, T3> t3)
    {

        public <T4> Prod4<F, T1, T2, T3, T4> and(final Kind<F, T4> t4)
        {
            return new Prod4<>(t1, t2, t3, t4);
        }

        public <T4, T5> Prod5<F, T1, T2, T3, T4, T5> and(final Prod2<F, T4, T5> p)
        {
            return new Prod5<>(t1, t2, t3, p.t1, p.t2);
        }

        public <T4, T5, T6> Prod6<F, T1, T2, T3, T4, T5, T6> and(final Prod3<F, T4, T5, T6> p)
        {
            return new Prod6<>(t1, t2, t3, p.t1, p.t2, p.t3);
        }

        public <T4, T5, T6, T7> Prod7<F, T1, T2, T3, T4, T5, T6, T7> and(final Prod4<F, T4, T5, T6, T7> p)
        {
            return new Prod7<>(t1, t2, t3, p.t1, p.t2, p.t3, p.t4);
        }

        public <T4, T5, T6, T7, T8> Prod8<F, T1, T2, T3, T4, T5, T6, T7, T8> and(final Prod5<F, T4, T5, T6, T7, T8> p)
        {
            return new Prod8<>(t1, t2, t3, p.t1, p.t2, p.t3, p.t4, p.t5);
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Fun3<T1, T2, T3, R> function)
        {
            return apply(instance, instance.point(function));
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Kind<F, Fun3<T1, T2, T3, R>> function)
        {
            return instance.ap3(function, t1, t2, t3);
        }

    }

    @NullMarked
    @Desugar
    record Prod4<F extends Ob, T1, T2, T3, T4>(Kind<F, T1> t1,
                                               Kind<F, T2> t2,
                                               Kind<F, T3> t3,
                                               Kind<F, T4> t4)
    {

        public <T5> Prod5<F, T1, T2, T3, T4, T5> and(final Kind<F, T5> t5)
        {
            return new Prod5<>(t1, t2, t3, t4, t5);
        }

        public <T5, T6> Prod6<F, T1, T2, T3, T4, T5, T6> and(final Prod2<F, T5, T6> p)
        {
            return new Prod6<>(t1, t2, t3, t4, p.t1, p.t2);
        }

        public <T5, T6, T7> Prod7<F, T1, T2, T3, T4, T5, T6, T7> and(final Prod3<F, T5, T6, T7> p)
        {
            return new Prod7<>(t1, t2, t3, t4, p.t1, p.t2, p.t3);
        }

        public <T5, T6, T7, T8> Prod8<F, T1, T2, T3, T4, T5, T6, T7, T8> and(final Prod4<F, T5, T6, T7, T8> p)
        {
            return new Prod8<>(t1, t2, t3, t4, p.t1, p.t2, p.t3, p.t4);
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Fun4<T1, T2, T3, T4, R> f)
        {
            return apply(instance, instance.point(f));
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Kind<F, Fun4<T1, T2, T3, T4, R>> f)
        {
            return instance.ap4(f, t1, t2, t3, t4);
        }

    }

    @NullMarked
    @Desugar
    record Prod5<F extends Ob, T1, T2, T3, T4, T5>(Kind<F, T1> t1,
                                                   Kind<F, T2> t2,
                                                   Kind<F, T3> t3,
                                                   Kind<F, T4> t4,
                                                   Kind<F, T5> t5)
    {

        public <T6> Prod6<F, T1, T2, T3, T4, T5, T6> and(final Kind<F, T6> t6)
        {
            return new Prod6<>(t1, t2, t3, t4, t5, t6);
        }

        public <T6, T7> Prod7<F, T1, T2, T3, T4, T5, T6, T7> and(final Prod2<F, T6, T7> p)
        {
            return new Prod7<>(t1, t2, t3, t4, t5, p.t1, p.t2);
        }

        public <T6, T7, T8> Prod8<F, T1, T2, T3, T4, T5, T6, T7, T8> and(final Prod3<F, T6, T7, T8> p)
        {
            return new Prod8<>(t1, t2, t3, t4, t5, p.t1, p.t2, p.t3);
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Fun5<T1, T2, T3, T4, T5, R> f)
        {
            return apply(instance, instance.point(f));
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Kind<F, Fun5<T1, T2, T3, T4, T5, R>> f)
        {
            return instance.ap5(f, t1, t2, t3, t4, t5);
        }

    }

    @NullMarked
    @Desugar
    record Prod6<F extends Ob, T1, T2, T3, T4, T5, T6>(Kind<F, T1> t1,
                                                       Kind<F, T2> t2,
                                                       Kind<F, T3> t3,
                                                       Kind<F, T4> t4,
                                                       Kind<F, T5> t5,
                                                       Kind<F, T6> t6)
    {

        public <T7> Prod7<F, T1, T2, T3, T4, T5, T6, T7> and(final Kind<F, T7> t7)
        {
            return new Prod7<>(t1, t2, t3, t4, t5, t6, t7);
        }

        public <T7, T8> Prod8<F, T1, T2, T3, T4, T5, T6, T7, T8> and(final Prod2<F, T7, T8> p)
        {
            return new Prod8<>(t1, t2, t3, t4, t5, t6, p.t1, p.t2);
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Fun6<T1, T2, T3, T4, T5, T6, R> f)
        {
            return apply(instance, instance.point(f));
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Kind<F, Fun6<T1, T2, T3, T4, T5, T6, R>> f)
        {
            return instance.ap6(f, t1, t2, t3, t4, t5, t6);
        }

    }

    @NullMarked
    @Desugar
    record Prod7<F extends Ob, T1, T2, T3, T4, T5, T6, T7>(Kind<F, T1> t1,
                                                           Kind<F, T2> t2,
                                                           Kind<F, T3> t3,
                                                           Kind<F, T4> t4,
                                                           Kind<F, T5> t5,
                                                           Kind<F, T6> t6,
                                                           Kind<F, T7> t7)
    {

        public <T8> Prod8<F, T1, T2, T3, T4, T5, T6, T7, T8> and(final Kind<F, T8> t8)
        {
            return new Prod8<>(t1, t2, t3, t4, t5, t6, t7, t8);
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Fun7<T1, T2, T3, T4, T5, T6, T7, R> f)
        {
            return apply(instance, instance.point(f));
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Kind<F, Fun7<T1, T2, T3, T4, T5, T6, T7, R>> f)
        {
            return instance.ap7(f, t1, t2, t3, t4, t5, t6, t7);
        }

    }

    @NullMarked
    @Desugar
    record Prod8<F extends Ob, T1, T2, T3, T4, T5, T6, T7, T8>(Kind<F, T1> t1,
                                                               Kind<F, T2> t2,
                                                               Kind<F, T3> t3,
                                                               Kind<F, T4> t4,
                                                               Kind<F, T5> t5,
                                                               Kind<F, T6> t6,
                                                               Kind<F, T7> t7,
                                                               Kind<F, T8> t8)
    {

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Fun8<T1, T2, T3, T4, T5, T6, T7, T8, R> f)
        {
            return apply(instance, instance.point(f));
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Kind<F, Fun8<T1, T2, T3, T4, T5, T6, T7, T8, R>> f)
        {
            return instance.ap8(f, t1, t2, t3, t4, t5, t6, t7, t8);
        }

    }

    @NullMarked
    @Desugar
    record Prod9<F extends Ob, T1, T2, T3, T4, T5, T6, T7, T8, T9>(Kind<F, T1> t1,
                                                                   Kind<F, T2> t2,
                                                                   Kind<F, T3> t3,
                                                                   Kind<F, T4> t4,
                                                                   Kind<F, T5> t5,
                                                                   Kind<F, T6> t6,
                                                                   Kind<F, T7> t7,
                                                                   Kind<F, T8> t8,
                                                                   Kind<F, T9> t9)
    {

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Fun9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> f)
        {
            return apply(instance, instance.point(f));
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Kind<F, Fun9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R>> f)
        {
            return instance.ap9(f, t1, t2, t3, t4, t5, t6, t7, t8, t9);
        }

    }

    @NullMarked
    @Desugar
    record Prod10<F extends Ob, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(Kind<F, T1> t1,
                                                                         Kind<F, T2> t2,
                                                                         Kind<F, T3> t3,
                                                                         Kind<F, T4> t4,
                                                                         Kind<F, T5> t5,
                                                                         Kind<F, T6> t6,
                                                                         Kind<F, T7> t7,
                                                                         Kind<F, T8> t8,
                                                                         Kind<F, T9> t9,
                                                                         Kind<F, T10> t10)
    {

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Fun10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> f)
        {
            return apply(instance, instance.point(f));
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Kind<F, Fun10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R>> f)
        {
            return instance.ap10(f, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
        }

    }

    @NullMarked
    @Desugar
    record Prod11<F extends Ob, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(Kind<F, T1> t1,
                                                                              Kind<F, T2> t2,
                                                                              Kind<F, T3> t3,
                                                                              Kind<F, T4> t4,
                                                                              Kind<F, T5> t5,
                                                                              Kind<F, T6> t6,
                                                                              Kind<F, T7> t7,
                                                                              Kind<F, T8> t8,
                                                                              Kind<F, T9> t9,
                                                                              Kind<F, T10> t10,
                                                                              Kind<F, T11> t11)
    {

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Fun11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> f)
        {
            return apply(instance, instance.point(f));
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Kind<F, Fun11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R>> f)
        {
            return instance.ap11(f, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
        }

    }

    @NullMarked
    @Desugar
    record Prod12<F extends Ob, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(Kind<F, T1> t1,
                                                                                   Kind<F, T2> t2,
                                                                                   Kind<F, T3> t3,
                                                                                   Kind<F, T4> t4,
                                                                                   Kind<F, T5> t5,
                                                                                   Kind<F, T6> t6,
                                                                                   Kind<F, T7> t7,
                                                                                   Kind<F, T8> t8,
                                                                                   Kind<F, T9> t9,
                                                                                   Kind<F, T10> t10,
                                                                                   Kind<F, T11> t11,
                                                                                   Kind<F, T12> t12)
    {

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Fun12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> f)
        {
            return apply(instance, instance.point(f));
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Kind<F, Fun12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R>> f)
        {
            return instance.ap12(f, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
        }

    }
    
    @NullMarked
    @Desugar
    record Prod13<F extends Ob, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(Kind<F, T1> t1,
                                                                                        Kind<F, T2> t2,
                                                                                        Kind<F, T3> t3,
                                                                                        Kind<F, T4> t4,
                                                                                        Kind<F, T5> t5,
                                                                                        Kind<F, T6> t6,
                                                                                        Kind<F, T7> t7,
                                                                                        Kind<F, T8> t8,
                                                                                        Kind<F, T9> t9,
                                                                                        Kind<F, T10> t10,
                                                                                        Kind<F, T11> t11,
                                                                                        Kind<F, T12> t12,
                                                                                        Kind<F, T13> t13)
    {
        
        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Fun13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> f)
        {
            return apply(instance, instance.point(f));
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Kind<F, Fun13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R>> f)
        {
            return instance.ap13(f, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
        }
        
    }

    @NullMarked
    @Desugar
    record Prod14<F extends Ob, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(Kind<F, T1> t1,
                                                                                             Kind<F, T2> t2,
                                                                                             Kind<F, T3> t3,
                                                                                             Kind<F, T4> t4,
                                                                                             Kind<F, T5> t5,
                                                                                             Kind<F, T6> t6,
                                                                                             Kind<F, T7> t7,
                                                                                             Kind<F, T8> t8,
                                                                                             Kind<F, T9> t9,
                                                                                             Kind<F, T10> t10,
                                                                                             Kind<F, T11> t11,
                                                                                             Kind<F, T12> t12,
                                                                                             Kind<F, T13> t13,
                                                                                             Kind<F, T14> t14)
    {

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Fun14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> f)
        {
            return apply(instance, instance.point(f));
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Kind<F, Fun14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R>> f)
        {
            return instance.ap14(f, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
        }

    }

    @NullMarked
    @Desugar
    record Prod15<F extends Ob, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(Kind<F, T1> t1,
                                                                                                  Kind<F, T2> t2,
                                                                                                  Kind<F, T3> t3,
                                                                                                  Kind<F, T4> t4,
                                                                                                  Kind<F, T5> t5,
                                                                                                  Kind<F, T6> t6,
                                                                                                  Kind<F, T7> t7,
                                                                                                  Kind<F, T8> t8,
                                                                                                  Kind<F, T9> t9,
                                                                                                  Kind<F, T10> t10,
                                                                                                  Kind<F, T11> t11,
                                                                                                  Kind<F, T12> t12,
                                                                                                  Kind<F, T13> t13,
                                                                                                  Kind<F, T14> t14,
                                                                                                  Kind<F, T15> t15)
    {

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Fun15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> f)
        {
            return apply(instance, instance.point(f));
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Kind<F, Fun15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R>> f)
        {
            return instance.ap15(f, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
        }

    }

    @NullMarked
    @Desugar
    record Prod16<F extends Ob, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(Kind<F, T1> t1,
                                                                                                       Kind<F, T2> t2,
                                                                                                       Kind<F, T3> t3,
                                                                                                       Kind<F, T4> t4,
                                                                                                       Kind<F, T5> t5,
                                                                                                       Kind<F, T6> t6,
                                                                                                       Kind<F, T7> t7,
                                                                                                       Kind<F, T8> t8,
                                                                                                       Kind<F, T9> t9,
                                                                                                       Kind<F, T10> t10,
                                                                                                       Kind<F, T11> t11,
                                                                                                       Kind<F, T12> t12,
                                                                                                       Kind<F, T13> t13,
                                                                                                       Kind<F, T14> t14,
                                                                                                       Kind<F, T15> t15,
                                                                                                       Kind<F, T16> t16)
    {

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Fun16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> f)
        {
            return apply(instance, instance.point(f));
        }

        public <R> Kind<F, R> apply(final Applicative<F, ?> instance, final Kind<F, Fun16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R>> f)
        {
            return instance.ap16(f, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
        }

    }

}
