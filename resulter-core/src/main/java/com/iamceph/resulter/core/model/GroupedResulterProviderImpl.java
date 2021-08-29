package com.iamceph.resulter.core.model;

import com.iamceph.resulter.core.api.GroupedResultable;
import com.iamceph.resulter.core.api.Resultable;
import com.iamceph.resulter.core.api.provider.GroupedResulterProvider;

import java.util.Collection;
import java.util.List;

final class GroupedResulterProviderImpl implements GroupedResulterProvider {

    private GroupedResulterProviderImpl() {
    }

    public static GroupedResulterProviderImpl get() {
        return new GroupedResulterProviderImpl();
    }

    @Override
    public GroupedResultable of() {
        return new GroupedResultableImpl(List.of());
    }

    @Override
    public GroupedResultable of(Resultable... resultable) {
        return new GroupedResultableImpl(List.of(resultable));
    }

    @Override
    public GroupedResultable of(Collection<Resultable> resultable) {
        return new GroupedResultableImpl(resultable);
    }
}
