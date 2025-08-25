package magicsweepy.iota.kind.list;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class ListOps
{

    /**
     * Returns an unmodifiable {@code List} containing zero elements.
     *
     * @return    An empty {@code List}.
     *
     * @param <E> The element type of the {@code List}.
     */
    @Unmodifiable
    public <E> List<E> of()
    {
        return Collections.emptyList();
    }

    /**
     * Returns an unmodifiable {@code List} containing an arbitrary number of elements.
     * <p>
     * This method returns immutable {@code List}, if you need mutable {@code List},
     * please used {@link ListOps#ofMutable(Object[])}.
     *
     * @param elements The elements to be contained in the {@code List}.
     * @return         A {@code List} containing the specified {@code elements}.
     *
     * @param <E>      The elements type of the {@code List}.
     */
    @Unmodifiable
    @SafeVarargs
    public <E> List<E> of(E... elements)
    {
        return ImmutableList.copyOf(elements);
    }

    /**
     * Returns an unmodifiable {@code List} from an existed {@code List}.
     *
     * @param list The existed list contained specific elements.
     * @return     A new {@code List} containing original elements but be immutable.
     *
     * @param <E> The elements type of the {@code List} and existed {@code List}.
     */
    @Unmodifiable
    public <E> List<E> of(List<E> list)
    {
        return ImmutableList.copyOf(list);
    }

    /**
     * Returns a modifiable {@code List} containing an arbitrary number of elements.
     * <p>
     * This method returns mutable {@code List}, if you need immutable {@code List},
     * please used {@link ListOps#of(Object[])}.
     *
     * @param elements The elements to be contained in the {@code List}.
     * @return         A {@code List} containing the specified {@code elements}.
     *
     * @param <E>      The elements type of the {@code List}.
     */
    @SafeVarargs
    public <E> List<E> ofMutable(E... elements)
    {
        return Lists.newArrayList(elements);
    }

    /**
     * Returns an unmodifiable {@code List} containing the elements of the given {@code Collection}.
     *
     * @param collection The {@code Collection} which contained all specified elements.
     * @return           A {@code List} containing the specified elements.
     *
     * @param <E>        The elements type of the {@code List}.
     */
    @Unmodifiable
    public <E> List<E> copyOf(Collection<? extends E> collection)
    {
        return ImmutableList.copyOf(collection);
    }

}
