package cz.iamceph.resulter.common.model;

import org.jetbrains.annotations.Nullable;

import cz.iamceph.resulter.common.Resulters;
import cz.iamceph.resulter.common.SimpleResult;
import cz.iamceph.resulter.common.api.DataResultable;
import cz.iamceph.resulter.common.api.ResultStatus;
import cz.iamceph.resulter.common.api.Resultable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ResultableImpl implements Resultable {
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

    @Override
    public <T> DataResultable<T> asData() {
        return asData(null);
    }

    @Override
    public <T> DataResultable<T> asData(@Nullable T data) {
        if (data == null && status() != ResultStatus.OK) {
            if (status() == ResultStatus.FAIL) {
                return Resulters.DATA_RESULTER().fail(message(), error());
            }

            return Resulters.DATA_RESULTER().warning(message(), error());
        }

        switch (status()) {
            case OK:
                return Resulters.DATA_RESULTER().ok(data, message());
            case WARNING:
                return Resulters.DATA_RESULTER().warning(data, message(), error());
            default:
                return Resulters.DATA_RESULTER().fail(data, message(), error());
        }
    }
}
