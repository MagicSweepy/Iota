package magicsweepy.iota.kind;

import magicsweepy.iota.kind.list.ListOps;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

import java.util.AbstractList;
import java.util.List;

@NullMarked
public interface Monoid<T> extends SemiGroup<T>
{

    T empty();

    static <T> Monoid<List<T>> listMonoid()
    {
        return new Monoid<>()
        {

            @Override
            public List<T> empty()
            {
                return ListOps.of();
            }

            @Override
            public List<T> combine(List<T> first, List<T> second)
            {
                return new ListMonoidView<>(first, second);
            }
        };
    }

    @Internal
    class ListMonoidView<T> extends AbstractList<T>
    {

        private final List<T> first;
        private final List<T> second;

        ListMonoidView(final List<T> first,
                       final List<T> second)
        {
            this.first = ListOps.of(first);
            this.second = ListOps.of(second);
        }

        @Override
        public T get(int index)
        {
            return (index < first.size()) ? first.get(index) : second.get(index - first.size());
        }

        @Override
        public int size()
        {
            return first.size() + second.size();
        }

    }

}
