package cz.iamceph.resulter.common.provider;

import cz.iamceph.resulter.common.api.Resultable;

public interface ResulterProvider {
    Resultable ok();

    Resultable ok(String message);

    Resultable fail(String message);

    Resultable fail(String message, Throwable throwable);

    Resultable fail(Throwable throwable);

    Resultable warning(String message);

    Resultable warning(String message, Throwable throwable);

    Resultable warning(Throwable throwable);

    Resultable convert(Object input);
}
