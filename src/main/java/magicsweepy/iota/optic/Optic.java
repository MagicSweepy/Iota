package magicsweepy.iota.optic;

import com.github.bsideup.jabel.Desugar;
import com.google.common.reflect.TypeToken;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Kind2;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.kind.Ob2;
import magicsweepy.iota.util.Unchecks;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("UnstableApiUsage")
@NullMarked
public interface Optic<Proof extends Ob, S, T, A, B>
{

    <P extends Ob2> Function<Kind2<P, A, B>, Kind2<P, S, T>> eval(final Kind<? extends Proof, P> proof);

    default <Q extends Ob> Optional<Optic<? super Q, S, T, A, B>> upCast(final Set<TypeToken<? extends Ob>> proofBounds,
                                                                         final TypeToken<Q> proof)
    {
        if (proofBounds.stream().allMatch(bound -> bound.isSupertypeOf(proof)))
            return Optional.of(Unchecks.cast(this));
        return Optional.empty();
    }

    @Desugar
    record Composition<Q extends Ob, S, T, A, B>(List<? extends Optic<? super Q, ?, ?, ?, ?>> optics) implements Optic<Q, S, T, A, B>
    {

        @Override
        public <P extends Ob2> Function<Kind2<P, A, B>, Kind2<P, S, T>> eval(final Kind<? extends Q, P> proof)
        {
            final List<Function<? extends Kind2<P, ?, ?>, ? extends Kind2<P, ?, ?>>> ff = new ArrayList<>(optics.size());
            for (int i = optics.size() - 1; i >= 0; i--)
                ff.add(optics.get(i).eval(proof));

            return input -> {
                Kind2<P, ?, ?> result = input;
                for (final Function<? extends Kind2<P, ?, ?>, ? extends Kind2<P, ?, ?>> function : ff)
                    result = function.apply(Unchecks.cast(result));
                return Unchecks.cast(result);
            };
        }


        @NotNull
        @Override
        public String toString()
        {
            return "(" + optics.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(" ◦ ")) + ")";
        }

    }

}
