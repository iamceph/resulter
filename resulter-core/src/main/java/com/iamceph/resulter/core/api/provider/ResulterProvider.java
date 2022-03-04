package com.iamceph.resulter.core.api.provider;

import org.jetbrains.annotations.NotNull;

import com.iamceph.resulter.core.api.ResultStatus;
import com.iamceph.resulter.core.Resultable;

/**
 * Service that provides {@link Resultable}.
 */
public interface ResulterProvider {
    /**
     * @see Resultable#ok()
     */
    @NotNull
    Resultable ok();

    /**
     * @see Resultable#ok(String)
     */
    @NotNull
    Resultable ok(@NotNull String message);

    /**
     * @see Resultable#fail(String)
     */
    @NotNull
    Resultable fail(@NotNull String message);

    /**
     * @see Resultable#fail(String, Throwable)
     */
    @NotNull
    Resultable fail(@NotNull String message, Throwable throwable);

    /**
     * @see Resultable#fail(Throwable)
     */
    @NotNull
    Resultable fail(Throwable throwable);

    /**
     * @see Resultable#warning(String)
     */
    @NotNull
    Resultable warning(@NotNull String message);

    /**
     * @see Resultable#warning(String, Throwable)
     */
    @NotNull
    Resultable warning(@NotNull String message, Throwable throwable);

    /**
     * @see Resultable#warning(Throwable)
     */
    @NotNull
    Resultable warning(Throwable throwable);

    /**
     * @see Resultable#from(ResultStatus, String, Throwable)
     */
    @NotNull
    Resultable from(@NotNull ResultStatus status, @NotNull String message, Throwable throwable);

    /**
     * @see Resultable
     */
    @NotNull
    Resultable convert(Object input);
}
