package com.iamceph.resulter.core.model;

import com.iamceph.resulter.core.api.GroupedResultable;
import com.iamceph.resulter.core.api.GroupedThrowable;
import com.iamceph.resulter.core.api.ResultStatus;
import com.iamceph.resulter.core.api.Resultable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Default implementation of the {@link GroupedResultable}
 */
final class GroupedResultableImpl implements GroupedResultable {
    private final List<Resultable> resultables;
    private final Long failResultables;
    private final Long warningResultables;

    GroupedResultableImpl(Collection<Resultable> resultables) {
        this.resultables = List.copyOf(resultables);

        failResultables = this.resultables.stream()
                .filter(result -> result.status() == ResultStatus.FAIL)
                .count();
        warningResultables = this.resultables.stream()
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
       return merge(List.of(resultables));
    }

    @Override
    public GroupedResultable merge(Collection<Resultable> resultables) {
        return new GroupedResultableImpl(Stream.concat(resultables.stream(), this.resultables.stream())
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
                return Resulters.convertor().json(GroupedResultableImpl.this);
            }

            @Override
            public <K> K convert(Class<K> target) {
                return Resulters.convertor().convert(GroupedResultableImpl.this, target);
            }
        };
    }

    public List<Resultable> resultables() {
        return List.copyOf(resultables);
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
}
