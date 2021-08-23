package cz.iamceph.resulter.common.api;

import java.io.Serializable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import cz.iamceph.resulter.common.DataResult;

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
     *
     * @return true if the result is OK.
     */
    boolean isOk();

    /**
     * Checks if this Result is FAIL.
     *
     * @return true if the result is FAIL.
     */
    boolean isFail();

    boolean isWarning();

    /**
     * Creates a {@link DataResultable} from this.
     *
     * @param <T> type
     * @return Failed DataResultable. If this Resultable is OK, fails anyways, because no data are present.
     */
    <T> DataResultable<T> asData();

    /**
     * Creates a {@link DataResultable} from this.
     *
     * @param <T> type
     * @return OK DataResult. If given data are null, fails.
     */
    <T> DataResultable<T> asData(@Nullable T data);
}
