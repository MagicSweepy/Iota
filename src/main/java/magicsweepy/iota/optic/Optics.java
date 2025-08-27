package magicsweepy.iota.optic;

import lombok.experimental.UtilityClass;
import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.either.Either;
import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NonNull;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A utility class for creating various optics such as {@link Lens} and {@link Prism}.
 * <p>
 * Provided short-circuit optics creating by default.
 */
@UtilityClass
public class Optics
{

    // region Optic Constructors

    public <S, T, A, B> Lens<S, T, A, B> lens(final Function<S, A> view,
                                              final BiFunction<B, S, T> update)
    {
        return new Lens<>()
        {

            @NonNull
            @Override
            public A view(@NonNull final S s)
            {
                return view.apply(s);
            }

            @NonNull
            @Override
            public T update(@NonNull final B b, @NonNull final S s)
            {
                return update.apply(b, s);
            }

        };
    }

    public <S, T, A, B> Prism<S, T, A, B> prism(final Function<S, Either<T, A>> match,
                                                final Function<B, T> build)
    {
        return new Prism<>()
        {

            @NonNull
            @Override
            public Either<T, A> match(@NonNull final S s)
            {
                return match.apply(s);
            }

            @NonNull
            @Override
            public T build(@NonNull final B b)
            {
                return build.apply(b);
            }

        };
    }

    public <S, T, A, B> Affine<S, T, A, B> affine(final Function<S, Either<T, A>> preview,
                                                  final BiFunction<B, S, T> build)
    {
        return new Affine<>()
        {

            @NonNull
            @Override
            public Either<T, A> preview(@NonNull final S s)
            {
                return preview.apply(s);
            }

            @NonNull
            @Override
            public T set(@NonNull final B b, @NonNull final S s)
            {
                return build.apply(b, s);
            }

        };
    }

    public static <S, T, A, B> Adapter<S, T, A, B> adapter(final Function<S, A> from, final Function<B, T> to)
    {
        return new Adapter<>()
        {

            @NonNull
            @Override
            public A from(@NonNull final S s)
            {
                return from.apply(s);
            }

            @NonNull
            @Override
            public T to(@NonNull final B b)
            {
                return to.apply(b);
            }

        };
    }

    public static <S, T> Adapter<S, T, S, T> id()
    {
        return Unchecks.cast(IdAdapter.INSTANCE);
    }

    public static <S, T, A, B> Getter<S, T, A, B> getter(final Function<S, A> get)
    {
        return get::apply;
    }

    public static <S, T, A, B> Grate<S, T, A, B> grate(final Functoid<Functoid<Functoid<S, A>, B>, T> grate)
    {
        return grate::apply;
    }

    public static <F, G, F2> ProjL<F, G, F2> leftProj()
    {
        return Unchecks.cast(ProjL.INSTANCE);
    }

    // endregion

    // region Optics Predicate

    public static boolean isId(final Optic<?, ?, ?, ?, ?> optic)
    {
        return optic == IdAdapter.INSTANCE;
    }

    public static boolean isLeftProj(final Optic<?, ?, ?, ?, ?> optic)
    {
        return optic == ProjL.INSTANCE;
    }

    public static <F, G, G2> ProjR<F, G, G2> rightProj()
    {
        return Unchecks.cast(ProjR.INSTANCE);
    }

    public static boolean isRightProj(final Optic<?, ?, ?, ?, ?> optic)
    {
        return optic == ProjR.INSTANCE;
    }

    public static <F, G, F2> InjL<F, G, F2> leftInj()
    {
        return Unchecks.cast(InjL.INSTANCE);
    }

    public static boolean isLeftInj(final Optic<?, ?, ?, ?, ?> optic)
    {
        return optic == InjL.INSTANCE;
    }

    public static <F, G, G2> InjR<F, G, G2> rightInj()
    {
        return Unchecks.cast(InjR.INSTANCE);
    }

    public static boolean isInj2(final Optic<?, ?, ?, ?, ?> optic)
    {
        return optic == InjR.INSTANCE;
    }

    // endregion

}
