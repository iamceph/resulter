package cz.iamceph.resulter.common.model;

import cz.iamceph.resulter.common.Resulters;
import cz.iamceph.resulter.common.api.ResultStatus;
import cz.iamceph.resulter.common.api.Resultable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Default implementation of the {@link Resultable}
 */
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ResultableImpl implements Resultable {
    final ResultStatus status;
    final String message;
    final Throwable error;

    /**
     * @see Resultable#isOk()
     */
    @Override
    public boolean isOk() {
        return status == ResultStatus.OK;
    }

    /**
     * @see Resultable#isFail()
     */
    @Override
    public boolean isFail() {
        return status == ResultStatus.FAIL;
    }

    /**
     * @see Resultable#isWarning()
     */
    @Override
    public boolean isWarning() {
        return status == ResultStatus.WARNING;
    }

    /**
     * @see Resultable#convertor()
     */
    @Override
    public Convertor convertor() {
        return new Convertor() {
            @Override
            public String json() {
                return Resulters.CONVERTOR().json(ResultableImpl.this);
            }

            @Override
            public <K> K convert(Class<K> target) {
                return (K) Resulters.CONVERTOR().convert(ResultableImpl.this, target);
            }
        };
    }
}
