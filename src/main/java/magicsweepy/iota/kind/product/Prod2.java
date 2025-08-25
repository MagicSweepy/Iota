package magicsweepy.iota.kind.product;

import com.github.bsideup.jabel.Desugar;
import magicsweepy.iota.kind.Applicative;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Ob;
import org.jspecify.annotations.NullMarked;

import java.util.function.BiFunction;

@NullMarked
@Desugar
public record Prod2<F extends Ob, T1, T2>(Kind<F, T1> t1,
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
        return new Prod5<>(t1, t2, p.t1(), p.t2(), p.t3());
    }

    public <T3, T4, T5, T6> Prod6<F, T1, T2, T3, T4, T5, T6> and(final Prod4<F, T3, T4, T5, T6> p)
    {
        return new Prod6<>(t1, t2, p.t1(), p.t2(), p.t3(), p.t4());
    }

    public <T3, T4, T5, T6, T7> Prod7<F, T1, T2, T3, T4, T5, T6, T7> and(final Prod5<F, T3, T4, T5, T6, T7> p)
    {
        return new Prod7<>(t1, t2, p.t1(), p.t2(), p.t3(), p.t4(), p.t5());
    }

    public <T3, T4, T5, T6, T7, T8> Prod8<F, T1, T2, T3, T4, T5, T6, T7, T8> and(final Prod6<F, T3, T4, T5, T6, T7, T8> p)
    {
        return new Prod8<>(t1, t2, p.t1(), p.t2(), p.t3(), p.t4(), p.t5(), p.t6());
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