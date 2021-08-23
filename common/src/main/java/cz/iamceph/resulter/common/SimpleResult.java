package cz.iamceph.resulter.common;

import cz.iamceph.resulter.common.model.Resultable;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

public abstract class SimpleResult implements Resultable {
    
    protected SimpleResult() {
    }

    public static SimpleResult ok() {
        return Resulters.RESULTER().ok();
    }

    public static SimpleResult ok(String message) {
        return Resulters.RESULTER().ok(message);
    }

    public static SimpleResult fail(String message) {
        return Resulters.RESULTER().fail(message);
    }

    public static SimpleResult fail(String message, Throwable throwable) {
        return Resulters.RESULTER().fail(message, throwable);
    }

    public static SimpleResult fail(Throwable throwable) {
        return Resulters.RESULTER().fail(throwable);
    }

    public static SimpleResult warn() {
        return Resulters.RESULTER().warn();
    }

    public static SimpleResult warn(String message) {
        return Resulters.RESULTER().warn(message);
    }

    public static SimpleResult convert(Object input) {
        return Resulters.RESULTER().convert(input);
    }

    public static Mono<SimpleResult> monoResult(Mono<?> input) {
        return Mono.defer(() -> input)
                .map(next -> SimpleResult.ok())
                .onErrorResume(t -> Mono.just(SimpleResult.fail(t)));
    }

    /**
     * Creates a {@link DataResult} from this.
     *
     * @param <T> type
     * @return fail DataResult. If this result is OK, fails anyways, because no data are present.
     */
    public <T> DataResult<T> asData() {
        return asData(null);
    }

    /**
     * Creates a {@link DataResult} from this.
     *
     * @param <T> type
     * @return OK DataResult. If given data are null, fails.
     */
    public <T> DataResult<T> asData(@Nullable T data) {
        return Resulters.RESULTER().asData(this, data);
    }
}
