package magicsweepy.iota.stream;

import lombok.experimental.UtilityClass;
import magicsweepy.iota.kind.list.ListOps;
import magicsweepy.iota.util.Checks;
import org.jetbrains.annotations.Range;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

/**
 * Implementations of {@link Gatherer} that provide useful intermediate operations, such as
 * windowing functions, folding functions, transforming elements concurrently, e.t.c.
 */
@UtilityClass
public class Gatherers
{

    /**
     * Returns a {@code Gatherer} that gathers elements into windows -- encounter-ordered groups of elements -- of a
     * fixed size. If the stream is empty then no window will be produced. The last window may contain fewer elements
     * than the supplied window size. For example:
     * <pre>{@code
     *     // Will contains: [[1, 2, 3], [4, 5, 6], [7, 8]]
     *     var windows = Stream.of(1, 2, 3, 4, 5, 6, 7, 8)
     *     LazyStream.gatherer(windows, Gatherers.windowFixed(3)).toList();
     * }</pre>
     *
     * @param windowSize The size of the windows.
     * @return           A new gatherer which groups elements into fixed-size windows.
     *
     * @param <TR> The type of elements the returned gatherer consumes and the contents of the windows it produces.
     *
     * @throws IllegalArgumentException When {@code windowSize} is less than 1.
     *
     * @apiNote For efficiency reasons, windows may be allocated contiguously and eagerly. This means that choosing
     *          large window sizes for small streams may use excessive memory for the duration of evaluation of this
     *          operation.
     *
     * @implSpec Each window produced is an unmodifiable List; calls to any mutator method will always cause
     *           the {@link UnsupportedOperationException} to be thrown. There are no guarantees on the implementation
     *           type or serializability of the produced lists.
     */
    @SuppressWarnings("unchecked")
    public <TR> Gatherer<TR, ?, List<TR>> windowFixed(@Range(from = 1, to = Integer.MAX_VALUE) int windowSize)
    {
        class FixedWindow
        {
            Object[] window;
            int at;

            FixedWindow()
            {
                at = 0;
                window = new Object[windowSize];
            }

            boolean integrate(TR element, Gatherer.Downstream<? super List<TR>> downstream)
            {
                window[at++] = element;
                if (at < windowSize)
                {
                    return true;
                }
                else
                {
                    final Object[] oldWindow = window;
                    window = new Object[windowSize];
                    at = 0;
                    return downstream.push((List<TR>) ListOps.ofMutable(oldWindow));
                }
            }

            void finish(Gatherer.Downstream<? super List<TR>> downstream)
            {
                if (at > 0 && !downstream.isRejecting())
                {
                    Object[] lastWindow = new Object[at];
                    System.arraycopy(window, 0, lastWindow, 0, at);
                    window = null;
                    at = 0;
                    downstream.push((List<TR>) ListOps.ofMutable(lastWindow));
                }
            }

        }

        return Gatherer.<TR, FixedWindow, List<TR>>ofSequential(FixedWindow::new, // Initializer.
                Gatherer.Integrator.<FixedWindow, TR, List<TR>>ofGreedy(FixedWindow::integrate), // Integrator.
                FixedWindow::finish); // Finisher.
    }

    /**
     * Returns a {@code Gatherer} that gathers elements into windows -- encounter-ordered groups of elements -- of a
     * given size, where each subsequent window includes all elements of the previous window except for the least
     * recent, and adds the next element in the stream. If the stream is empty then no window will be produced. If the
     * size of the stream is smaller than the window size then only one window will be produced, containing all elements
     * in the stream. For example:
     * <pre>{@code
     *     // Will contains: [[1, 2], [2, 3], [3, 4], [4, 5], [5, 6], [6, 7], [7, 8]].
     *     var windows = Stream.of(1, 2, 3, 4, 5, 6, 7, 8);
     *     LazyStream.gatherer(windows, Gatherers.windowSliding(2)).toList();
     * }</pre>
     *
     * @param windowSize The size of the windows.
     * @return           A new {@code Gatherer} which groups elements into sliding windows.
     *
     * @param <TR> The type of elements the returned gatherer consumes and the contents of the windows it produces.
     *
     * @implSpec Each window produced is an unmodifiable {@code List}; calls to any mutator method will always cause
     *           the {@link UnsupportedOperationException} to be thrown. There are no guarantees on the implementation
     *           type or serializability of the produced Lists.
     *
     * @apiNote For efficiency reasons, windows may be allocated contiguously and eagerly. This means that choosing
     *          large window sizes for small streams may use excessive memory for the duration of evaluation of this
     *          operation.
     *
     * @throws IllegalArgumentException When {@code windowSize} is less than <tt>1</tt>.
     */
    @SuppressWarnings("unchecked")
    public <TR> Gatherer<TR, ?, List<TR>> windowSliding(@Range(from = 1, to = Integer.MAX_VALUE) int windowSize)
    {
        class SlidingWindow
        {
            Object[] window;
            int at;
            boolean firstWindow;

            SlidingWindow()
            {
                firstWindow = true;
                at = 0;
                window = new Object[windowSize];
            }

            boolean integrate(TR element, Gatherer.Downstream<? super List<TR>> downstream)
            {
                window[at++] = element;
                if (at < windowSize)
                {
                    return true;
                } else {
                    final var oldWindow = window;
                    final var newWindow = new Object[windowSize];
                    System.arraycopy(oldWindow,1, newWindow, 0, windowSize - 1);
                    window = newWindow;
                    at -= 1;
                    firstWindow = false;
                    return downstream.push((List<TR>) ListOps.ofMutable(oldWindow));
                }
            }

            void finish(Gatherer.Downstream<? super List<TR>> downstream)
            {
                if (firstWindow && at > 0 && !downstream.isRejecting())
                {
                    var lastWindow = new Object[at];
                    System.arraycopy(window, 0, lastWindow, 0, at);
                    window = null;
                    at = 0;
                    downstream.push((List<TR>) ListOps.ofMutable(lastWindow));
                }
            }
        }
        return Gatherer.<TR, SlidingWindow, List<TR>>ofSequential(SlidingWindow::new, // Initializer.
                Gatherer.Integrator.<SlidingWindow, TR, List<TR>>ofGreedy(SlidingWindow::integrate), // Integrator.
                SlidingWindow::finish); // Finisher.
    }

