package cz.iamceph.resulter.common.model;

import lombok.Getter;

/**
 * Current status of the Result.
 */
public enum ResultStatus {
    /**
     * Result is OK.
     * That means that any operation with the Result itself is safe.
     */
    OK(0),
    /**
     * Result failed.
     */
    FAIL(1),
    /**
     * This represents a state when result did not fail but also did not end OK.
     */
    WARNING(-1);

    @Getter
    private final Integer numberValue;

    ResultStatus(Integer numberValue) {
        this.numberValue = numberValue;
    }
}
