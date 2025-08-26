package magicsweepy.iota.kind.optional;

import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Ob;
import org.jspecify.annotations.NonNull;

/**
 * @see java.util.Optional
 */
public interface OptionalKind<T> extends Kind<OptionalKind.@NonNull Mu, T>
{

    final class Mu implements Ob {}

}
