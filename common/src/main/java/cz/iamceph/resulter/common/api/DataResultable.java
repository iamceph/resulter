package cz.iamceph.resulter.common.api;

import cz.iamceph.resulter.common.DataResult;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Resultable that can have data.
 *
 * @param <T> Type of the Data
 */
public interface DataResultable<T> extends Resultable {

    /**
     * Checks if this Resultable has any data.
     *
     * @return true if this Resultable has any data.
     */
    boolean hasData();

    /**
     * @return data for this resultable. Null if not present :)
     */
    @Nullable T data();

    default DataResultable<T> filter(Predicate<T> predicate, String errorMessage) {
        if (isFail()) {
            return this;
        }

        try {
            if (!predicate.test(data())) {
                return DataResult.fail(errorMessage);
            }
        } catch (Throwable t) {
            return DataResult.fail("filter() failed because of Throwable.", t);
        }
        return this;
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

    default DataResultable<T> ifOk(Consumer<T> data, Consumer<Resultable> fallback) {
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
            return DataResult.ok(mapper.apply(data()), message());
        } catch (Throwable t) {
            return DataResult.fail("map() failed because of Throwable.", t);
        }
    }

    @SuppressWarnings("unchecked")
    default <K> DataResultable<K> transform() {
        try {
            return (DataResultable<K>) this;
        } catch (Throwable t) {
            return DataResult.fail("transform() failed because of Throwable.", t);
        }
    }

    default Optional<T> asOptional() {
        return Optional.ofNullable(data());
    }
}
