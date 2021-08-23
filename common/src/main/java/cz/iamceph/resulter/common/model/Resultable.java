package cz.iamceph.resulter.common.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * Resultable for holding operation results.
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
     * Checks if this Result is OK.
     * @return true if the result is OK.
     */
    boolean isOk();

    /**
     * Checks if this Result is FAIL.
     * @return true if the result is FAIL.
     */
    boolean isFail();

    boolean isWarning();
}
