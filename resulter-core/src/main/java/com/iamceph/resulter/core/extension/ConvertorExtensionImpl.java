package com.iamceph.resulter.core.extension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.iamceph.resulter.core.SimpleResult;
import com.iamceph.resulter.core.api.ResultStatus;
import com.iamceph.resulter.core.api.Resultable;
import com.iamceph.resulter.core.model.ProtoThrowable;
import com.iamceph.resulter.core.model.Result;
import com.iamceph.resulter.core.model.Resulters;

/**
 * Default implementation of ConvertorExtension.
 */
public class ConvertorExtensionImpl implements ConvertorExtension {
    private final static ConvertorExtension INSTANCE = new ConvertorExtensionImpl();

    private ConvertorExtensionImpl() {
    }

    public static ConvertorExtension get() {
        return INSTANCE;
    }

    /**
     * Tries to convert Object into a Resultable.
     * - supported type right now is {@link Result}
     *
     * @param input input
     * @return OK resultable if everything is OK, otherwise FAIL.
     */
    @Override
    public Resultable convert(Object input) {
        if (input instanceof Result) {
            final var casted = (Result) input;
            final var status = ResultStatus.fromNumber(casted.getStatus().getNumber());
            final var error = new Throwable(casted.getError().getErrorMessage());
            error.setStackTrace(toStackTrace(casted.getError().getStackTraceList()).toArray(new StackTraceElement[0]));

            return Resulters.resulter().from(status, casted.getMessage(), error);
        }

        return SimpleResult.fail("Cannot convert input[" + input.getClass().getSimpleName() + "] to Resultable!");
    }

    /**
     * Tries to convert Resultable into something else.
     * Supported types right now are:
     * - {@link Result}
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
        if (target.isAssignableFrom(Result.class)) {
            final var message = Result.newBuilder();
            final var error = resultable.error();

            if (error != null) {
                message.setError(ProtoThrowable.newBuilder()
                                .setErrorMessage(error.getMessage())
                                .addAllStackTrace(fromStackTrace(Arrays.asList(error.getStackTrace())))
                                .build())
                        .build();
            }

            if (resultable.message() != null) {
                message.setMessage(resultable.message());
            }

            message.setStatus(Result.Status.forNumber(resultable.status().getNumberValue()));
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
                        .setClassLoaderName(next.getClassLoaderName())
                        .setModuleName(next.getModuleName())
                        .setModuleVersion(next.getModuleVersion())
                        .setDeclaringClass(next.getClassName())
                        .setMethodName(next.getMethodName())
                        .setFileName(next.getFileName())
                        .setLineNumber(next.getLineNumber())
                        .build())
                .collect(Collectors.toList());

    }

    private List<StackTraceElement> toStackTrace(List<ProtoThrowable.ProtoStackTrace> elements) {
        return elements.stream()
                .map(next -> new StackTraceElement(next.getClassLoaderName(),
                        next.getModuleName(),
                        next.getModuleVersion(),
                        next.getDeclaringClass(),
                        next.getMethodName(),
                        next.getFileName(),
                        next.getLineNumber()))
                .collect(Collectors.toList());
    }
}
