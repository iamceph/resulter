package com.iamceph.resulter.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

final class GroupedResultableBuilder implements GroupedResultable.Builder {
    private final List<Resultable> resultables;

    public GroupedResultableBuilder() {
        this.resultables = new CopyOnWriteArrayList<>();
    }

    public GroupedResultableBuilder(List<Resultable> from) {
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
        return GroupedResultable.of(resultables);
    }
}