package magicsweepy.iota.stream;

import lombok.experimental.UtilityClass;
import magicsweepy.iota.optic.Affine;
import magicsweepy.iota.optic.Getter;
import magicsweepy.iota.optic.Lens;
import magicsweepy.iota.optic.Optic;
import magicsweepy.iota.optic.Prism;
import magicsweepy.iota.util.Checks;
import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NonNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@UtilityClass
public class StreamOps
{

    /**
     * Returns a stream consisting of the results of applying the given {@link Gatherer}
     * of the upstream {@code source} to the elements of this stream.
     * <p>
     * This is a stateful intermediate operation that is an extension point.
     * <p>
     * Gatherers are highly flexible and can describe a vast array of possibly stateful
     * operations, with support for short-circuiting, and parallelization.
     * <p>
     * When executed in parallel, multiple intermediate results may be instantiated,
     * populated, and merged to maintain isolation of mutable data structures. Therefore,
     * even when executed in parallel with non-thread-safe data structures (such as
     * a {@code ArrayList}), no additional synchronization is needed for a parallel
     * reduction.
     *
     * @param source   Upstream for gatherer pipeline.
     * @param gatherer A gatherer.
     * @return         The new stream.
     *
     * @param <T> The type of input elements for the gatherer.
     * @param <A> The type of the state of the returned initializer.
     * @param <R> The type of results for the gatherer.
     */
    public <T, A, R> Stream<R> gatherer(@NonNull Stream<T> source,
                                        @NonNull Gatherer<? super T, A, R> gatherer)
    {
        Checks.notnull(source, gatherer);

        Supplier<A> initializer = gatherer.initializer();
        Gatherer.Integrator<A, ? super T, R> integrator = gatherer.integrator();
        BinaryOperator<A> combiner = gatherer.combiner();
        BiConsumer<A, Gatherer.Downstream<? super R>> finisher = gatherer.finisher();

        Spliterator<T> srcSpliterator = source.spliterator();
        int characteristics = srcSpliterator.characteristics() &
                (Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED);

        return StreamSupport.stream(new GathererSpliterator<>(initializer, integrator,
                        combiner, finisher, srcSpliterator, characteristics, source.isParallel()),
                source.isParallel()).onClose(source::close);
    }

    /**
     * Returns a {@code List} of keys sorted by their corresponding values in the provided map.
     *
     * @param map        The map containing key-value pairs.
     * @param comparator The comparator to determine the order of the values.
     * @return           The {@code List} of keys sorted based on their corresponding values.
     *
     * @param <K>        The type of keys in the map.
     * @param <V>        The type of values in the map.
     */
    public <K, V> List<K> sortedByKey(@NonNull Map<K, V> map,
                                      @NonNull Comparator<? super V> comparator)
    {
        Checks.notnull(map, comparator);
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(comparator))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Returns a {@code List} of keys sorted by their corresponding values in the provided map.
     * <p>
     * The values are compared using their natural ordering, which requires that the value type
     * implements the {@link Comparable} interface.
     *
     * @param map          The map containing key-value pairs.
     * @param keyExtractor The function to extract the comparable key from the value.
     * @return             The {@code List} of keys sorted based on their corresponding values.
     *
     * @param <K>          The type of keys in the map.
     * @param <V>          The type of values in the map.
     * @param <U>          The type of the comparable key extracted from the value.
     */
    public <K, V, U extends Comparable<? super U>> List<K> sortedByKey(@NonNull Map<K, V> map,
                                                                       @NonNull Function<? super V, ? extends U> keyExtractor)
    {
        Checks.notnull(map, keyExtractor);
        return sortedByKey(map, Comparator.comparing(keyExtractor));
    }

