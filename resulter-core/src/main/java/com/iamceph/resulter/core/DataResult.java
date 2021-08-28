package com.iamceph.resulter.core;

import com.iamceph.resulter.core.api.DataResultable;
import com.iamceph.resulter.core.api.ResultStatus;
import com.iamceph.resulter.core.model.Resulters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

/**
 * DataResult is a "simple" access point for creating {@link DataResultable}.
 */
public abstract class DataResult {

    /**
     * Creates new {@link DataResultable} that has state {@link ResultStatus#OK} with no message and some data.
     *
     * @param data data for this result.
     * @param <T>  type of the data
     * @return OK DataResultable.
     */
    public static <T> DataResultable<T> ok(@NotNull T data) {
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
    public static <T> DataResultable<T> ok(@NotNull T data, @NotNull String message) {
        return Resulters.dataResulter().ok(data, message);
    }

    /**
     * Creates new {@link DataResultable} that has state {@link ResultStatus#FAIL} with message.
     *
     * @param message message that caused this result.
     * @param <T>     type of the data.
     * @return FAIL DataResultable.
     */
    public static <T> DataResultable<T> fail(@NotNull String message) {
        return Resulters.dataResulter().fail(message);
    }

    /**
     * Creates new {@link DataResultable} that has state {@link ResultStatus#FAIL} with throwable and message form throwable.
     *
     * @param throwable error that caused this result.
     * @param <T>       type of the data.
     * @return FAIL DataResultable.
     */
    public static <T> DataResultable<T> fail(@NotNull Throwable throwable) {
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
    public static <T> DataResultable<T> fail(@NotNull T data, @NotNull String message) {
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
    public static <T> DataResultable<T> fail(@NotNull T data, @NotNull String message, @NotNull Throwable throwable) {
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
    public static <T> DataResultable<T> fail(@Nullable String message, @NotNull Throwable throwable) {
        return Resulters.dataResulter().fail(message, throwable);
    }

    /**
     * Creates new {@link DataResultable} that has state {@link ResultStatus#WARNING} with message.
     *
     * @param message message that caused this result.
     * @param <T>     type of the data.
     * @return WARNING DataResultable.
     */
    public static <T> DataResultable<T> warning(@NotNull String message) {
        return Resulters.dataResulter().warning(message);
    }

    /**
     * Creates new {@link DataResultable} that has state {@link ResultStatus#WARNING} with throwable and message form throwable.
     *
     * @param throwable error that caused this result.
     * @param <T>       type of the data.
     * @return WARNING DataResultable.
     */
    public static <T> DataResultable<T> warning(@NotNull Throwable throwable) {
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
    public static <T> DataResultable<T> warning(@NotNull T data, @NotNull String message) {
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
    public static <T> DataResultable<T> warning(@NotNull T data, @NotNull String message, @NotNull Throwable throwable) {
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
    public static <T> DataResultable<T> warning(@NotNull String message, @NotNull Throwable throwable) {
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
    public static <T> DataResultable<T> failIfNull(@Nullable T data) {
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
    public static <T> DataResultable<T> failIfNull(@Nullable T data, @NotNull String failMessage) {
        if (data == null) {
            return DataResult.fail(failMessage);
        }
        return DataResult.ok(data);
    }

    /**
     * @param input input mono
     * @param <T>   type of the data
     * @return {@link Mono} with it's data wrapped into a {@link DataResultable}
     * @see DataResult#mono(Supplier) mono
     */
    public static <T> Mono<DataResultable<T>> mono(@NotNull final Mono<T> input) {
        return mono(() -> input);
    }

    /**
     * Creates new {@link Mono#defer(Supplier)} and wraps it into a {@link DataResultable}.
     * If the input results empty, {@link DataResult#fail(String)} is returned.
     * <p>
     * Error handling is also done, if any error is thrown, {@link DataResult#fail(Throwable)} is returned.
     *
     * @param input input supplier
     * @param <T>   type of the data
     * @return {@link Mono} with it's data wrapped into a {@link DataResultable}
     */
    public static <T> Mono<DataResultable<T>> mono(@NotNull final Supplier<Mono<T>> input) {
        return Mono.defer(input)
                .map(DataResult::ok)
                .onErrorResume(ex -> Mono.just(DataResult.fail(ex)))
                .switchIfEmpty(Mono.just(DataResult.fail("Mono ended empty.")));
    }
}
