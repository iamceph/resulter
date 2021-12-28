package com.iamceph.resulter.core;

import com.iamceph.resulter.core.api.ResultStatus;
import com.iamceph.resulter.core.model.Resulters;
import lombok.var;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

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
     * Creates new {@link DataResultable} that has state {@link ResultStatus#OK} with no message and some data.
     *
     * @param data data for this result.
     * @param <T>  type of the data
     * @return OK DataResultable.
     */
    static <T> DataResultable<T> ok(@NotNull T data) {
        return Resulters.dataResulter().ok(data);
    }

    /**
     * Creates new {@link DataResultable} that has state {@link ResultStatus#OK} with message and some data.
     *
     * @param data    data for this result.
     * @param message message for this result.
     * @param <T>     type of the data.
     * @return OK DataResultable.
     */
    static <T> DataResultable<T> ok(@NotNull T data, @NotNull String message) {
        return Resulters.dataResulter().ok(data, message);
    }

    /**
     * Creates new {@link DataResultable} that has state {@link ResultStatus#FAIL} with message.
     *
     * @param message message that caused this result.
     * @param <T>     type of the data.
     * @return FAIL DataResultable.
     */
    static <T> DataResultable<T> fail(@NotNull String message) {
        return Resulters.dataResulter().fail(message);
    }

    /**
     * Creates new {@link DataResultable} that has state {@link ResultStatus#FAIL} with throwable and message form throwable.
     *
     * @param throwable error that caused this result.
     * @param <T>       type of the data.
     * @return FAIL DataResultable.
     */
    static <T> DataResultable<T> fail(@NotNull Throwable throwable) {
        return Resulters.dataResulter().fail(throwable);
    }

    /**
     * Creates new {@link DataResultable} that has state {@link ResultStatus#FAIL} with message and failed data.
     *
     * @param data    data for this result.
     * @param message message for this result.
     * @param <T>     type of the data.
     * @return FAIL DataResultable.
     */
    static <T> DataResultable<T> fail(@NotNull T data, @NotNull String message) {
        return Resulters.dataResulter().fail(data, message);
    }

    /**
     * Creates new {@link DataResultable} that has state {@link ResultStatus#FAIL} with message, throwable and failed data.
     *
     * @param data      data for this result.
     * @param message   message for this result.
     * @param throwable error that caused this result.
     * @param <T>       type of the data.
     * @return FAIL DataResultable.
     */
    static <T> DataResultable<T> fail(@NotNull T data, @NotNull String message, @NotNull Throwable throwable) {
        return Resulters.dataResulter().fail(data, message, throwable);
    }

    /**
     * Creates new {@link DataResultable} that has state {@link ResultStatus#FAIL} with message and throwable.
     *
     * @param message   message for this result.
     * @param throwable error that caused this result.
     * @param <T>       type of the data.
     * @return FAIL DataResultable.
     */
    static <T> DataResultable<T> fail(@Nullable String message, @NotNull Throwable throwable) {
        return Resulters.dataResulter().fail(message, throwable);
    }

    /**
     * Creates new {@link DataResultable} that has state {@link ResultStatus#WARNING} with message.
     *
     * @param message message that caused this result.
     * @param <T>     type of the data.
     * @return WARNING DataResultable.
     */
    static <T> DataResultable<T> warning(@NotNull String message) {
        return Resulters.dataResulter().warning(message);
    }

    /**
     * Creates new {@link DataResultable} that has state {@link ResultStatus#WARNING} with throwable and message form throwable.
     *
     * @param throwable error that caused this result.
     * @param <T>       type of the data.
     * @return WARNING DataResultable.
     */
    static <T> DataResultable<T> warning(@NotNull Throwable throwable) {
        return Resulters.dataResulter().warning(throwable);
    }

    /**
     * Creates new {@link DataResultable} that has state {@link ResultStatus#WARNING} with message and failed data.
     *
     * @param data    data for this result.
     * @param message message for this result.
     * @param <T>     type of the data.
     * @return WARNING DataResultable.
     */
    static <T> DataResultable<T> warning(@NotNull T data, @NotNull String message) {
        return Resulters.dataResulter().warning(data, message);
    }

    /**
     * Creates new {@link DataResultable} that has state {@link ResultStatus#WARNING} with message, throwable and failed data.
     *
     * @param data      data for this result.
     * @param message   message for this result.
     * @param throwable error that caused this result.
     * @param <T>       type of the data.
     * @return WARNING DataResultable.
     */
    static <T> DataResultable<T> warning(@NotNull T data, @NotNull String message, @NotNull Throwable throwable) {
        return Resulters.dataResulter().warning(data, message, throwable);
    }

    /**
     * Creates new {@link DataResultable} that has state {@link ResultStatus#WARNING} with message and throwable.
     *
     * @param message   message for this result.
     * @param throwable error that caused this result.
     * @param <T>       type of the data.
     * @return WARNING DataResultable.
     */
    static <T> DataResultable<T> warning(@NotNull String message, @NotNull Throwable throwable) {
        return Resulters.dataResulter().warning(message, throwable);
    }

    /**
     * Creates new {@link DataResultable} that is in {@link ResultStatus#OK} if the input is <i>not null</i>.
     * If the input is null, then the state is {@link ResultStatus#FAIL}.
     *
     * @param data data for this result, can be null.
     * @param <T>  type of the data.
     * @return DataResultable dependent on the input.
     */
    static <T> DataResultable<T> failIfNull(@Nullable T data) {
        return failIfNull(data, "No data provided.");
    }

    /**
     * Creates new {@link DataResultable} that is in {@link ResultStatus#OK} if the input is <i>not null</i>.
     * If the input is null, then the state is {@link ResultStatus#FAIL}.
     *
     * @param data        data for this result, can be null.
     * @param failMessage custom message if the result fails.
     * @param <T>         type of the data.
     * @return DataResultable dependent on the input.
     */
    static <T> DataResultable<T> failIfNull(@Nullable T data, @NotNull String failMessage) {
        if (data == null) {
            return DataResultable.fail(failMessage);
        }
        return DataResultable.ok(data);
    }

    /**
     * @param input input mono
     * @param <T>   type of the data
     * @return {@link Mono} with it's data wrapped into a {@link DataResultable}
     * @see DataResultable#mono(Supplier) mono
     */
    static <T> Mono<DataResultable<T>> mono(@NotNull final Mono<T> input) {
        return mono(() -> input);
    }

    /**
     * Creates new {@link Mono#defer(Supplier)} and wraps it into a {@link DataResultable}.
     * If the input results empty, {@link DataResultable#fail(String)} is returned.
     * <p>
     * Error handling is also done, if any error is thrown, {@link DataResultable#fail(Throwable)} is returned.
     *
     * @param input input supplier
     * @param <T>   type of the data
     * @return {@link Mono} with it's data wrapped into a {@link DataResultable}
     */
    static <T> Mono<DataResultable<T>> mono(@NotNull final Supplier<Mono<T>> input) {
        return Mono.defer(input)
                .map(DataResultable::ok)
                .onErrorResume(ex -> Mono.just(DataResultable.fail(ex)))
                .switchIfEmpty(Mono.just(DataResultable.fail("Mono ended empty.")));
    }

    /**
     * Tries to convert given {@link Object} into {@link DataResultable}.
     *
     * @param input input
     * @return Resultable
     */
    static <T> DataResultable<T> from(Object input, T data) {
        final var result = Resulters.resulter().convert(input);
        return result.transform(data);
    }

    /**
     * Tries to convert given {@link Optional} into {@link DataResultable}.
     *
     * @param input input
     * @return Resultable
     */
    static <T> DataResultable<T> fromOptional(Optional<T> input) {
        if (input.isPresent()) {
            return DataResultable.ok(input.get());
        }
        return DataResultable.fail("No data present.");
    }

    /**
     * Tries to filter the input data and decides if the result should FAIL or return OK.
     *
     * @param predicate    data check
     * @param errorMessage error message in case the predicate fails
     * @return {@link DataResultable#fail(String)} if the predicate fails
     */
    default DataResultable<T> filter(Predicate<T> predicate, String errorMessage) {
        if (isFail()) {
            return this;
        }

        try {
            if (!predicate.test(data())) {
                return DataResultable.fail(errorMessage);
            }
        } catch (Throwable t) {
            return DataResultable.fail("filter() failed because of Throwable.", t);
        }
        return this;
    }

    /**
     * @param predicate data check
     * @return {@link DataResultable#fail(String)} if the predicate fails
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
            return DataResultable.ok(data.get());
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
     * Maps given data to something else with given function.
     * This can only happen if the {@link DataResultable} is {@link ResultStatus#OK}.
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
            return DataResultable.fail("map() failed because of Throwable.", t);
        }
    }

    /**
     * Maps given data to different {@link DataResultable}.
     * This can only happen if the {@link DataResultable} is {@link ResultStatus#OK}.
     *
     * @param mapper mapping function
     * @param <K>    new type of the data
     * @return OK Resultable if the data are OK.
     */
    default <K> DataResultable<K> flatMap(Function<T, DataResultable<K>> mapper) {
        if (isFail()) {
            return transform();
        }

        try {
            return mapper.apply(data());
        } catch (Throwable t) {
            return DataResultable.fail("flatMap() failed because of Throwable.", t);
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

    /**
     * Checks if this Resultable has any data.
     *
     * @return true if this Resultable has any data.
     */
    boolean hasData();

    /**
     * @return data for this resultable. Null if not present, duh. :)
     */
    T data();
}
