package com.iamceph.resulter.core.api;

import java.util.Arrays;
import java.util.List;

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

    private final static List<ResultStatus> VALUES = Arrays.asList(values());

    /**
     * Number value of this {@link Resultable}. Useful for converting to gRPC for example :)
     */
    @Getter
    private final int numberValue;

    ResultStatus(int numberValue) {
        this.numberValue = numberValue;
    }

    public static ResultStatus fromNumber(int input) {
        return VALUES.stream()
                .filter(next -> next.getNumberValue() == input)
                .findFirst()
                .orElse(ResultStatus.FAIL);
    }
}
