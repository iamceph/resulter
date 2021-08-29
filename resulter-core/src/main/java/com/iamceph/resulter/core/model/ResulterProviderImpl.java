package com.iamceph.resulter.core.model;

import com.iamceph.resulter.core.api.ResultStatus;
import com.iamceph.resulter.core.api.Resultable;
import com.iamceph.resulter.core.api.provider.ResulterProvider;

final class ResulterProviderImpl implements ResulterProvider {

    private ResulterProviderImpl() {
    }

    static ResulterProvider get() {
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
        return new ResultableImpl(ResultStatus.FAIL, throwable.getMessage(), throwable);
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
    public Resultable from(ResultStatus status, String message, Throwable throwable) {
        return new ResultableImpl(status, message, throwable);
    }

    @Override
    public Resultable convert(Object input) {
        return Resulters.convertor().convert(input);
    }
}
