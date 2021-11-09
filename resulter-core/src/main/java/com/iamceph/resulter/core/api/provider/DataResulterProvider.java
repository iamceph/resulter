package com.iamceph.resulter.core.api.provider;

import com.iamceph.resulter.core.DataResultable;

/**
 * Service that provides {@link DataResultable}.
 */
public interface DataResulterProvider {
    /**
     * @see DataResultable#ok(Object)
     */
    <T> DataResultable<T> ok(T data);

    /**
     * @see DataResultable#ok(Object, String)
     */
    <T> DataResultable<T> ok(T data, String message);

    /**
     * @see DataResultable#fail(String)
     */
    <T> DataResultable<T> fail(String message);

    /**
     * @see DataResultable#fail(Object, String)
     */
    <T> DataResultable<T> fail(T data, String message);

    /**
     * @see DataResultable#fail(Object, String, Throwable)
     */
    <T> DataResultable<T> fail(T data, String message, Throwable throwable);

    /**
     * @see DataResultable#fail(String, Throwable)
     */
    <T> DataResultable<T> fail(String message, Throwable throwable);

    /**
     * @see DataResultable#fail(Throwable)
     */
    <T> DataResultable<T> fail(Throwable throwable);

    /**
     * @see DataResultable#warning(String)
     */
    <T> DataResultable<T> warning(String message);

    /**
     * @see DataResultable#warning(Object, String)
     */
    <T> DataResultable<T> warning(T data, String message);

    /**
     * @see DataResultable#warning(Object, String, Throwable)
     */
    <T> DataResultable<T> warning(T data, String message, Throwable throwable);

    /**
     * @see DataResultable#warning(String, Throwable)
     */
    <T> DataResultable<T> warning(String message, Throwable throwable);

    /**
     * @see DataResultable#warning(Throwable)
     */
    <T> DataResultable<T> warning(Throwable throwable);
}
