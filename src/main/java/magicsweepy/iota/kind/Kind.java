package magicsweepy.iota.kind;

import org.jspecify.annotations.NullMarked;

/**
 * Basic Type which based on {@link Ob}s to construct a operation {@code F} and value {@code A}.
 * <p>
 * For any contained value type {@code A}, the {@link Kind} is the basic type based on any {@link Ob}, it means it being
 * upped from category level (from object to type). It can be represented with {@code A -> F<A>} or {@code F(A)}. This
 * is the type correspondence of category sense.
 * <p>
 * For example, when we construct a regular list {@code List<String>}, it can be represented with category level operand
 * as {@code Kind<ListKind.Mu, String}, or {@code String -> Kind<ListKind.Mu>(String)}.
 *
 * @param <F> The generic type of the type constructor, must be a functional object, i.e. {@link Ob}.
 * @param <A> The generic type of the contained value type.
 */
@NullMarked
public interface Kind<F extends Ob, A> {}
