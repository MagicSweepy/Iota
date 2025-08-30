package magicsweepy.iota.kind.lazy;

import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.kind.function.ThrowableSupplier;
import magicsweepy.iota.util.Checks;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

public final class Lazy<A> implements Kind<Lazy.@NonNull Mu, A>
{

    public static final class Mu implements Ob {}

    private transient volatile boolean evaluated = false;

    @Nullable
    private A value;
    @Nullable
    private Throwable exception;

    @NonNull
    private final ThrowableSupplier<? extends A> evaluator;

    private Lazy(@NonNull ThrowableSupplier<? extends A> evaluator)
    {
        Checks.notnull(evaluator);
        this.evaluator = evaluator;
    }

    @NonNull
    public static <A> Lazy<A> create(@NonNull ThrowableSupplier<? extends A> evaluator)
    {
        return new Lazy<>(evaluator);
    }

    @NonNull
    public static <A> Lazy<A> get(@Nullable A value)
    {
        Lazy<A> lazy = new Lazy<>(() -> value);
        lazy.value = value;
        lazy.exception = null;
        lazy.evaluated = true;
        return lazy;
    }

    @Nullable
    public A pop() throws Throwable
    {
        if (!evaluated)
        {
            synchronized (this)
            {
                if (!evaluated)
                {
                    try
                    {
                        value = evaluator.get();
                        exception = null;
                    }
                    catch (Throwable throwable)
                    {
                        exception = throwable;
                        value = null;
                    }
                    finally
                    {
                        evaluated = true;
                    }
                }
            }
        }
        if (this.exception != null)
        {
            throw this.exception;
        }
        return this.value;
    }

    @NonNull
    public <B> Lazy<B> map(@NonNull Function<? super A, ? extends B> f)
    {
        Checks.notnull(f);
        return Lazy.create(() -> f.apply(this.pop()));
    }

    @NonNull
    public <B> Lazy<B> flatMap(@NonNull Function<? super A, ? extends Lazy<? extends B>> f)
    {
        Checks.notnull(f);
        return Lazy.create(() -> {
            Lazy<? extends B> pullLazy = f.apply(this.pop());
            Checks.notnull(pullLazy);
            return pullLazy.pop();
        });
    }

    @NonNull
    @Override
    public String toString()
    {
        if (evaluated)
        {
            if (exception != null)
                return "Lazy[exception: \"]" + exception.getClass().getSimpleName() + "\"]";
            else
                return "Lazy[value: \"]" + value + "\"]";
        }
        else
        {
            return "Lazy";
        }
    }

}
