package cz.iamceph.resulter.common;

import cz.iamceph.resulter.common.api.Resultable;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

/**
 *
 */
public abstract class SimpleResult {

    /**
     * We don't want people to initialize this by themselves, right? :)
     */
    protected SimpleResult() {
    }

    /**
     *
     * @return
     */
    public static Resultable ok() {
        return Resulters.RESULTER().ok();
    }

    /**
     *
     * @param message
     * @return
     */
    public static Resultable ok(String message) {
        return Resulters.RESULTER().ok(message);
    }

    /**
     *
     * @param message
     * @return
     */
    public static Resultable fail(String message) {
        return Resulters.RESULTER().fail(message);
    }

    /**
     *
     * @param message
     * @param throwable
     * @return
     */
    public static Resultable fail(String message, Throwable throwable) {
        return Resulters.RESULTER().fail(message, throwable);
    }

    /**
     *
     * @param throwable
     * @return
     */
    public static Resultable fail(Throwable throwable) {
        return Resulters.RESULTER().fail(throwable);
    }

    /**
     *
     * @param message
     * @return
     */
    public static Resultable warning(String message) {
        return Resulters.RESULTER().warning(message);
    }

    /**
     *
     * @param message
     * @param throwable
     * @return
     */
    public static Resultable warning(String message, Throwable throwable) {
        return Resulters.RESULTER().warning(message, throwable);
    }

    /**
     *
     * @param throwable
     * @return
     */
    public static Resultable warning(Throwable throwable) {
        return Resulters.RESULTER().warning(throwable);
    }

    /**
     *
     * @param input
     * @return
     */
    public static Resultable convert(Object input) {
        return Resulters.RESULTER().convert(input);
    }

    /**
     *
     * @param input
     * @return
     */
    public static Mono<Resultable> monoResult(Mono<?> input) {
        return Mono.defer(() -> input)
                .map(next -> SimpleResult.ok())
                .onErrorResume(t -> Mono.just(SimpleResult.fail(t)));
    }
}
