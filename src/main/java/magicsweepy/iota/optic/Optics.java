package magicsweepy.iota.optic;

import lombok.experimental.UtilityClass;
import magicsweepy.iota.kind.Applicative;
import magicsweepy.iota.kind.Functoid;
import magicsweepy.iota.kind.Kind;
import magicsweepy.iota.kind.Ob;
import magicsweepy.iota.kind.either.Either;
import magicsweepy.iota.kind.tuple.Pair;
import magicsweepy.iota.util.Unchecks;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
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

    public static <F, G, F2, G2, A, B> Lens<Either<F, G>, Either<F2, G2>, A, B> eitherLens(final Lens<F, F2, A, B> fLens,
                                                                                           final Lens<G, G2, A, B> gLens)
    {
        return lens(either -> either.map(fLens::view, gLens::view),
                (b, either) -> either.mapBoth(f -> fLens.update(b, f), g -> gLens.update(b, g)));
    }

    public static <F, G, F2, G2, A, B> Affine<Either<F, G>, Either<F2, G2>, A, B> eitherAffine(final Affine<F, F2, A, B> fAffine,
                                                                                               final Affine<G, G2, A, B> gAffine)
    {
        return affine(either -> either.map(f -> fAffine.preview(f).mapLeft(Either::left),
                g -> gAffine.preview(g).mapLeft(Either::right)),
                (b, either) -> either.mapBoth(f -> fAffine.set(b, f), g -> gAffine.set(b, g)));
    }

    public static <F, G, F2, G2, A, B> Traversal<Either<F, G>, Either<F2, G2>, A, B> eitherTraversal(final Traversal<F, F2, A, B> fOptic,
                                                                                                              final Traversal<G, G2, A, B> gOptic)
    {
        return new Traversal<>()
        {

            @NonNull
            @Override
            public <FT extends Ob> Functoid<Either<F, G>, Kind<@NonNull FT, Either<F2, G2>>> wander(@NonNull final Applicative<@NonNull FT, ?> applicative,
                                                                                                    @NonNull final Functoid<A, Kind<@NonNull FT, B>> input)
            {
                return e -> e.map(l -> { return applicative.ap(Either::left, fOptic.wander(applicative, input).apply(l)); },
                        r -> { return applicative.ap(Either::right, gOptic.wander(applicative, input).apply(r)); });
            }

        };
    }

    public static <A, B> ListTraversal<A, B> listTraversal()
    {
        return Unchecks.cast(ListTraversal.INSTANCE);
    }

    public static <R, A, B> Forget<R, A, B> forget(final Function<A, R> function)
    {
        return function::apply;
    }

    public static <R, A, B> ForgetOpt<R, A, B> forgetOpt(final Function<A, Optional<R>> function)
    {
        return function::apply;
    }

    public static <R, A, B> ForgetE<R, A, B> forgetE(final Function<A, Either<B, R>> function)
    {
        return function::apply;
    }

    public static <R, A, B> ReForget<R, A, B> reForget(final Function<R, B> function)
    {
        return function::apply;
    }

    public static <R, A, B> ReForgetEP<R, A, B> reForgetEP(final String name,
                                                           final Function<Either<A, Pair<A, R>>, B> function)
    {
        return new ReForgetEP<>()
        {

            @NonNull
            @Override
            public B run(@NonNull final Either<A, Pair<A, R>> e)
            {
                return function.apply(e);
            }

            @Override
            public String toString()
            {
                return "ReForgetEP_" + name;
            }

        };
    }

    public static <R, A, B> ReForgetE<R, A, B> reForgetE(final String name,
                                                         final Function<Either<A, R>, B> function)
    {
        return new ReForgetE<>()
        {

            @NonNull
            @Override
            public B run(@NonNull final Either<A, R> t)
            {
                return function.apply(t);
            }

            @Override
            public String toString()
            {
                return "ReForgetE_" + name;
            }

        };
    }

    public static <R, A, B> ReForgetP<R, A, B> reForgetP(final String name,
                                                         final BiFunction<A, R, B> function)
    {
        return new ReForgetP<>()
        {

            @NonNull
            @Override
            public B run(@NonNull final A a, @NonNull final R r)
            {
                return function.apply(a, r);
            }

            @Override
            public String toString()
            {
                return "ReForgetP_" + name;
            }

        };
    }

    public static <R, A, B> ReForgetC<R, A, B> reForgetC(final String name,
                                                                  final Either<Function<R, B>, BiFunction<A, R, B>> either)
    {
        return new ReForgetC<>()
        {

            @NonNull
            @Override
            public Either<Function<R, B>, BiFunction<A, R, B>> construct()
            {
                return either;
            }

            @Override
            public String toString()
            {
                return "ReForgetC_" + name;
            }

        };
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
