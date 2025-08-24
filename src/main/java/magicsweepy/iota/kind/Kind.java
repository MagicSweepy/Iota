package magicsweepy.iota.kind;

import org.jspecify.annotations.NullMarked;

/**
 * Basic Type for constructor {@code F} and value type {@code A}.
 * <p>
 * For any contained value type {@code A}, the {@link Kind} can be represented with <tt>A -> F(A)</tt> or <tt>F(A)</tt>,
 * it means an argument {@code A} and its constructor {@code F}.
 * <p>
 * For example, regular list {@code List<String>} can be represented as {@code Kind<ListKind.Mu, String>}, {@code Kind}
 * operand is {@code String -> Kind<ListKind.Mu>(String)}.
 *
 * @param <F> The generic type of the type constructor.
 * @param <A> The generic type of contained value.
 */
@NullMarked
public interface Kind<F, A> {}
