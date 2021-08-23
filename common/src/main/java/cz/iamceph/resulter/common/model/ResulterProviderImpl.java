package cz.iamceph.resulter.common.model;

import cz.iamceph.resulter.common.SimpleResult;
import cz.iamceph.resulter.common.api.ResultStatus;
import cz.iamceph.resulter.common.api.Resultable;
import cz.iamceph.resulter.common.provider.ResulterProvider;

public class ResulterProviderImpl implements ResulterProvider {

    private ResulterProviderImpl() {
    }

    public static ResulterProvider get() {
        return new ResulterProviderImpl();
    }

    @Override
    public Resultable ok() {
        return new ResultableImpl(ResultStatus.OK, "Ok.", null);
    }

    @Override
    public Resultable ok(String message) {
        return new ResultableImpl(ResultStatus.OK, message, null);
    }

    @Override
    public Resultable fail(String message) {
        return new ResultableImpl(ResultStatus.FAIL, message, null);
    }

    @Override
    public Resultable fail(String message, Throwable throwable) {
        return new ResultableImpl(ResultStatus.FAIL, message, throwable);
    }

    @Override
    public Resultable fail(Throwable throwable) {
        return new ResultableImpl(ResultStatus.FAIL, null, throwable);
    }

    @Override
    public Resultable warning(String message) {
        return new ResultableImpl(ResultStatus.WARNING, message, null);
    }

    @Override
    public Resultable warning(String message, Throwable throwable) {
        return new ResultableImpl(ResultStatus.WARNING, message, throwable);
    }

    @Override
    public Resultable warning(Throwable throwable) {
        return new ResultableImpl(ResultStatus.WARNING, throwable.getMessage(), throwable);
    }

    @Override
    public Resultable convert(Object input) {
        return SimpleResult.fail("Not implemented yet.");
    }
}
