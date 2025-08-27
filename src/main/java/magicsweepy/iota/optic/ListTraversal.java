package magicsweepy.iota.optic;

import com.google.common.collect.ImmutableList;
import magicsweepy.iota.kind.Applicative;
import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Ob;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public final class ListTraversal<A, B> implements Traversal<List<A>, List<B>, A, B>
{

    static final ListTraversal<?, ?> INSTANCE = new ListTraversal<>();

    @Override
    public <F extends Ob> Functoid<List<A>, Kind<F, List<B>>> wander(final Applicative<F, ?> applicative,
                                                                              final Functoid<A, Kind<F, B>> input)
    {
        return as -> {
            Kind<F, ImmutableList.Builder<B>> result = applicative.point(ImmutableList.builder());
            for (final A a : as)
            {
                result = applicative.ap2(applicative.point(ImmutableList.Builder::add), result, input.apply(a));
            }
            return applicative.map(ImmutableList.Builder::build, result);
        };
    }

    @Override
    public String toString()
    {
        return "ListTraversal";
    }

}