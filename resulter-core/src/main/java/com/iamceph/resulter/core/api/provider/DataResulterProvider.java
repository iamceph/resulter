package com.iamceph.resulter.core.api.provider;

import com.iamceph.resulter.core.DataResult;
import com.iamceph.resulter.core.api.DataResultable;

/**
 * Service that provides {@link DataResultable}.
 */
public interface DataResulterProvider {
    /**
     * @see DataResult#ok(Object)
     */
    <T> DataResultable<T> ok(T data);

    /**
     * @see DataResult#ok(Object, String)
     */
    <T> DataResultable<T> ok(T data, String message);

    /**
     * @see DataResult#fail(String)
     */
    <T> DataResultable<T> fail(String message);

    /**
     * @see DataResult#fail(Object, String)
     */
    <T> DataResultable<T> fail(T data, String message);

    /**
     * @see DataResult#fail(Object, String, Throwable)
     */
    <T> DataResultable<T> fail(T data, String message, Throwable throwable);

    /**
     * @see DataResult#fail(String, Throwable)
     */
    <T> DataResultable<T> fail(String message, Throwable throwable);

    /**
     * @see DataResult#fail(Throwable)
     */
    <T> DataResultable<T> fail(Throwable throwable);

    /**
     * @see DataResult#warning(String)
     */
    <T> DataResultable<T> warning(String message);

    /**
     * @see DataResult#warning(Object, String)
     */
    <T> DataResultable<T> warning(T data, String message);

    /**
     * @see DataResult#warning(Object, String, Throwable)
     */
    <T> DataResultable<T> warning(T data, String message, Throwable throwable);

    /**
     * @see DataResult#warning(String, Throwable)
     */
    <T> DataResultable<T> warning(String message, Throwable throwable);

    /**
     * @see DataResult#warning(Throwable)
     */
    <T> DataResultable<T> warning(Throwable throwable);
}