    /**
     * Returns a {@code Gatherer} that performs an ordered, <i>reduction-like</i>, transformation for scenarios where no
     * combiner-function can be implemented, or for reductions which are intrinsically order-dependent. For example:
     * <pre>{@code
     *     // Will contains: Optional["123456789"]
     *     var windows = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
     *     LazyStream.gatherer(windows, Gatherers.fold(() -> "", (str, num) -> str + num)).findFirst();
     * }</pre>
     *
     * @param initial The identity value for the fold operation.
     * @param folder  The folding function.
     * @return        A new {@code Gatherer}.
     *
     * @param <T> The type of elements the returned gatherer consumes.
     * @param <R> The type of elements the returned gatherer produces.
     *
     * @throws NullPointerException If any of the parameters are {@code null}.
     *
     * @implSpec If no exceptions are thrown during processing, then this operation only ever produces a single element.
     *
     * @see java.util.stream.Stream#reduce(Object, BinaryOperator)
     */
    public <T, R> Gatherer<T, ?, R> fold(@NonNull Supplier<R> initial,
                                         @NonNull BiFunction<? super R, ? super T, ? extends R> folder)
    {
        Checks.notnull(initial, folder);

        class State
        {
            R value = initial.get();

            State() {}
        }

        return Gatherer.ofSequential(State::new, // Initializer.
                Gatherer.Integrator.ofGreedy((state, element, downstream) -> {
                    state.value = folder.apply(state.value, element);
                    return true;
                }), // Integrator.
                (state, downstream) -> downstream.push(state.value)); // Finisher.
    }

    /**
     * Returns a {@code Gatherer} that performs a Prefix Scan -- an incremental accumulation -- using the provided
     * functions.  Starting with an initial value obtained from the {@code Supplier}, each subsequent value is obtained
     * by applying the {@code BiFunction} to the current value and the next input element, after which the resulting
     * value is produced downstream. For example:
     * <pre>{@code
     *     // Will contains: ["1", "12", "123", "1234", "12345", "123456", "1234567", "12345678", "123456789"].
     *     var nums = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
     *     LazyStream.gatherer(nums, Gatherers.scan(() -> "", (str, num) -> str + num)).toList();
     * }</pre>
     *
     * @param initial The supplier of the initial value for the scanner.
     * @param scanner The function to apply for each element.
     * @return        A new {@code Gatherer} which performs a prefix scan.
     *
     * @param <T> The type of element which this gatherer consumes.
     * @param <R> The type of element which this gatherer produces.
     *
     * @throws NullPointerException If any of the parameters are {@code null}.
     */
    public <T, R> Gatherer<T, ?, R> scan(@NonNull Supplier<R> initial,
                                         @NonNull BiFunction<? super R, ? super T, ? extends R> scanner)
    {
        Checks.notnull(initial, scanner);

        class State
        {
            R current = initial.get();

            boolean integrate(T element, Gatherer.Downstream<? super R> downstream)
            {
                return downstream.push(current = scanner.apply(current, element));
            }
        }

        return Gatherer.ofSequential(State::new, // Initializer
                Gatherer.Integrator.<State,T, R>ofGreedy(State::integrate)); // Integrator
    }

}
