package cz.iamceph.resulter.common.extension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cz.iamceph.resulter.common.api.Resultable;
import cz.iamceph.resulter.common.model.ProtoThrowable;

/**
 * @author Frantisek Novosad (fnovosad@monetplus.cz)
 */
public class ConvertorExtensionImpl implements ConvertorExtension {

    private ConvertorExtensionImpl() {

    }

    public static ConvertorExtension get() {
        return new ConvertorExtensionImpl();
    }

    @Override
    public Resultable convert(Object input) {
        if (input instanceof cz.iamceph.resulter.common.model.Result) {
            final var casted = (cz.iamceph.resulter.common.model.Result) input;
            final var error = new Throwable(casted.getError().getErrorMessage());
            error.setStackTrace(toStackTrace(casted.getError().getStackTraceList()).toArray(new StackTraceElement[0]));
        }

        throw new UnsupportedOperationException("Cannot convert this input to Resultable.");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T convert(Resultable resultable, Class<T> target) {
        if (target.isAssignableFrom(cz.iamceph.resulter.common.model.Result.class)) {
            final var message = cz.iamceph.resulter.common.model.Result.newBuilder();
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

            message.setStatus(cz.iamceph.resulter.common.model.Result.Status.forNumber(resultable.status().getNumberValue()));
            return (T) message.build();
        }

        if (target.isAssignableFrom(String.class)) {
            return (T) json(resultable);
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public String json(Resultable resultable) {
        //TODO
        return null;
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
                .map(next -> {
                    return new StackTraceElement(next.getClassLoaderName(),
                            next.getModuleName(),
                            next.getModuleVersion(),
                            next.getDeclaringClass(),
                            next.getMethodName(),
                            next.getFileName(),
                            next.getLineNumber());
                })
                .collect(Collectors.toList());
    }
}
