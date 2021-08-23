package cz.iamceph.resulter.common.model;

import cz.iamceph.resulter.common.DataResult;
import cz.iamceph.resulter.common.SimpleResult;

import java.util.Optional;

/**
 * Resultable that can have data.
 * @param <T>
 */
public interface DataResultable<T> extends Resultable {

    boolean hasData();

    T data();

    @SuppressWarnings("unchecked")
    default  <K> DataResultable<K> transform() {
        return (DataResultable<K>) this;
    }

    default Optional<T> asOptional() {
        if (isFail() || isWarning()) {
            return Optional.empty();
        }
        return Optional.of(data());
    }

    default SimpleResult asSimple() {
        switch (this.status()) {
            case FAIL:
                return SimpleResult.fail(message());
            case OK:
                return SimpleResult.ok(message());
            default:
                return SimpleResult.warn(message());
        }
    }
}
