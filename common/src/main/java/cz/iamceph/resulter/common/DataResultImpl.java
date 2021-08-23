package cz.iamceph.resulter.common;

import cz.iamceph.resulter.common.model.DataResultable;
import cz.iamceph.resulter.common.model.ResultStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DataResultImpl<T> extends DataResult<T> {
    final ResultStatus status;
    final String message;
    final Throwable error;
    final T data;

    @Override
    public boolean hasData() {
        return data != null;
    }

    @Override
    public boolean isOk() {
        return status == ResultStatus.OK && hasData();
    }

    @Override
    public boolean isFail() {
        return status == ResultStatus.FAIL;
    }

    @Override
    public boolean isWarning() {
        return status == ResultStatus.WARNING;
    }
}
