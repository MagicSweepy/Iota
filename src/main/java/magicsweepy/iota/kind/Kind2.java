package magicsweepy.iota.kind;

import org.jspecify.annotations.NullMarked;

/**
 * Binary Type which based on {@link Ob2}s to constructor a operation {@code F} and two values {@code A} and {@code B}.
 * <p>
 * For any contained value types {@code A}, {@code B}, the {@link Kind2} is the binary type based on any {@link Ob2}, it
 * means it being upped from category level (from object to type). It can be represented with {@code (A, B) -> F<A, B>}
 * or {@code F(A, B)}. This is the type correspondence of category sense.
 * <p>
 * For example, when we construct a regular map {@code Map<String, Integer> }, it can be represented with category level
 * operand as {@code Kind2<MapKind.Mu, String, Integer> -> Kind2<MapKind.Mu>(String, Integer)}.
 *
 * @param <F> The generic type of the type constructor, must be a functional object, i.e. {@link Ob2}.
 * @param <A> The generic type of the contained first value type.
 * @param <B> The generic type of the contained second value type.
 */
@NullMarked
public interface Kind2<F extends Ob2, A, B> {}
