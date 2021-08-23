package cz.iamceph.resulter.common.model;

import org.jetbrains.annotations.Nullable;

import cz.iamceph.resulter.common.DataResult;
import cz.iamceph.resulter.common.api.DataResultable;
import cz.iamceph.resulter.common.api.ResultStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DataResultableImpl<T> implements DataResultable<T> {
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

    @Override
    public <K> DataResultable<K> asData() {
        return null;
    }

    @Override
    public <K> DataResultable<K> asData(@Nullable K data) {
        return null;
    }
}
