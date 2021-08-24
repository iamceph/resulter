package cz.iamceph.resulter.common.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

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

    /**
     * @return Convertor instance. Depends on the implementation.
     */
    Convertor convertor();

    interface Convertor {
        /**
         * Converts this {@link Resultable} into a JSON string.
         *
         * @return JSON string.
         */
        String json();

        /**
         * Converts this {@link Resultable} into other Object.
         *
         * @param target Class of the target
         * @param <K>    type of the target
         * @return converted {@link Resultable}
         */
        <K> K convert(Class<K> target);
    }
}
