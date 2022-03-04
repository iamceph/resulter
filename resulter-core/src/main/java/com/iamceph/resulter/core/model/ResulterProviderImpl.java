package com.iamceph.resulter.core.model;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.iamceph.resulter.core.Resultable;
import com.iamceph.resulter.core.api.ResultStatus;
import com.iamceph.resulter.core.api.provider.ResulterProvider;

/**
 * Default implementation of ResulterProvider.
 */
final class ResulterProviderImpl implements ResulterProvider {

    /**
     * Non accessible default constructor.
     */
    private ResulterProviderImpl() {
    }

    /**
     * Package-private way to get this ResulterProvider.
     *
     * @return new instance of ResulterProvider.
     */
    static ResulterProvider get() {
        return new ResulterProviderImpl();
    }

    /**
     * Creates new result.
     *
     * @return new {@link Resultable}
     */
    @Override
    public @NotNull Resultable ok() {
        return ok("Ok.");
    }

    /**
     * Creates new result.
     *
     * @param message message to use
     * @return new {@link Resultable}
     */
    @Override
    public @NotNull Resultable ok(@NotNull String message) {
        return new ResultableImpl(ResultStatus.OK, resolveMessage(message), null);
    }

    /**
     * Creates new result.
     *
     * @param message message to use
     * @return new {@link Resultable}
     */
    @Override
    public @NotNull Resultable fail(@NotNull String message) {
        return fail(message, null);
    }

    /**
     * Creates new result.
     *
     * @param message   message to use
     * @param throwable error to use
     * @return new {@link Resultable}
     */
    @Override
    public @NotNull Resultable fail(@NotNull String message, Throwable throwable) {
        return new ResultableImpl(ResultStatus.FAIL, resolveMessage(message), throwable);
    }

    /**
     * Creates new result.
     *
     * @param throwable error to use
     * @return new {@link Resultable}
     */
    @Override
    public @NotNull Resultable fail(Throwable throwable) {
        return fail(resolveMessage(throwable), throwable);
    }

    /**
     * Creates new result.
     *
     * @param message message to use
     * @return new {@link Resultable}
     */
    @Override
    public @NotNull Resultable warning(@NotNull String message) {
        return warning(resolveMessage(message), null);
    }

    /**
     * Creates new result.
     *
     * @param message   message to use
     * @param throwable error to use
     * @return new {@link Resultable}
     */
    @Override
    public @NotNull Resultable warning(@NotNull String message, Throwable throwable) {
        return new ResultableImpl(ResultStatus.WARNING, resolveMessage(message), throwable);
    }

    /**
     * Creates new result.
     *
     * @param throwable error to use
     * @return new {@link Resultable}
     */
    @Override
    public @NotNull Resultable warning(Throwable throwable) {
        return warning(resolveMessage(throwable), throwable);
    }

    /**
     * Creates new result from the values.
     *
     * @param status    status to use
     * @param message   message to use
     * @param throwable error to use
     * @return new {@link Resultable}
     */
    @Override
    public @NotNull Resultable from(@NotNull ResultStatus status, @NotNull String message, Throwable throwable) {
        Objects.requireNonNull(status, "Status cannot be null!");
        return new ResultableImpl(status, resolveMessage(message), throwable);
    }

    /**
     * Converts an object into Resultable.
     *
     * @param input input
     * @return new {@link Resultable}
     */
    @Override
    public @NotNull Resultable convert(Object input) {
        return Resulters.convertor().convert(input);
    }

    /**
     * Gets an error message or empty string if none present.
     *
     * @param throwable the throwable
     * @return message
     */
    private @NotNull String resolveMessage(@Nullable Throwable throwable) {
        if (throwable == null) {
            return "No error was supplied.";
        }

        return resolveMessage(throwable.getMessage());
    }

    /**
     * Gets an error message or empty string if none present.
     *
     * @param input input
     * @return message
     */
    private @NotNull String resolveMessage(@Nullable String input) {
        if (input == null) {
            return "";
        }
        return input;
    }
}
