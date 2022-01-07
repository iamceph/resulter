package com.iamceph.resulter.core.extension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.iamceph.resulter.core.Resultable;
import com.iamceph.resulter.core.api.ResultStatus;
import com.iamceph.resulter.core.model.ProtoResultable;
import com.iamceph.resulter.core.model.ProtoThrowable;
import com.iamceph.resulter.core.model.Resulters;

import lombok.var;

/**
 * Default implementation of ConvertorExtension.
 */
public class ConvertorExtensionImpl implements ConvertorExtension {
    private final static ConvertorExtension INSTANCE = new ConvertorExtensionImpl();

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
    public Resultable convert(Object input) {
        if (input instanceof ProtoResultable) {
            final var casted = (ProtoResultable) input;
            final var status = ResultStatus.fromNumber(casted.getStatus().getNumber());
            final var error = new Throwable(casted.getError().getErrorMessage());
            error.setStackTrace(toStackTrace(casted.getError().getStackTraceList()).toArray(new StackTraceElement[0]));

            return Resulters.resulter().from(status, casted.getMessage(), error);
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
            final var message = ProtoResultable.newBuilder();
            final var error = resultable.error();

            if (error != null) {
                message.setError(ProtoThrowable.newBuilder()
                                .setErrorMessage(error.getMessage())
                                .addAllStackTrace(fromStackTrace(Arrays.asList(error.getStackTrace())))
                                .build())
                        .build();
            }

            message.setMessage(resultable.message());
            message.setStatus(ProtoResultable.Status.forNumber(resultable.status().getNumberValue()));
            return (T) message.build();
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
                        .setDeclaringClass(next.getClassName())
                        .setMethodName(next.getMethodName())
                        .setFileName(next.getFileName())
                        .setLineNumber(next.getLineNumber())
                        .build())
                .collect(Collectors.toList());

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
