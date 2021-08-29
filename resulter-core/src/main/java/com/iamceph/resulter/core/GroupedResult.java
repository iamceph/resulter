package com.iamceph.resulter.core;

import com.iamceph.resulter.core.api.GroupedResultable;
import com.iamceph.resulter.core.api.Resultable;
import com.iamceph.resulter.core.model.Resulters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * GroupedResult is a "simple" access point for creating {@link GroupedResultable}.
 */
public abstract class GroupedResult {

    /**
     * Creates empty {@link GroupedResultable}
     *
     * @return empty {@link GroupedResultable}.
     */
    public static GroupedResultable of() {
        return Resulters.groupedResulter().of();
    }

    /**
     * Creates new {@link GroupedResultable} with provided {@link Resultable}s.
     *
     * @return new {@link GroupedResultable}.
     */
    public static GroupedResultable of(Resultable... resultable) {
        return Resulters.groupedResulter().of(resultable);
    }

    /**
     * Creates new {@link GroupedResultable} with provided {@link Resultable}s.
     *
     * @return new {@link GroupedResultable}.
     */
    public static GroupedResultable of(Collection<Resultable> resultable) {
        return Resulters.groupedResulter().of(resultable);
    }

    public static GroupedResultable.Builder builder() {
        return new Builder();
    }

    public static GroupedResultable.Builder builder(List<Resultable> from) {
        return new Builder(from);
    }

    public static class Builder implements GroupedResultable.Builder {
        private final List<Resultable> resultables;

        public Builder() {
            this.resultables = new CopyOnWriteArrayList<>();
        }

        public Builder(List<Resultable> from) {
            this.resultables = new CopyOnWriteArrayList<>(from);
        }

        @Override
        public GroupedResultable.Builder with(Resultable... resultable) {
            resultables.addAll(Arrays.asList(resultable));
            return this;
        }

        @Override
        public GroupedResultable.Builder with(Collection<Resultable> resultable) {
            resultables.addAll(resultable);
            return this;
        }

        public GroupedResultable build() {
            return GroupedResult.of(resultables);
        }
    }
}