    /**
     * Returns a {@code List} of values sorted by their corresponding keys in the provided map.
     *
     * @param map        The map containing key-value pairs.
     * @param comparator The comparator to determine the order of the keys.
     * @return           The {@code List} of values sorted based on their corresponding keys.
     *
     * @param <K>        The type of keys in the map.
     * @param <V>        The type of values in the map.
     */
    public <K, V> List<V> sortedByValue(@NonNull Map<K, V> map,
                                        @NonNull Comparator<? super K> comparator)
    {
        Checks.notnull(map, comparator);
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(comparator))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    /**
     * Maps all elements in upstream pipeline of {@link Stream}.
     * <p>
     * This operation will map all elements in the stream using the provided {@link Getter}.
     *
     * @param stream The upstream pipeline of {@link Stream}.
     * @param getter The {@link Getter} to access elements.
     * @return       The new stream with mapped elements.
     *
     * @param <S>    The type of elements in the stream.
     * @param <A>    The type of the accessed element.
     */
    public <S, A> Stream<A> map(@NonNull Stream<S> stream,
                                @NonNull Getter<S, ?, A, ?> getter)
    {
        Checks.notnull(stream, getter);
        return stream.map(getter::get);
    }

    /**
     * Updated all elements in upstream pipeline of {@link Stream}.
     * <p>
     * This operation will update all elements in the stream using the provided {@link Lens} and updater function.
     *
     * @param stream  The upstream pipeline of {@link Stream}.
     * @param lens    The {@link Lens} to access and update elements.
     * @param updater The function to update the element.
     * @return        The new stream with updated elements.
     *
     * @param <S>     The type of elements in the stream.
     * @param <A>     The type of the accessed element.
     * @param <B>     The type of the updated element.
     */
    public <S, A, B> Stream<S> updateEach(@NonNull Stream<S> stream,
                                          @NonNull Lens<S, S, A, B> lens,
                                          @NonNull Function<A, B> updater)
    {
        Checks.notnull(stream, lens, updater);
        return stream.map(s -> lens.update(updater.apply(lens.view(s)), s));
    }

    /**
     * Updated matching elements in upstream pipeline of {@link Stream}.
     * <p>
     * This operation will only update elements which is satisfied by the {@link Prism}'s match function. Non-matching
     * elements will be passed through unchanged.
     *
     * @param stream  The upstream pipeline of {@link Stream}.
     * @param prism   The {@link Prism} to match and update elements.
     * @param updater The function to update the matched element.
     * @return        The new stream with updated elements.
     *
     * @param <S>     The type of elements in the stream.
     * @param <A>     The type of the matched element.
     * @param <B>     The type of the updated element.
     */
    public <S, A, B> Stream<S> updateMatch(@NonNull Stream<S> stream,
                                           @NonNull Prism<S, S, A, B> prism,
                                           @NonNull Function<A, B> updater)
    {
        Checks.notnull(stream, prism, updater);
        return stream.map(s -> prism.match(s)
                .map(left -> left, right -> prism.build(updater.apply(right))));
    }

    /**
     * Updated matching elements in upstream pipeline of {@link Stream}.
     * <p>
     * This operation will only update elements which is satisfied by the {@link Affine}'s preview function. Non-matching
     * elements will be passed through unchanged.
     * <p>
     * For {@link Prism} optic, please see: {@link #updateMatch(Stream, Prism, Function)}.
     *
     * @param stream  The upstream pipeline of {@link Stream}.
     * @param affine  The {@link Affine} to match and update elements.
     * @param updater The function to update the matched element.
     * @return        The new stream with updated elements.
     *
     * @param <S>     The type of elements in the stream.
     * @param <A>     The type of the matched element.
     * @param <B>     The type of the updated element.
     */
    public <S, A, B> Stream<S> updateMatch(@NonNull Stream<S> stream,
                                           @NonNull Affine<S, S, A, B> affine,
                                           @NonNull Function<A, B> updater)
    {
        Checks.notnull(stream, affine, updater);
        return stream.map(s -> affine.preview(s)
                .map(left -> left, right -> affine.set(updater.apply(right), s)));
    }

