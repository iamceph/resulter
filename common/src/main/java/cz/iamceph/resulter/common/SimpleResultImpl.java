package cz.iamceph.resulter.common;

import cz.iamceph.resulter.common.model.ResultStatus;
import cz.iamceph.resulter.common.model.Resultable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.xml.transform.Result;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SimpleResultImpl extends SimpleResult {
    final ResultStatus status;
    final String message;
    final Throwable error;

    @Override
    public boolean isOk() {
        return status == ResultStatus.OK;
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
