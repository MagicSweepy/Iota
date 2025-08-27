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

/**
 * An {@code Optic} is a generalization of various types of optics (like lenses, prisms, etc.) that can be used to focus
 * on and manipulate parts of data structures in a composable way.
 * <p>
 * The {@code Optic} is parameterized by:
 * <ul>
 *     <li>{@code Proof}: A type-level representation of the kind of optic (e.g., lens, prism) being used.</li>
 *     <li>{@code S}: The source type from which we are focusing.</li>
 *     <li>{@code T}: The modified source type after applying an update.</li>
 *     <li>{@code A}: The target type that we are focusing on.</li>
 *     <li>{@code B}: The modified target type after applying an update.</li>
 * </ul>
 * <p>
 * The primary method in this interface is {@link #eval(Kind)}, which takes a proof of the optic's kind and returns a
 * function that can transform a value of type {@code Kind2<P, A, B>} into a value of type {@code Kind2<P, S, T>}, where
 * {@code P} is some profunctor.
 * <p>
 * This design allows for the composition of different optics, enabling complex data manipulations to be built up from
 * simpler components.
 *
 * @param <Proof> A type-level representation of the kind of optic.
 * @param <S>     The source type.
 * @param <T>     The modified source type.
 * @param <A>     The target type.
 * @param <B>     The modified target type.
 */
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
