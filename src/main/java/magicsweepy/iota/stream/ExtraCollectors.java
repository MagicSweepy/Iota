package magicsweepy.iota.stream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@UtilityClass
public class ExtraCollectors
{

    /**
     * Returns a {@link Collector} that accumulates input elements into {@code ArrayList}.
     */
    public <T> Collector<T, ?, List<T>> toMutableList()
    {
        return Collectors.toCollection(Lists::newArrayList);
    }

    /**
     * Returns a {@link Collector} that accumulates input elements into {@link ImmutableList}.
     */
    @SuppressWarnings("UnstableApiUsage")
    public <T> Collector<T, ?, ImmutableList<T>> toImmutableList()
    {
        return ImmutableList.toImmutableList();
    }

    /**
     * Returns a {@link Map.Entry} that accumulates input keys and values into {@link Map}.
     */
    public <K, V> Collector<Map.Entry<? extends K, ? extends V>, ?, Map<K, V>> toMap()
    {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
    }

}
