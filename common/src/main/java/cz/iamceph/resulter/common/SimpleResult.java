package cz.iamceph.resulter.common;

import java.util.function.Supplier;

import cz.iamceph.resulter.common.api.ResultStatus;
import cz.iamceph.resulter.common.api.Resultable;
import reactor.core.publisher.Mono;

/**
 * SimpleResult is a "simple" access point for creating {@link Resultable}.
 */
public abstract class SimpleResult {

    protected SimpleResult() {
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#OK} with no message.
     *
     * @return OK Resultable.
     */
    public static Resultable ok() {
        return Resulters.RESULTER().ok();
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#OK} and holds a message.
     *
     * @param message message of this result.
     * @return OK Resultable with message.
     */
    public static Resultable ok(String message) {
        return Resulters.RESULTER().ok(message);
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#FAIL} and holds a message.
     *
     * @param message message of this result.
     * @return FAIL Resultable with message.
     */
    public static Resultable fail(String message) {
        return Resulters.RESULTER().fail(message);
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#FAIL} and holds a message with throwable.
     *
     * @param message   message of this result.
     * @param throwable what caused this fail.
     * @return FAIL Resultable with message and throwable.
     */
    public static Resultable fail(String message, Throwable throwable) {
        return Resulters.RESULTER().fail(message, throwable);
    }

    /**
     * Creates new {@link Resultable} that has state {@link ResultStatus#FAIL} and holds a throwable.
     * Message from {@link Resultable#message()} is taken from the throwable.
     *
     * @param throwable what caused this fail.
     * @return FAIL Resultable with throwable.
     */
    public static Resultable fail(Throwable throwable) {
        return Resulters.RESULTER().fail(throwable);
    }

    /**
     * @param message
     * @return
     */
    public static Resultable warning(String message) {
        return Resulters.RESULTER().warning(message);
    }

    /**
     * @param message
     * @param throwable
     * @return
     */
    public static Resultable warning(String message, Throwable throwable) {
        return Resulters.RESULTER().warning(message, throwable);
    }

    /**
     * @param throwable
     * @return
     */
    public static Resultable warning(Throwable throwable) {
        return Resulters.RESULTER().warning(throwable);
    }

    /**
     * @param input
     * @return
     */
    public static Mono<Resultable> monoResult(Mono<?> input) {
        return monoResult(() -> input);
    }

    /**
     * @param input
     * @return
     */
    public static Mono<Resultable> monoResult(Supplier<Mono<?>> input) {
        return Mono.defer(input)
                .map(next -> SimpleResult.ok())
                .onErrorResume(t -> Mono.just(SimpleResult.fail(t)));
    }
}
