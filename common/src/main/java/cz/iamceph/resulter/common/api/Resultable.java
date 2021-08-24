package cz.iamceph.resulter.common.api;

import java.io.Serializable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The Resultable API.
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
     * Check if the Resultable is {@link ResultStatus#OK}
     *
     * @return true if this Resultable is in state {@link ResultStatus#OK}
     */
    boolean isOk();

    /**
     * Check if the Resultable is {@link ResultStatus#FAIL}
     *
     * @return true if this Resultable is in state {@link ResultStatus#FAIL}
     */
    boolean isFail();

    /**
     * Check if the Resultable is {@link ResultStatus#WARNING}
     *
     * @return true if this Resultable is in state {@link ResultStatus#WARNING}
     */
    boolean isWarning();

    Convertor convertor();

    interface Convertor {
        String json();

        <K> K convert(Class<K> target);
    }
}
