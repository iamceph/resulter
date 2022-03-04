package com.iamceph.resulter.core.api.provider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.iamceph.resulter.core.DataResultable;

/**
 * Service that provides {@link DataResultable}.
 */
public interface DataResulterProvider {
    /**
     * @param <T>  data type
     * @param data data to use
     * @return new resultable
     * @see DataResultable#ok(Object)
     */
    @NotNull <T> DataResultable<T> ok(@NotNull T data);

    /**
     * @param <T>     data type
     * @param data    data to use
     * @param message result message
     * @return new resultable
     * @see DataResultable#ok(Object, String)
     */
    @NotNull <T> DataResultable<T> ok(@NotNull T data, @NotNull  String message);

    /**
     * @param <T>     data type
     * @param message result message
     * @return new resultable
     * @see DataResultable#fail(String)
     */
    @NotNull <T> DataResultable<T> fail(@NotNull String message);

    /**
     * @param <T>     data type
     * @param data    data to use
     * @param message result message
     * @return new resultable
     * @see DataResultable#fail(Object, String)
     */
    @NotNull <T> DataResultable<T> fail(@Nullable T data, @NotNull String message);

    /**
     * @param <T>       data type
     * @param data      data to use
     * @param message   result message
     * @param throwable result error
     * @return new resultable
     * @see DataResultable#fail(Object, String, Throwable)
     */
    <T> DataResultable<T> fail(@Nullable T data, @NotNull String message, @Nullable Throwable throwable);

    /**
     * @param <T>       data type
     * @param message   result message
     * @param throwable result error
     * @return new resultable
     * @see DataResultable#fail(String, Throwable)
     */
    <T> DataResultable<T> fail(@NotNull String message, @Nullable Throwable throwable);

    /**
     * @param <T>       data type
     * @param throwable result error
     * @return new resultable
     * @see DataResultable#fail(Throwable)
     */
    <T> DataResultable<T> fail(@NotNull Throwable throwable);

    /**
     * @param <T>     data type
     * @param message result message
     * @return new resultable
     * @see DataResultable#warning(String)
     */
    <T> DataResultable<T> warning(@NotNull String message);

    /**
     * @param <T>     data type
     * @param data    data to use
     * @param message result message
     * @return new resultable
     * @see DataResultable#warning(Object, String)
     */
    <T> DataResultable<T> warning(@Nullable T data, @NotNull String message);

    /**
     * @param <T>       data type
     * @param data      data to use
     * @param message   result message
     * @param throwable result error
     * @return new resultable
     * @see DataResultable#warning(Object, String, Throwable)
     */
    <T> DataResultable<T> warning(@Nullable T data, @NotNull String message, @Nullable Throwable throwable);

    /**
     * @param <T>       data type
     * @param message   result message
     * @param throwable result error
     * @return new resultable
     * @see DataResultable#warning(String, Throwable)
     */
    <T> DataResultable<T> warning(@NotNull String message, @Nullable Throwable throwable);

    /**
     * @param <T>       data type
     * @param throwable result error
     * @return new resultable
     * @see DataResultable#warning(Throwable)
     */
    <T> DataResultable<T> warning(@NotNull Throwable throwable);
}
