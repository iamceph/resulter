package com.iamceph.resulter.core.api.provider;

import com.iamceph.resulter.core.api.ResultStatus;
import com.iamceph.resulter.core.Resultable;

/**
 * Service that provides {@link Resultable}.
 */
public interface ResulterProvider {
    /**
     * @see Resultable#ok()
     */
    Resultable ok();

    /**
     * @see Resultable#ok(String) 
     */
    Resultable ok(String message);

    /**
     * @see Resultable#fail(String) 
     */
    Resultable fail(String message);

    /**
     * @see Resultable#fail(String, Throwable) 
     */
    Resultable fail(String message, Throwable throwable);

    /**
     * @see Resultable#fail(Throwable) 
     */
    Resultable fail(Throwable throwable);

    /**
     * @see Resultable#warning(String) 
     */
    Resultable warning(String message);

    /**
     * @see Resultable#warning(String, Throwable) 
     */
    Resultable warning(String message, Throwable throwable);

    /**
     * @see Resultable#warning(Throwable)
     */
    Resultable warning(Throwable throwable);

    /**
     * @see Resultable#from(ResultStatus, String, Throwable)
     */
    Resultable from(ResultStatus status, String message, Throwable throwable);

    /**
     * @see Resultable
     */
    Resultable convert(Object input);
}
