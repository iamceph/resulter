package cz.iamceph.resulter.common.model;

import cz.iamceph.resulter.common.api.DataResultable;
import cz.iamceph.resulter.common.api.ResultStatus;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
class DataResultableImpl<T> extends ResultableImpl implements DataResultable<T> {
    final T data;

    DataResultableImpl(ResultStatus status, String message, Throwable error, T data) {
        super(status, message, error);
        this.data = data;
    }

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
