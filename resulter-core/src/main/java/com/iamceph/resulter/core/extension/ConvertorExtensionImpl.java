package com.iamceph.resulter.core.extension;

import com.iamceph.resulter.core.Resultable;
import com.iamceph.resulter.core.api.ResultStatus;
import com.iamceph.resulter.core.model.ProtoResultable;
import com.iamceph.resulter.core.model.ProtoThrowable;
import com.iamceph.resulter.core.model.Resulters;

import lombok.val;
import lombok.var;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

/**
 * Default implementation of ConvertorExtension.
 */
public class ConvertorExtensionImpl implements ConvertorExtension {
    private static final ConvertorExtension INSTANCE = new ConvertorExtensionImpl();

    protected ConvertorExtensionImpl() {
    }

    public static ConvertorExtension get() {
        return INSTANCE;
    }

    /**
     * Tries to convert Object into a Resultable.
     * - supported type right now is {@link ProtoResultable}
     *
     * @param input input
     * @return OK resultable if everything is OK, otherwise FAIL.
     */
    @Override
    public @NotNull Resultable convert(Object input) {
        if (input instanceof ProtoResultable) {
            final var casted = (ProtoResultable) input;
            final var message = casted.getMessage();
            final var status = ResultStatus.fromNumber(casted.getStatus().getNumber());
            final var error = casted.getError();

            if (!error.equals(error.getDefaultInstanceForType())) {
                final var buildError = new Throwable(error.getErrorMessage());
                buildError.setStackTrace(
                        toStackTrace(error.getStackTraceList())
                                .toArray(new StackTraceElement[0])
                );
                return Resulters.resulter().from(status, message, buildError);
            }

            return Resulters.resulter().from(status, message, null);
        }

        return Resultable.fail("Cannot convert input[" + input.getClass().getSimpleName() + "] to Resultable!");
    }

    /**
     * Tries to convert Resultable into something else.
     * Supported types right now are:
     * - {@link ProtoResultable}
     * - JSON string.
     *
     * @param resultable resultable to convert from
     * @param target     target
     * @param <T>        target type
     * @return target
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T convert(Resultable resultable, Class<T> target) {
        if (target.isAssignableFrom(ProtoResultable.class)) {
            final var builder = ProtoResultable.newBuilder();
            final var error = resultable.error();

            if (error != null) {
                builder.setError(ProtoThrowable.newBuilder()
                                .setErrorMessage(getOrEmpty(error.getMessage()))
                                .addAllStackTrace(fromStackTrace(Arrays.asList(error.getStackTrace())))
                                .build())
                        .build();
            }

            builder.setMessage(resultable.message());
            builder.setStatus(ProtoResultable.Status.forNumber(resultable.status().getNumberValue()));
            return (T) builder.build();
        }

        if (target.isAssignableFrom(String.class)) {
            return (T) json(resultable);
        }

        throw new UnsupportedOperationException("Cannot convert Resultable to [" + target.getSimpleName() + "]!");
    }

    @Override
    public String json(Resultable resultable) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    private List<ProtoThrowable.ProtoStackTrace> fromStackTrace(List<StackTraceElement> elements) {
        return elements.stream()
                .map(next -> ProtoThrowable.ProtoStackTrace.newBuilder()
                        .setDeclaringClass(getOrEmpty(next.getClassName()))
                        .setMethodName(getOrEmpty(next.getMethodName()))
                        .setFileName(getOrEmpty(next.getFileName()))
                        .setLineNumber(next.getLineNumber())
                        .build())
                .collect(Collectors.toList());

    }

    private String getOrEmpty(String input) {
        if (input == null) {
            return "";
        }
        return input;
    }

    private List<StackTraceElement> toStackTrace(List<ProtoThrowable.ProtoStackTrace> elements) {
        return elements.stream()
                .map(next -> new StackTraceElement(
                        next.getDeclaringClass(),
                        next.getMethodName(),
                        next.getFileName(),
                        next.getLineNumber()))
                .collect(Collectors.toList());
    }
}
