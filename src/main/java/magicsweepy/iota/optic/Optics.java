package magicsweepy.iota.optic;

import lombok.experimental.UtilityClass;
import magicsweepy.iota.kind.either.Either;
import org.jspecify.annotations.NonNull;

import java.util.function.BiFunction;
import java.util.function.Function;

@UtilityClass
public class Optics
{

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

}
