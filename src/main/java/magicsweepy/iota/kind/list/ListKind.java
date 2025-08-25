package magicsweepy.iota.kind.list;

import com.github.bsideup.jabel.Desugar;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * @see java.util.List
 */
@SuppressWarnings("NullableProblems")
public interface ListKind<E> extends Kind<ListKind.Mu, E>
{

    final class Mu implements Ob {}

    @NonNull
    List<E> unbox();

    @NonNull
    static <E> ListKind<E> unbox(@NonNull Kind<ListKind.Mu, E> kind)
    {
        return Unchecks.cast(kind);
    }

    @NonNull
    static <E> ListKind<E> of(@NonNull List<E> list)
    {
        return new ListView<>(list);
    }

    @Desugar
    record ListView<E>(@NonNull List<E> list) implements ListKind<E>
    {

        @NonNull
        @Override
        public List<E> unbox()
        {
            return list;
        }

    }

}
