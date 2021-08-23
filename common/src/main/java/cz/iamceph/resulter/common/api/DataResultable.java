package cz.iamceph.resulter.common.api;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import cz.iamceph.resulter.common.DataResult;
import cz.iamceph.resulter.common.SimpleResult;

/**
 * Resultable that can have data.
 * @param <T>
 */
public interface DataResultable<T> extends Resultable {

    boolean hasData();

    T data();

    default DataResultable<T> filter(Predicate<T> predicate, String errorMessage) {
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

    default DataResultable<T> filter(Predicate<T> predicate) {
        return filter(predicate, "No data found.");
    }

    default DataResultable<T> orElse(Supplier<T> data) {
        if (isFail() || !hasData()) {
            return DataResult.ok(data.get());
        }
        return this;
    }

    default DataResultable<T> orElse(T data) {
        return orElse(() -> data);
    }

    default DataResultable<T> ifOk(Consumer<T> data) {
        if (isOk() && hasData()) {
            data.accept(data());
        }
        return this;
    }

    default DataResultable<T> ifOkOrElse(Consumer<T> data, Consumer<Resultable> fallback) {
        if (isOk() && hasData()) {
            data.accept(data());
            return this;
        }

        fallback.accept(this);
        return this;
    }

    default <K> DataResultable<K> map(Function<T, K> mapper) {
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
    default <K> DataResultable<K> transform() {
        return (DataResultable<K>) this;
    }

    default Optional<T> asOptional() {
        if (isFail() || isWarning()) {
            return Optional.empty();
        }
        return Optional.of(data());
    }

    default Resultable asSimple() {
        switch (this.status()) {
            case FAIL:
                return SimpleResult.fail(message());
            case OK:
                return SimpleResult.ok(message());
            default:
                return SimpleResult.warning(message());
        }
    }
}
