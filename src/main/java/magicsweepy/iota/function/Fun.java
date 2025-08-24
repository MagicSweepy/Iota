package magicsweepy.iota.function;

@FunctionalInterface
public interface Fun<T1, R>
{

    R apply(T1 t1);

}
