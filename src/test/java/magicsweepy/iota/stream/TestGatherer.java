package magicsweepy.iota.stream;

import magicsweepy.iota.kind.list.ListOps;
import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestOnly
public final class TestGatherer
{

    @ParameterizedTest
    @MethodSource("normalArguments")
    public void windowFixedIntsAsStream(List<Integer> input, int windowSize, List<List<Integer>> result)
    {
        assertEquals(result, StreamOps.gatherer(input.stream(), Gatherers.windowFixed(windowSize))
                .map(Collections::unmodifiableList)
                .collect(Collectors.toList()));
    }

    private static Stream<Arguments> normalArguments()
    {
        return Stream.of(
                // Common
                Arguments.of(ListOps.of(1, 2, 3, 4, 5, 6, 7, 8), 3,
                        ListOps.of(ListOps.of(1, 2, 3),
                                ListOps.of(4, 5, 6),
                                ListOps.of(7, 8))),
                // Full
                Arguments.of(ListOps.of(1, 2, 3, 4, 5, 6), 2,
                        ListOps.of(ListOps.of(1, 2),
                                ListOps.of(3, 4),
                                ListOps.of(5, 6))),
                // Single Element
                Arguments.of(ListOps.of(1, 2, 3, 4), 1,
                        ListOps.of(ListOps.of(1),
                                ListOps.of(2),
                                ListOps.of(3),
                                ListOps.of(4))));
    }

    @SuppressWarnings("SimplifyStreamApiCallChains")
    @Test
    public void elementOrderedWindowFixed()
    {
        var list = new ArrayList<>(ListOps.of(1, 2, 3, 4, 5));
        Collections.shuffle(list);

        var windows = StreamOps.gatherer(list.stream(), Gatherers.windowFixed(2))
                .collect(Collectors.toList());

        int i = 0;
        for (var window : windows)
        {
            for (Integer n : window)
            {
                assertEquals(list.get(i++), n);
            }
        }
    }

    @Test
    public void windowSlidingWithMultiWindows()
    {
        var windows = StreamOps.gatherer(Stream.of(1, 2, 3, 4, 5),
                Gatherers.windowSliding(3)).collect(Collectors.toList());

        assertEquals(ListOps.of(ListOps.of(1, 2, 3),
                ListOps.of(2, 3, 4),
                ListOps.of(3, 4, 5)), windows);
    }

    // @Test
    // public void foldSingleElement()
    // {
    //     assertEquals(ListOps.of("1"), StreamOps.gatherer(Stream.of(1),
    //             Gatherers.fold(() -> "", (str, num) -> str + num)));
    // }

    // @Test
    // public void foldMultiElements()
    // {
    //     assertEquals(ListOps.of("123"), StreamOps.gatherer(Stream.of(1, 2, 3),
    //             Gatherers.fold(() -> "", (str, num) -> str + num)));
    // }

    @Test
    public void scanElementWithIntermediate()
    {
        assertEquals(ListOps.of(1, 3, 6), StreamOps.gatherer(Stream.of(1, 2, 3),
                Gatherers.scan(() -> 0, Integer::sum)).collect(Collectors.toList()));
    }

}
