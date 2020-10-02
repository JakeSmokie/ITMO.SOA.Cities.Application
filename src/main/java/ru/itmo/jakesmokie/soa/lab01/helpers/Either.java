package ru.itmo.jakesmokie.soa.lab01.helpers;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class Either<L, R> {
    public static <L, R> Either<L, R> left(L value) {
        return new Either<L, R>(Optional.of(value), Optional.empty());
    }

    public static <L, R> Either<L, R> right(R value) {
        return new Either<>(Optional.empty(), Optional.of(value));
    }

    private final Optional<L> left;
    private final Optional<R> right;

    private Either(Optional<L> l, Optional<R> r) {
        left = l;
        right = r;
    }

    public <T> T map(
        Function<? super L, ? extends T> lFunc,
        Function<? super R, ? extends T> rFunc
    ) {
        return left.<T>map(lFunc).orElseGet(() -> right.map(rFunc).orElse(null));
    }

    public <T> Either<T, R> mapLeft(Function<? super L, ? extends T> lFunc) {
        return new Either<>(left.map(lFunc), right);
    }

    public <T> Either<L, T> mapRight(Function<? super R, ? extends T> rFunc) {
        return new Either<>(left, right.map(rFunc));
    }

    public <T> Either<L, T> flatMap(Function<? super R, Either<L, T>> rFunc) {
        if (right.isPresent()) {
            return rFunc.apply(right.get());
        } else {
            return new Either<>(left, Optional.empty());
        }
    }

    public void apply(Consumer<? super L> lFunc, Consumer<? super R> rFunc) {
        left.ifPresent(lFunc);
        right.ifPresent(rFunc);
    }
}