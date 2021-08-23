package cz.iamceph.resulter.common;

import cz.iamceph.resulter.common.api.DataResultable;
import reactor.core.publisher.Mono;

public abstract class DataResult {

    public static <T> DataResultable<T> ok(T data) {
        return Resulters.DATA_RESULTER().ok(data);
    }

    public static <T> DataResultable<T> ok(String message, T data) {
        return Resulters.DATA_RESULTER().ok(data);
    }

    public static <T> DataResultable<T> fail(String message) {
        return Resulters.DATA_RESULTER().fail(message);
    }

    public static <T> DataResultable<T> fail(Throwable throwable) {
        return Resulters.DATA_RESULTER().fail(throwable);
    }

    public static <T> DataResultable<T> fail(T data, String message) {
        return Resulters.DATA_RESULTER().fail(data, message);
    }

    public static <T> DataResultable<T> fail(T data, String message, Throwable throwable) {
        return Resulters.DATA_RESULTER().fail(data, message, throwable);
    }

    public static <T> DataResultable<T> fail(String message, Throwable throwable) {
        return Resulters.DATA_RESULTER().fail(message, throwable);
    }


    public static <T> DataResultable<T> warning(String message) {
        return Resulters.DATA_RESULTER().warning(message);
    }

    public static <T> DataResultable<T> warning(Throwable throwable) {
        return Resulters.DATA_RESULTER().warning(throwable);
    }

    public static <T> DataResultable<T> warning(T data, String message) {
        return Resulters.DATA_RESULTER().warning(data, message);
    }

    public static <T> DataResultable<T> warning(T data, String message, Throwable throwable) {
        return Resulters.DATA_RESULTER().warning(data, message, throwable);
    }

    public static <T> DataResultable<T> warning(String message, Throwable throwable) {
        return Resulters.DATA_RESULTER().warning(message, throwable);
    }

    public static <T> DataResultable<T> failIfNull(T data) {
        return failIfNull(data, "No data provided.");
    }

    public static <T> DataResultable<T> failIfNull(T data, String message) {
        if (data == null) {
            return DataResult.fail(message);
        }
        return DataResult.ok(data);
    }

    public static <T> Mono<DataResultable<T>> mono(Mono<T> input) {
        return Mono.defer(() -> input)
                .map(DataResult::ok)
                .onErrorResume(ex -> Mono.just(DataResult.fail(ex)));
    }
}
