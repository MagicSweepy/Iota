package magicsweepy.iota.kind.function;

@FunctionalInterface
public interface Fun<T1, R>
{

    R apply(T1 t1);

}
