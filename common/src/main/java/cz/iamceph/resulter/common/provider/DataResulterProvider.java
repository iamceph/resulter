package cz.iamceph.resulter.common.provider;

import cz.iamceph.resulter.common.DataResult;
import cz.iamceph.resulter.common.SimpleResult;
import cz.iamceph.resulter.common.api.DataResultable;
import cz.iamceph.resulter.common.api.Resultable;

public interface DataResulterProvider {
    <T> DataResultable<T> ok(T data);

    <T> DataResultable<T> ok(T data, String message);

    <T> DataResultable<T> fail(String message);

    <T> DataResultable<T> fail(T data, String message);

    <T> DataResultable<T> fail(T data, String message, Throwable throwable);

    <T> DataResultable<T> fail(String message, Throwable throwable);

    <T> DataResultable<T> fail(Throwable throwable);

    <T> DataResultable<T> warning(String message);

    <T> DataResultable<T> warning(T data, String message);

    <T> DataResultable<T> warning(T data, String message, Throwable throwable);

    <T> DataResultable<T> warning(String message, Throwable throwable);

    <T> DataResultable<T> warning(Throwable throwable);
}
