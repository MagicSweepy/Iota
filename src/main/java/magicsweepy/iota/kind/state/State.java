package magicsweepy.iota.kind.state;

import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.kind.Unit;
import magicsweepy.iota.kind.tuple.Pair;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

@NullMarked
@FunctionalInterface
public interface State<S, A> extends Kind<State.Mu<S>, A>
{

    final class Mu<S> implements Ob {}

    Pair<S, A> run(S initialState);

    static <S, A> State<S, A> of(final Function<S, Pair<S, A>> runner)
    {
        return runner::apply;
    }

    static <S, A> State<S, A> of(@Nullable A value)
    {
        return of(s -> Pair.of(s, value));
    }

    static <S> State<S, S> get()
    {
        return of(s -> Pair.of(s, s));
    }

    static <S> State<S, Unit> set(S newState)
    {
        return of(s -> Pair.of(newState, Unit.INSTANCE));
    }

    static <S> State<S, Unit> set(Function<S, S> f)
    {
        return of(s -> Pair.of(f.apply(s), Unit.INSTANCE));
    }

    static <S, A> State<S, A> update(Function<S, A> f)
    {
        return of(s -> Pair.of(s, f.apply(s)));
    }

    default <B> State<S, B> map(final Function<A, B> f)
    {
        return of(s -> {
            final Pair<S, A> result = run(s);
            return Pair.of(result.first(), f.apply(result.second()));
        });
    }

    default <B> State<S, B> flatMap(final Function<? super A, ? extends State<S, ? extends B>> f)
    {
        return of(initialState -> {
            final Pair<S, A> pair = run(initialState);
            State<S, ? extends B> state = f.apply(pair.second());
            Pair<S, ? extends B> result = state.run(pair.first());
            return Pair.of(result.first(), result.second());
        });
    }

}