    /**
     * Grouped elements in upstream pipeline of {@link Stream} by the key extracted using the provided {@link Lens}.
     *
     * @param stream The upstream pipeline of {@link Stream}.
     * @param lens   The {@link Lens} to extract the key for grouping.
     * @return       The {@code Map} where keys are extracted using the lens and values are lists of elements
     *               corresponding to each key.
     *
     * @param <S>    The type of elements in the stream.
     * @param <A>    The type of the key used for grouping.
     */
    public <S, A> Map<A, List<S>> groupBy(@NonNull Stream<S> stream,
                                          @NonNull Lens<S, ?, A, ?> lens)
    {
        Checks.notnull(stream, lens);
        return stream.collect(Collectors.groupingBy(lens::view));
    }

    /**
     * Partitions elements in upstream pipeline of {@link Stream} into two groups based on the provided {@link Predicate}
     * applied to the key extracted using the provided {@link Lens}.
     *
     * @param stream    The upstream pipeline of {@link Stream}.
     * @param lens      The {@link Lens} to extract the key for partitioning.
     * @param predicate The {@link Predicate} to test the extracted key.
     * @return          The {@code Map} with two entries: {@code true} and {@code false}. Each entry contains a list of
     *                  elements for which the predicate evaluated to true or false, respectively.
     *
     * @param <S>       The type of elements in the stream.
     * @param <A>       The type of the key used for partitioning.
     */
    public <S, A> Map<Boolean, List<S>> partitionBy(@NonNull Stream<S> stream,
                                                    @NonNull Lens<S, ?, A, ?> lens,
                                                    @NonNull Predicate<A> predicate)
    {
        Checks.notnull(stream, lens, predicate);
        return stream.collect(Collectors.partitioningBy(s -> predicate.test(lens.view(s))));
    }

    /**
     * Finds the minimum value in upstream pipeline of {@link Stream} based on the provided {@link Lens}.
     * <p>
     * The values are compared using their natural ordering, which requires that the value type
     * implements the {@link Comparable} interface.
     *
     * @param stream The upstream pipeline of {@link Stream}.
     * @param lens   The {@link Lens} to access elements for comparison.
     * @return       An {@link Optional} describing the minimum value, or an empty {@code Optional}
     *               if the stream is empty.
     *
     * @param <S>    The type of elements in the stream.
     * @param <A>    The type of the accessed element, which must implement {@link Comparable}.
     */
    public <S, A extends Comparable<A>> Optional<A> maxBy(@NonNull Stream<S> stream,
                                                          @NonNull Lens<S, ?, A, ?> lens)
    {
        Checks.notnull(stream, lens);
        return stream.map(lens::view).max(Comparator.naturalOrder());
    }

    /**
     * Finds the minimum value in upstream pipeline of {@link Stream} based on the provided {@link Lens}.
     * <p>
     * The values are compared using their natural ordering, which requires that the value type
     * implements the {@link Comparable} interface.
     *
     * @param stream The upstream pipeline of {@link Stream}.
     * @param lens   The {@link Lens} to access elements for comparison.
     * @return       An {@link Optional} describing the minimum value, or an empty {@code Optional}
     *               if the stream is empty.
     *
     * @param <S>    The type of elements in the stream.
     * @param <A>    The type of the accessed element, which must implement {@link Comparable}.
     */
    public <S, A extends Comparable<A>> Optional<A> minBy(@NonNull Stream<S> stream,
                                                          @NonNull Lens<S, ?, A, ?> lens)
    {
        Checks.notnull(stream, lens);
        return stream.map(lens::view).min(Comparator.naturalOrder());
    }

