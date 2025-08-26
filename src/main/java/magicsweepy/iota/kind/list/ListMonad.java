package magicsweepy.iota.kind.list;

import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.SemiMonad;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@NullMarked
public class ListMonad implements SemiMonad<ListKind.Mu>
{

    @Override
    public <A> Kind<ListKind.Mu, A> point()
    {
        return ListKind.of(ListOps.of());
    }

    @Override
    public <A> Kind<ListKind.Mu, A> point(A a)
    {
        return ListKind.of(ListOps.of(a));
    }

    @Override
    public <A> Kind<ListKind.Mu, A> of(@Nullable A value)
    {
        if (value == null)
            return ListKind.of(ListOps.of());
        return ListKind.of(ListOps.of(value));
    }

    @Override
    public <A, B> Kind<ListKind.Mu, B> map(Function<? super A, ? extends B> f,
                                           Kind<ListKind.Mu, A> fa)
    {
        List<A> leftList = ListKind.unbox(fa).unbox();
        List<B> rightList = new ArrayList<>(leftList.size());

        leftList.forEach(a -> rightList.add(f.apply(a)));
        return ListKind.of(rightList);
    }

    @Override
    public <A, B> Kind<ListKind.Mu, B> flatMap(Function<? super A, ? extends Kind<ListKind.Mu, B>> f,
                                               Kind<ListKind.Mu, A> fa)
    {
        List<A> listA = ListKind.unbox(fa).unbox();
        List<B> listB = new ArrayList<>();

        listA.forEach(a -> listB.addAll(ListKind.unbox(f.apply(a)).unbox()));
        return ListKind.of(listB);
    }

    @Override
    public <A, B> Function<Kind<ListKind.Mu, A>, Kind<ListKind.Mu, B>> lift(Kind<ListKind.Mu, Function<A, B>> f)
    {
        return fa -> {
            List<Function<A, B>> fList = ListKind.unbox(f).unbox();
            List<A> listA = ListKind.unbox(fa).unbox();
            List<B> listB = new ArrayList<>();

            fList.forEach(g -> {
                for (A a : listA) listB.add(g.apply(a));
            });
            return ListKind.of(listB);
        };
    }

    @Override
    public <A, B> Kind<ListKind.Mu, B> ap(Kind<ListKind.Mu, Function<A, B>> f,
                                                   Kind<ListKind.Mu, A> fa)
    {
        List<? extends Function<A, B>> fList = ListKind.unbox(f).unbox();
        List<A> listA = ListKind.unbox(fa).unbox();
        List<B> listB = new ArrayList<>();

        if (fList.isEmpty() || listA.isEmpty())
            return ListKind.of(ListOps.of());

        fList.forEach(h -> {
            for (A a : listA) listB.add(h.apply(a));
        });
        return ListKind.of(listB);
    }

}
