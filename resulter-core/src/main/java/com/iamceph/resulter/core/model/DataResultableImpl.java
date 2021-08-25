package com.iamceph.resulter.core.model;

import com.iamceph.resulter.core.api.DataResultable;
import com.iamceph.resulter.core.api.ResultStatus;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Default implementation of the {@link DataResultable}.
 *
 * @param <T> type of the data
 * @see DataResultable
 */
@EqualsAndHashCode(callSuper = false)
@ToString
@Getter
@Accessors(fluent = true)
class DataResultableImpl<T> extends ResultableImpl implements DataResultable<T> {
    final T data;

    DataResultableImpl(ResultStatus status, String message, Throwable error, T data) {
        super(status, message, error);
        this.data = data;
    }

    /**
     * @see DataResultable#hasData()
     */
    @Override
    public boolean hasData() {
        return data != null;
    }

    /**
     * @see DataResultable#isOk()
     */
    @Override
    public boolean isOk() {
        return status == ResultStatus.OK && hasData();
    }

    /**
     * @see DataResultable#isFail()
     */
    @Override
    public boolean isFail() {
        return status == ResultStatus.FAIL;
    }

    /**
     * @see DataResultable#isWarning()
     */
    @Override
    public boolean isWarning() {
        return status == ResultStatus.WARNING;
    }
}