    /**
     * Computes the sum of integer values in upstream pipeline of {@link Stream} based on the provided {@link Lens}.
     *
     * @param stream The upstream pipeline of {@link Stream}.
     * @param lens   The {@link Lens} to access integer elements for summation.
     * @return       An {@link OptionalInt} describing the sum of integer values, or an empty {@code OptionalInt}
     *               if the stream is empty.
     */
    public <S> OptionalInt sumBy(@NonNull Stream<S> stream,
                                 @NonNull Lens<S, ?, Integer, ?> lens)
    {
        Checks.notnull(stream, lens);
        return stream.map(lens::view).mapToInt(Integer::intValue).reduce(Integer::sum);
    }

    /**
     * Computes the average of integer values in upstream pipeline of {@link Stream} based on the provided {@link Lens}.
     *
     * @param stream The upstream pipeline of {@link Stream}.
     * @param lens   The {@link Lens} to access integer elements for averaging.
     * @return       An {@link OptionalDouble} describing the average of integer values, or an empty {@code OptionalDouble}
     *               if the stream is empty.
     */
    public <S> OptionalDouble averageIntBy(@NonNull Stream<S> stream,
                                           @NonNull Lens<S, ?, Integer, ?> lens)
    {
        return stream.map(lens::view).mapToInt(Integer::intValue).average();
    }

    /**
     * Sorts elements in upstream pipeline of {@link Stream} based on the provided {@link Optic}.
     * <p>
     * If the provided optic is a {@link Getter} or {@link Lens}, the elements will be sorted based on the
     * values accessed by the optic. The accessed values must implement the {@link Comparable} interface.
     * <p>
     * If the provided optic is of other types, the elements will be sorted based on their natural ordering,
     * which requires that the element type implements the {@link Comparable} interface.
     *
     * @param stream The upstream pipeline of {@link Stream}.
     * @param optic  The {@link Optic} to access elements for comparison.
     * @return       The new stream with sorted elements.
     *
     * @param <S>    The type of elements in the stream.
     * @param <A>    The type of the accessed element, which must implement {@link Comparable}.
     */
    public <S, A extends Comparable<A>> Stream<S> sortedBy(@NonNull Stream<S> stream,
                                                           @NonNull Optic<?, S, ?, A, ?> optic)
    {
        Checks.notnull(stream, optic);
        if (optic instanceof Getter)
        {
            Getter<S, ?, A, ?> getter = Unchecks.cast(optic);
            return stream.sorted(Comparator.comparing(getter::get));
        }
        else if (optic instanceof Lens)
        {
            Lens<S, ?, A, ?> lens = Unchecks.cast(optic);
            return stream.sorted(Comparator.comparing(lens::view));
        }
        else
        {
            return stream.sorted();
        }
    }

    /**
     * Removes duplicate elements in upstream pipeline of {@link Stream} based on the provided {@link Optic}.
     * <p>
     * If the provided optic is a {@link Getter} or {@link Lens}, the elements will be considered duplicates
     * if the values accessed by the optic are equal.
     * <p>
     * If the provided optic is of other types, the elements will be considered duplicates based on their
     * natural equality (using {@code equals()} method).
     *
     * @param stream The upstream pipeline of {@link Stream}.
     * @param optic  The {@link Optic} to access elements for equality check.
     * @return       The new stream with duplicate elements removed.
     *
     * @param <S>    The type of elements in the stream.
     * @param <A>    The type of the accessed element used for equality check.
     */
    public <S, A> Stream<S> distinctBy(@NonNull Stream<S> stream,
                                       @NonNull Optic<?, S, ?, A, ?> optic)
    {
        Checks.notnull(stream, optic);
        Set<A> seen = new HashSet<>();
        if (optic instanceof Getter)
        {
            Getter<S, ?, A, ?> getter = Unchecks.cast(optic);
            return stream.filter(s -> seen.add(getter.get(s)));
        }
        else if (optic instanceof Lens)
        {
            Lens<S, ?, A, ?> lens = Unchecks.cast(optic);
            return stream.filter(s -> seen.add(lens.view(s)));
        }
        else
        {
            return stream.distinct();
        }
    }

}
