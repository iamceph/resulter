package com.iamceph.resulter.core.api;

import lombok.Getter;

/**
 * Status of given {@link Resultable}.
 */
public enum ResultStatus {
    /**
     * Result failed. The reason is either in the {@link Resultable#message()} or {@link Resultable#error()}.
     */
    FAIL(0),
    /**
     * Result is OK.
     * OK means that given {@link Resultable} ended OK and nothing is wrong. Duh?
     */
    OK(1),
    /**
     * Warning means that given operation did not finish as expected, but it is not critical a critical error.
     */
    WARNING(2);

    /**
     * Number value of this {@link Resultable}. Useful for converting to gRPC for example :)
     */
    @Getter
    private final Integer numberValue;

    ResultStatus(Integer numberValue) {
        this.numberValue = numberValue;
    }
}
