package cz.iamceph.resulter.common;

public abstract class AbstractResulter {
    abstract SimpleResult ok();

    abstract SimpleResult ok(String message);

    abstract SimpleResult fail(String message);

    abstract SimpleResult fail(String message, Throwable throwable);

    abstract SimpleResult fail(Throwable throwable);

    abstract SimpleResult warn();

    abstract SimpleResult warn(String message);

    abstract SimpleResult convert(Object input);

    abstract <T> DataResult<T> asData(SimpleResult input, T data);
}
