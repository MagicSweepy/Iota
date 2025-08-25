package magicsweepy.iota.kind.product;

import com.github.bsideup.jabel.Desugar;
import magicsweepy.iota.kind.Applicative;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.kind.function.Fun5;
import org.jspecify.annotations.NullMarked;

@NullMarked
@Desugar
public record Prod5<F extends Ob, T1, T2, T3, T4, T5>(Kind<F, T1> t1,
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
        return new Prod7<>(t1, t2, t3, t4, t5, p.t1(), p.t2());
    }

    public <T6, T7, T8> Prod8<F, T1, T2, T3, T4, T5, T6, T7, T8> and(final Prod3<F, T6, T7, T8> p)
    {
        return new Prod8<>(t1, t2, t3, t4, t5, p.t1(), p.t2(), p.t3());
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