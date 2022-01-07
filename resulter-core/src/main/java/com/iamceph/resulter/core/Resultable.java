package com.iamceph.resulter.core;

import java.io.Serializable;
import java.util.function.Supplier;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.iamceph.resulter.core.api.ResultStatus;
import com.iamceph.resulter.core.model.ProtoResultable;
import com.iamceph.resulter.core.model.Resulters;

import reactor.core.publisher.Mono;

/**
 * The Resultable API.
 */
public interface Resultable extends Serializable, Cloneable {

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#OK} with no message.
     *
     * @return OK Resultable.
     */
    static Resultable ok() {
        return Resulters.resulter().ok();
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#OK} and holds a message.
     *
     * @param message message of this result.
     * @return OK Resultable with message.
     */
    static Resultable ok(@NotNull String message) {
        return Resulters.resulter().ok(message);
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#FAIL} and holds a message.
     *
     * @param message message of this result.
     * @return FAIL Resultable with message.
     */
    static Resultable fail(@NotNull String message) {
        return Resulters.resulter().fail(message);
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#FAIL} and holds a message with throwable.
     *
     * @param message   message of this result.
     * @param throwable what caused this fail.
     * @return FAIL Resultable with message and throwable.
     */
    static Resultable fail(@NotNull String message, @NotNull Throwable throwable) {
        return Resulters.resulter().fail(message, throwable);
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#FAIL} and holds a throwable.
     * Message from {@link Resultable#message()} is taken from the throwable.
     *
     * @param throwable what caused this fail.
     * @return FAIL Resultable with throwable.
     */
    static Resultable fail(@NotNull Throwable throwable) {
        return Resulters.resulter().fail(throwable);
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#WARNING} and holds a message.
     *
     * @param message message of this result.
     * @return WARNING Resultable with message.
     */
    static Resultable warning(@NotNull String message) {
        return Resulters.resulter().warning(message);
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#WARNING} and holds a message with throwable.
     *
     * @param message   message of this result.
     * @param throwable what caused this fail.
     * @return WARNING Resultable with message and throwable.
     */
    static Resultable warning(@NotNull String message, @NotNull Throwable throwable) {
        return Resulters.resulter().warning(message, throwable);
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#WARNING} and holds a throwable.
     * Message from {@link Resultable#message()} is taken from the throwable.
     *
     * @param throwable what caused this fail.
     * @return WARNING Resultable with throwable.
     */
    static Resultable warning(@NotNull Throwable throwable) {
        return Resulters.resulter().warning(throwable);
    }


    /**
     * @param input input
     * @return {@link Mono} with it's data wrapped into a {@link Resultable}
     * @see Resultable#mono(Supplier)
     */
    static Mono<Resultable> mono(@NotNull Mono<?> input) {
        return mono(() -> input);
    }

    /**
     * Creates new {@link Mono#defer(Supplier)} and wraps it into a {@link Resultable}.
     * If the input results empty, {@link DataResultable#fail(String)} is returned.
     * <p>
     * Error handling is also done, if any error is thrown, {@link DataResultable#fail(Throwable)} is returned.
     *
     * @param input input supplier
     * @return {@link Mono} with it's data wrapped into a {@link Resultable}
     */
    static Mono<Resultable> mono(@NotNull Supplier<Mono<?>> input) {
        return Mono.defer(input)
                .map(next -> Resultable.ok())
                .onErrorResume(t -> Mono.just(Resultable.fail(t)))
                .switchIfEmpty(Mono.just(Resultable.fail("Mono ended empty.")));
    }

    /**
     * Creates new {@link Resultable} from provided values.
     *
     * @param status    status of this Resultable
     * @param message   message
     * @param throwable error
     * @return new Resultable from the values.
     */
    static Resultable from(ResultStatus status, String message, Throwable throwable) {
        return Resulters.resulter().from(status, message, throwable);
    }

    /**
     * Tries to convert given {@link Object} into {@link Resultable}.
     *
     * @param input input
     * @return Resultable
     */
    static Resultable convert(Object input) {
        return Resulters.resulter().convert(input);
    }

    /**
     * Transforms {@link Resultable} into {@link DataResultable}.
     *
     * @param data data, can be null,
     * @param <T>  type of the data,
     * @return Transformed DataResultable.
     */
    default <T> DataResultable<T> transform(@Nullable T data) {
        if (data == null && isOk()) {
            return DataResultable.fail("No data provided.");
        }

        switch (status()) {
            case OK:
                return Resulters.dataResulter().ok(data, message());
            case WARNING:
                return Resulters.dataResulter().warning(data, message(), error());
            default:
                return Resulters.dataResulter().fail(data, message(), error());

        }
    }

    /**
     * Transforms {@link Resultable} into {@link DataResultable}.
     * Error handling is provided.
     *
     * @param <T> type
     * @return cast Resultable. Love generics.
     */
    @SuppressWarnings("unchecked")
    default <T> DataResultable<T> transform() {
        if (this instanceof DataResultable) {
            try {
                return (DataResultable<T>) this;
            } catch (Throwable t) {
                return DataResultable.fail("transform() failed because of Throwable.", t);
            }
        }

        return transform(null);
    }

    /**
     * For mappings or other stuff only.
     *
     * @return {@link Resultable#message()}
     */
    @ApiStatus.Internal
    default String getMessage() {
        return message();
    }

    /**
     * @return current state of this Result.
     */
    @NotNull ResultStatus status();

    /**
     * @return message for this result, null if not defined.
     */
    @NotNull String message();

    /**
     * @return error for this result, null if not defined.
     */
    @Nullable Throwable error();

    /**
     * Check if the Resultable is {@link ResultStatus#OK}
     *
     * @return true if this Resultable is in state {@link ResultStatus#OK}
     */
    boolean isOk();

    /**
     * Check if the Resultable is {@link ResultStatus#FAIL}
     *
     * @return true if this Resultable is in state {@link ResultStatus#FAIL}
     */
    boolean isFail();

    /**
     * Check if the Resultable is {@link ResultStatus#WARNING}
     *
     * @return true if this Resultable is in state {@link ResultStatus#WARNING}
     */
    boolean isWarning();

    /**
     * @return Convertor instance. Depends on the implementation.
     */
    Convertor convertor();

    /**
     * Convertor that converts from/to {@link Resultable}.
     */
    interface Convertor {
        /**
         * Converts this {@link Resultable} into a JSON string.
         *
         * @return JSON string.
         */
        String json();

        /**
         * Converts this {@link Resultable} into other Object.
         *
         * @param target Class of the target
         * @param <K>    type of the target
         * @return converted {@link Resultable}
         */
        <K> K convert(Class<K> target);

        /**
         * Converts this Resultable info gRPC generated {@link ProtoResultable}.
         *
         * @return {@link ProtoResultable} for gRPC.
         */
        default ProtoResultable grpc() {
            return convert(ProtoResultable.class);
        }
    }
}
