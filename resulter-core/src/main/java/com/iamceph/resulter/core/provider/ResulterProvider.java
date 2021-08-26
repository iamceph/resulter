package com.iamceph.resulter.core.provider;

import com.iamceph.resulter.core.SimpleResult;
import com.iamceph.resulter.core.api.Resultable;

/**
 * Service that provides {@link Resultable}.
 */
public interface ResulterProvider {
    /**
     * @see SimpleResult#ok()
     */
    Resultable ok();

    /**
     * @see SimpleResult#ok(String) 
     */
    Resultable ok(String message);

    /**
     * @see SimpleResult#fail(String) 
     */
    Resultable fail(String message);

    /**
     * @see SimpleResult#fail(String, Throwable) 
     */
    Resultable fail(String message, Throwable throwable);

    /**
     * @see SimpleResult#fail(Throwable) 
     */
    Resultable fail(Throwable throwable);

    /**
     * @see SimpleResult#warning(String) 
     */
    Resultable warning(String message);

    /**
     * @see SimpleResult#warning(String, Throwable) 
     */
    Resultable warning(String message, Throwable throwable);

    /**
     * @see SimpleResult#warning(Throwable)
     */
    Resultable warning(Throwable throwable);

    /**
     * @see SimpleResult
     */
    Resultable convert(Object input);
}
