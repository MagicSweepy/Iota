package magicsweepy.iota.kind;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface SemiGroup<T>
{

    T combine(T first, T second);

}
