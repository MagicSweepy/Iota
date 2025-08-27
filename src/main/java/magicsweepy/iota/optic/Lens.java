package magicsweepy.iota.optic;

import com.github.bsideup.jabel.Desugar;
import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.kind.tuple.Pair;
import magicsweepy.iota.optic.profunctor.Cartesian;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

@NullMarked
public interface Lens<S, T, A, B> extends Kind2<Lens.Mu<A, B>, S, T>, Optic<Cartesian.Mu, S, T, A, B>
{

    final class Mu<A, B> implements Ob2 {}

    final class Mu2<S, T> implements Ob2 {}

    @Desugar
    record Holder<S, T, A, B>(Lens<S, T, A, B> lens) implements Kind2<Lens.Mu2<S, T>, B, A> {}

    static <S, T, A, B> Kind2<Lens.Mu2<S, T>, B, A> box(final Lens<S, T, A, B> lens)
    {
        return new Holder<>(lens);
    }

    static <S, T, A, B> Lens<S, T, A, B> unbox(final Kind2<Lens.Mu<A, B>, S, T> box)
    {
        return (Lens<S, T, A, B>) box;
    }

    static <S, T, A, B> Lens<S, T, A, B> unbox2(final Kind2<Lens.Mu2<S, T>, B, A> box)
    {
        return ((Holder<S, T, A, B>) box).lens;
    }

    A view(final S s);

    T update(final B b, final S s);

    @Override
    default <P extends Ob2> Functoid<Kind2<P, A, B>, Kind2<P, S, T>> eval(final Kind<? extends Cartesian.Mu, P> box)
    {
        final Cartesian<P, ? extends Cartesian.Mu> proof = Cartesian.unbox(box);
        return a -> proof.dimap(proof.<A, B, S>first(a),
                s -> Pair.<A, S>of(view(s), s),
                pair -> update(pair.first(), pair.second()));
    }

    final class Instance<A2, B2> implements Cartesian<Mu<A2, B2>, Cartesian.Mu>
    {

        @Override
        public <A, B, C, D> Functoid<Kind2<Lens.Mu<A2, B2>, A, B>, Kind2<Lens.Mu<A2, B2>, C, D>> dimap(final Function<C, A> g,
                                                                                                         final Function<B, D> h)
        {
            return l -> Optics.lens(c -> Lens.unbox(l).view(g.apply(c)),
                    (b2, c) -> h.apply(Lens.unbox(l).update(b2, g.apply(c))));
        }

        @Override
        public <A, B, C> Kind2<Lens.Mu<A2, B2>, Pair<A, C>, Pair<B, C>> first(final Kind2<Lens.Mu<A2, B2>, A, B> input)
        {
            return Optics.lens(pair -> Lens.unbox(input).view(pair.first()),
                    (b2, pair) -> Pair.of(Lens.unbox(input).update(b2, pair.first()), pair.second()));
        }

        @Override
        public <A, B, C> Kind2<Lens.Mu<A2, B2>, Pair<C, A>, Pair<C, B>> second(final Kind2<Lens.Mu<A2, B2>, A, B> input)
        {
            return Optics.lens(pair -> Lens.unbox(input).view(pair.second()),
                    (b2, pair) -> Pair.of(pair.first(), Lens.unbox(input).update(b2, pair.second())));
        }

    }

}
