package cz.iamceph.resulter.common;

import cz.iamceph.resulter.common.model.ResultStatus;
import cz.iamceph.resulter.common.model.Resultable;

class ResulterImpl extends AbstractResulter {
    @Override
    SimpleResult ok() {
        return new SimpleResultImpl(ResultStatus.OK, "Ok.", null);
    }

    @Override
    SimpleResult ok(String message) {
        return new SimpleResultImpl(ResultStatus.OK, message, null);
    }

    @Override
    SimpleResult fail(String message) {
        return new SimpleResultImpl(ResultStatus.FAIL, message, null);
    }

    @Override
    SimpleResult fail(String message, Throwable throwable) {
        return new SimpleResultImpl(ResultStatus.FAIL, message, throwable);
    }

    @Override
    SimpleResult fail(Throwable throwable) {
        return new SimpleResultImpl(ResultStatus.FAIL, null, throwable);
    }

    @Override
    SimpleResult warn() {
        return new SimpleResultImpl(ResultStatus.WARNING, "Warning!", null);
    }

    @Override
    SimpleResult warn(String message) {
        return new SimpleResultImpl(ResultStatus.WARNING, message, null);
    }

    @Override
    SimpleResult convert(Object input) {
        return SimpleResult.fail("Not implemented yet.");
    }

    @Override
    <T> DataResult<T> asData(SimpleResult input, T data) {
        return null;
    }
}
