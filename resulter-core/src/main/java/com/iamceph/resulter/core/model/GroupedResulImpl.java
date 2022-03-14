package com.iamceph.resulter.core.model;

import com.iamceph.resulter.core.GroupedResultable;
import com.iamceph.resulter.core.Resultable;
import com.iamceph.resulter.core.api.GroupedThrowable;
import com.iamceph.resulter.core.api.ResultStatus;
import lombok.var;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Default implementation of the {@link GroupedResultable}
 */
final class GroupedResulImpl implements GroupedResultable {
    private final List<Resultable> resultables;
    private final Long failResultables;
    private final Long warningResultables;

    GroupedResulImpl(Collection<Resultable> resultables) {
        this.resultables = new LinkedList<>(resultables);

        failResultables = this.resultables
                .stream()
                .filter(result -> result.status() == ResultStatus.FAIL)
                .count();
        warningResultables = this.resultables
                .stream()
                .filter(result -> result.status() == ResultStatus.WARNING)
                .count();
    }

    @Override
    public @NotNull String message() {
        return toLineMessage();
    }

    @Override
    public @Nullable GroupedThrowable error() {
        if (failResultables == 0 && warningResultables == 0) {
            return null;
        }

        return new GroupedThrowable(resultables.stream()
                .filter(next -> !next.isOk())
                .map(Resultable::error)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    @Override
    public GroupedResultable merge(Resultable... resultables) {
        return merge(Arrays.stream(resultables).collect(Collectors.toList()));
    }

    @Override
    public GroupedResultable merge(Collection<Resultable> resultables) {
        return new GroupedResulImpl(Stream.concat(resultables.stream(), this.resultables.stream())
                .collect(Collectors.toList()));
    }

    @Override
    public List<String> messages() {
        return resultables.stream()
                .map(Resultable::message)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public @NotNull ResultStatus status() {
        if (failResultables == 0 && warningResultables == 0) {
            return ResultStatus.OK;
        }

        if (failResultables > 0) {
            return ResultStatus.FAIL;
        }

        return ResultStatus.WARNING;
    }

    @Override
    public boolean isOk() {
        return status() == ResultStatus.OK;
    }

    @Override
    public boolean isFail() {
        return status() == ResultStatus.FAIL;
    }

    @Override
    public boolean isWarning() {
        return status() == ResultStatus.WARNING;
    }

    @Override
    public Convertor convertor() {
        return new Convertor() {
            @Override
            public String json() {
                return Resulters.convertor().json(GroupedResulImpl.this);
            }

            @Override
            public <K> K convert(Class<K> target) {
                return Resulters.convertor().convert(GroupedResulImpl.this, target);
            }
        };
    }

    public List<Resultable> resultables() {
        return Collections.unmodifiableList(resultables);
    }

    private String toLineMessage() {
        final var builder = new StringBuilder();
        resultables.forEach(result -> {
            if (result.isFail()) {
                builder.append(result.message());
                builder.append(System.getProperty("line.separator"));
            }
        });

        return builder.toString();
    }

    @Override
    public ProtoResultable asProto() {
        return convertor().asProto();
    }
}
