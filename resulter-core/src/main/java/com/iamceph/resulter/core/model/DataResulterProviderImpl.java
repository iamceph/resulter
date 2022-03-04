package com.iamceph.resulter.core.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.iamceph.resulter.core.DataResultable;
import com.iamceph.resulter.core.api.ResultStatus;
import com.iamceph.resulter.core.api.provider.DataResulterProvider;

/**
 * Default implementation of DataResulterProvider.
 */
final class DataResulterProviderImpl implements DataResulterProvider {

    /**
     * Non accessible default constructor.
     */
    private DataResulterProviderImpl() {
    }

    static DataResulterProvider get() {
        return new DataResulterProviderImpl();
    }

    @Override
    public @NotNull <T> DataResultable<T> ok(@NotNull T data) {
        return ok(data, "Ok.");
    }

    @Override
    public @NotNull <T> DataResultable<T> ok(@NotNull T data, @NotNull String message) {
        return new DataResultableImpl<>(ResultStatus.OK, resolveMessage(message), null, data);
    }

    @Override
    public @NotNull <T> DataResultable<T> fail(@NotNull String message) {
        return fail(null, message);
    }

    @Override
    public <T> DataResultable<T> fail(@NotNull Throwable throwable) {
        return fail(null, resolveMessage(throwable), throwable);
    }

    @Override
    public @NotNull <T> DataResultable<T> fail(T data, @NotNull String message) {
        return fail(data, message, null);
    }

    @Override
    public <T> DataResultable<T> fail(T data, @NotNull String message, Throwable throwable) {
        return new DataResultableImpl<>(ResultStatus.FAIL, resolveMessage(message), throwable, data);
    }

    @Override
    public <T> DataResultable<T> fail(@NotNull String message, Throwable throwable) {
        return fail(null, message, throwable);
    }

    @Override
    public <T> DataResultable<T> warning(@NotNull String message) {
        return warning(null, message);
    }

    @Override
    public <T> DataResultable<T> warning(@NotNull Throwable throwable) {
        return warning(null, resolveMessage(throwable), throwable);
    }

    @Override
    public <T> DataResultable<T> warning(T data, @NotNull String message) {
        return warning(data, message, null);
    }

    @Override
    public <T> DataResultable<T> warning(T data, @NotNull String message, @Nullable Throwable throwable) {
        return new DataResultableImpl<>(ResultStatus.WARNING, resolveMessage(message), throwable, data);
    }

    @Override
    public <T> DataResultable<T> warning(@NotNull String message, @Nullable Throwable throwable) {
        return new DataResultableImpl<>(ResultStatus.WARNING, resolveMessage(message), throwable, null);
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
