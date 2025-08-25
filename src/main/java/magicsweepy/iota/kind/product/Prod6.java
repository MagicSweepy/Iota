package magicsweepy.iota.kind.product;

import com.github.bsideup.jabel.Desugar;
import magicsweepy.iota.kind.Applicative;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.kind.function.Fun6;
import org.jspecify.annotations.NullMarked;

@NullMarked
@Desugar
public record Prod6<F extends Ob, T1, T2, T3, T4, T5, T6>(Kind<F, T1> t1,
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
        return new Prod8<>(t1, t2, t3, t4, t5, t6, p.t1(), p.t2());
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