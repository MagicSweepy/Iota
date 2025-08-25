package magicsweepy.iota.kind.product;

import com.github.bsideup.jabel.Desugar;
import magicsweepy.iota.kind.Applicative;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Ob;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

@NullMarked
@Desugar
public record Prod1<F extends Ob, T1>(Kind<F, T1> t1)
{

    public <T2> Prod2<F, T1, T2> and(final Kind<F, T2> t2)
    {
        return new Prod2<>(t1, t2);
    }

    public <T2, T3> Prod3<F, T1, T2, T3> and(final Prod2<F, T2, T3> p)
    {
        return new Prod3<>(t1, p.t1(), p.t2());
    }

    public <T2, T3, T4> Prod4<F, T1, T2, T3, T4> and(final Prod3<F, T2, T3, T4> p)
    {
        return new Prod4<>(t1, p.t1(), p.t2(), p.t3());
    }

    public <T2, T3, T4, T5> Prod5<F, T1, T2, T3, T4, T5> and(final Prod4<F, T2, T3, T4, T5> p)
    {
        return new Prod5<>(t1, p.t1(), p.t2(), p.t3(), p.t4());
    }

    public <T2, T3, T4, T5, T6> Prod6<F, T1, T2, T3, T4, T5, T6> and(final Prod5<F, T2, T3, T4, T5, T6> p)
    {
        return new Prod6<>(t1, p.t1(), p.t2(), p.t3(), p.t4(), p.t5());
    }

    public <T2, T3, T4, T5, T6, T7> Prod7<F, T1, T2, T3, T4, T5, T6, T7> and(final Prod6<F, T2, T3, T4, T5, T6, T7> p)
    {
        return new Prod7<>(t1, p.t1(), p.t2(), p.t3(), p.t4(), p.t5(), p.t6());
    }

    public <T2, T3, T4, T5, T6, T7, T8> Prod8<F, T1, T2, T3, T4, T5, T6, T7, T8> and(final Prod7<F, T2, T3, T4, T5, T6, T7, T8> p)
    {
        return new Prod8<>(t1, p.t1(), p.t2(), p.t3(), p.t4(), p.t5(), p.t6(), p.t7());
    }

    public <R> Kind<F, R> apply(final Applicative<F, ?> ap, final Function<T1, R> f)
    {
        return apply(ap, ap.point(f));
    }

    public <R> Kind<F, R> apply(final Applicative<F, ?> ap, final Kind<F, Function<T1, R>> f)
    {
        return ap.ap(f, t1);
    }

}