package cz.iamceph.resulter.common;

import cz.iamceph.resulter.common.model.DataResultable;
import cz.iamceph.resulter.common.model.ResultStatus;
import cz.iamceph.resulter.common.model.Resultable;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class DataResult<T> implements DataResultable<T> {

    static <T> DataResult<T> ok(T data) {
        return new DataResultImpl<>(ResultStatus.OK, "Ok.", null, data);
    }

    static <T> DataResult<T> ok(String message, T data) {
        return new DataResultImpl<>(ResultStatus.OK, message, null, data);
    }

    static <T> DataResult<T> fail(String message) {
        return new DataResultImpl<>(ResultStatus.FAIL, message, null, null);
    }

    static <T> DataResult<T> fail(String message, Throwable throwable) {
        return new DataResultImpl<>(ResultStatus.FAIL, message, throwable, null);
    }

    static <T> DataResult<T> fail(Throwable throwable) {
        return new DataResultImpl<>(ResultStatus.FAIL, throwable.getMessage(), throwable, null);
    }

    static <T> DataResult<T> failIfNull(T data) {
        return failIfNull(data, "No data provided.");
    }

    static <T> DataResult<T> failIfNull(T data, String message) {
        if (data == null) {
            return DataResult.fail(message);
        }
        return DataResult.ok(data);
    }

    static <T> DataResult<T> warning() {
        return new DataResultImpl<>(ResultStatus.WARNING, "Unknown status.", null, null);
    }

    static <T> DataResult<T> warning(String message) {
        return new DataResultImpl<>(ResultStatus.WARNING, message, null, null);
    }

    static <T> DataResult<T> warning(T data) {
        return new DataResultImpl<>(ResultStatus.WARNING, null, null, data);
    }

    static <T> Mono<DataResult<T>> mono(Mono<T> input) {
        return Mono.defer(() -> input)
                .map(DataResult::ok)
                .onErrorResume(ex -> Mono.just(DataResult.fail(ex)));
    }

    public DataResult<T> filter(Predicate<T> predicate, String errorMessage) {
        if (isFail()) {
            return this;
        }

        try {
            if (predicate.test(data())) {
                return DataResult.ok(data());
            }
            return DataResult.fail(errorMessage);
        } catch (Throwable t) {
            return DataResult.fail("filter() failed because of Throwable.", t);
        }
    }

    public DataResult<T> filter(Predicate<T> predicate) {
        return filter(predicate, "No data found.");
    }

    public DataResult<T> orElse(Supplier<T> data) {
        if (isFail() || !hasData()) {
            return DataResult.ok(data.get());
        }
        return this;
    }

    public DataResult<T> orElse(T data) {
        return orElse(() -> data);
    }

    public DataResult<T> ifOk(Consumer<T> data) {
        if (isOk() && hasData()) {
            data.accept(data());
        }
        return this;
    }

    public DataResult<T> ifOkOrElse(Consumer<T> data, Consumer<Resultable> fallback) {
        if (isOk() && hasData()) {
            data.accept(data());
            return this;
        }

        fallback.accept(this);
        return this;
    }

    public <K> DataResult<K> map(Function<T, K> mapper) {
        if (isFail()) {
            return transform();
        }

        try {
            return DataResult.ok(mapper.apply(data()));
        } catch (Throwable t) {
            return DataResult.fail("map() failed because of Throwable.", t);
        }
    }

    @SuppressWarnings("unchecked")
    public <K> DataResult<K> transform() {
        return (DataResult<K>) this;
    }

    public Optional<T> asOptional() {
        if (isFail() || isWarning()) {
            return Optional.empty();
        }
        return Optional.of(data());
    }

    public SimpleResult asSimple() {
        switch (this.status()) {
            case FAIL:
                return SimpleResult.fail(message());
            case OK:
                return SimpleResult.ok(message());
            default:
                return SimpleResult.warn(message());
        }
    }
}
