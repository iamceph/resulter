package com.iamceph.resulter.core.api;

import com.iamceph.resulter.core.DataResult;
import com.iamceph.resulter.core.model.Resulters;
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

    /**
     * Tries to filter the input data and decides if the result should FAIL or return OK.
     *
     * @param predicate    data check
     * @param errorMessage error message in case the predicate fails
     * @return {@link DataResult#fail(String)} if the predicate fails
     */
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

    /**
     * @param predicate    data check
     * @return {@link DataResult#fail(String)} if the predicate fails
     * @see DataResultable#filter(Predicate, String) filter
     */
    default DataResultable<T> filter(Predicate<T> predicate) {
        return filter(predicate, "No data found.");
    }

    /**
     * If the main result is FAIL, supplies another data.
     *
     * @param data data to use in case the result is FAIL
     * @return OK DataResult.
     */
    default DataResultable<T> orElse(Supplier<T> data) {
        if (isFail() || !hasData()) {
            return DataResult.ok(data.get());
        }
        return this;
    }

    /**
     * @param data data to use in case the result is FAIL
     * @return OK DataResult.
     * @see DataResultable#orElse(Supplier)
     */
    default DataResultable<T> orElse(T data) {
        return orElse(() -> data);
    }

    /**
     * Provides data into the consumer if the Resultable is OK.
     *
     * @param provider provides data.
     * @return this DataResult
     */
    default DataResultable<T> ifOk(Consumer<T> provider) {
        if (isOk() && hasData()) {
            provider.accept(data());
        }
        return this;
    }

    /**
     * Provides data into the consumer if the Resultable is OK.
     *
     * @param provider provides data.
     * @param fallback returns this result into the fallback.
     * @return this DataResult
     */
    default DataResultable<T> ifOk(Consumer<T> provider, Consumer<Resultable> fallback) {
        if (isOk() && hasData()) {
            provider.accept(data());
            return this;
        }

        fallback.accept(this);
        return this;
    }

    /**
     * If the data is OK, maps them into something else.
     * Exception handling is done, return {@link DataResult#fail(String, Throwable)} if that happens.
     *
     * @param mapper mapping function
     * @param <K>    new type of the data
     * @return OK Resultable if the data are OK.
     */
    default <K> DataResultable<K> map(Function<T, K> mapper) {
        if (isFail()) {
            return transform();
        }

        try {
            return Resulters.dataResulter().ok(mapper.apply(data()), message());
        } catch (Throwable t) {
            return DataResult.fail("map() failed because of Throwable.", t);
        }
    }

    /**
     * Tries to transform this Resultable into an {@link Optional}.
     *
     * @return {@link Optional} of the data.
     */
    default Optional<T> asOptional() {
        return Optional.ofNullable(data());
    }
}
