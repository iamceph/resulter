package com.iamceph.resulter.core.api;

import com.iamceph.resulter.core.DataResult;
import com.iamceph.resulter.core.model.Result;
import com.iamceph.resulter.core.model.Resulters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * The Resultable API.
 */
public interface Resultable extends Serializable, Cloneable {
    /**
     * @return current state of this Result.
     */
    @NotNull ResultStatus status();

    /**
     * @return message for this result, null if not defined.
     */
    @Nullable String message();

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
         * Converts this Resultable info gRPC generated {@link Result}.
         * @return {@link Result} for gRPC.
         */
        default Result grpc() {
            return convert(Result.class);
        }
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
            return DataResult.fail("No data provided.");
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
                return DataResult.fail("transform() failed because of Throwable.", t);
            }
        }

        return transform(null);
    }
}
