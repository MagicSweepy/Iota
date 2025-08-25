package magicsweepy.iota.kind.product;

import com.github.bsideup.jabel.Desugar;
import magicsweepy.iota.kind.Applicative;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.kind.function.Fun10;
import org.jspecify.annotations.NullMarked;

@NullMarked
@Desugar
public record Prod10<F extends Ob, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(Kind<F, T1> t1,
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