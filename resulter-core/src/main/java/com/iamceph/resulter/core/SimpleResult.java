package com.iamceph.resulter.core;

import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.iamceph.resulter.core.api.ResultStatus;
import com.iamceph.resulter.core.api.Resultable;
import com.iamceph.resulter.core.model.Resulters;

import reactor.core.publisher.Mono;

/**
 * SimpleResult is a "simple" access point for creating {@link Resultable}.
 */
public abstract class SimpleResult {

    protected SimpleResult() {
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#OK} with no message.
     *
     * @return OK Resultable.
     */
    public static Resultable ok() {
        return Resulters.resulter().ok();
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#OK} and holds a message.
     *
     * @param message message of this result.
     * @return OK Resultable with message.
     */
    public static Resultable ok(@NotNull String message) {
        return Resulters.resulter().ok(message);
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#FAIL} and holds a message.
     *
     * @param message message of this result.
     * @return FAIL Resultable with message.
     */
    public static Resultable fail(@NotNull String message) {
        return Resulters.resulter().fail(message);
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#FAIL} and holds a message with throwable.
     *
     * @param message   message of this result.
     * @param throwable what caused this fail.
     * @return FAIL Resultable with message and throwable.
     */
    public static Resultable fail(@NotNull String message, @NotNull Throwable throwable) {
        return Resulters.resulter().fail(message, throwable);
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#FAIL} and holds a throwable.
     * Message from {@link Resultable#message()} is taken from the throwable.
     *
     * @param throwable what caused this fail.
     * @return FAIL Resultable with throwable.
     */
    public static Resultable fail(@NotNull Throwable throwable) {
        return Resulters.resulter().fail(throwable);
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#WARNING} and holds a message.
     *
     * @param message message of this result.
     * @return WARNING Resultable with message.
     */
    public static Resultable warning(@NotNull String message) {
        return Resulters.resulter().warning(message);
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#WARNING} and holds a message with throwable.
     *
     * @param message   message of this result.
     * @param throwable what caused this fail.
     * @return WARNING Resultable with message and throwable.
     */
    public static Resultable warning(@NotNull String message, @NotNull Throwable throwable) {
        return Resulters.resulter().warning(message, throwable);
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#WARNING} and holds a throwable.
     * Message from {@link Resultable#message()} is taken from the throwable.
     *
     * @param throwable what caused this fail.
     * @return WARNING Resultable with throwable.
     */
    public static Resultable warning(@NotNull Throwable throwable) {
        return Resulters.resulter().warning(throwable);
    }


    /**
     * @param input input
     * @return {@link Mono} with it's data wrapped into a {@link Resultable}
     * @see SimpleResult#mono(Supplier)
     */
    public static Mono<Resultable> mono(@NotNull Mono<?> input) {
        return mono(() -> input);
    }

    /**
     * Creates new {@link Mono#defer(Supplier)} and wraps it into a {@link Resultable}.
     * If the input results empty, {@link DataResult#fail(String)} is returned.
     * <p>
     * Error handling is also done, if any error is thrown, {@link DataResult#fail(Throwable)} is returned.
     *
     * @param input input supplier
     * @return {@link Mono} with it's data wrapped into a {@link Resultable}
     */
    public static Mono<Resultable> mono(@NotNull Supplier<Mono<?>> input) {
        return Mono.defer(input)
                .map(next -> SimpleResult.ok())
                .onErrorResume(t -> Mono.just(SimpleResult.fail(t)))
                .switchIfEmpty(Mono.just(SimpleResult.fail("Mono ended empty.")));
    }

    /**
     * Creates new {@link Resultable} from provided values.
     *
     * @param status    status of this Resultable
     * @param message   message
     * @param throwable error
     * @return new Resultable from the values.
     */
    public static Resultable from(ResultStatus status, String message, Throwable throwable) {
        return Resulters.resulter().from(status, message, throwable);
    }

    /**
     * Tries to convert given {@link Object} into {@link Resultable}.
     *
     * @param input input
     * @return Resultable
     */
    public static Resultable convert(Object input) {
        return Resulters.resulter().convert(input);
    }
}
