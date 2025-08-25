package magicsweepy.iota.kind.product;

import com.github.bsideup.jabel.Desugar;
import magicsweepy.iota.kind.Applicative;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.kind.function.Fun4;
import org.jspecify.annotations.NullMarked;

@NullMarked
@Desugar
public record Prod4<F extends Ob, T1, T2, T3, T4>(Kind<F, T1> t1,
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
            return new Prod6<>(t1, t2, t3, t4, p.t1(), p.t2());
        }

        public <T5, T6, T7> Prod7<F, T1, T2, T3, T4, T5, T6, T7> and(final Prod3<F, T5, T6, T7> p)
        {
            return new Prod7<>(t1, t2, t3, t4, p.t1(), p.t2(), p.t3());
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