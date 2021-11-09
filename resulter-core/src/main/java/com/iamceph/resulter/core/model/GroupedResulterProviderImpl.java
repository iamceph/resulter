package com.iamceph.resulter.core.model;

import com.iamceph.resulter.core.GroupedResultable;
import com.iamceph.resulter.core.Resultable;
import com.iamceph.resulter.core.api.provider.GroupedResulterProvider;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

final class GroupedResulterProviderImpl implements GroupedResulterProvider {

    private GroupedResulterProviderImpl() {
    }

    public static GroupedResulterProviderImpl get() {
        return new GroupedResulterProviderImpl();
    }

    @Override
    public GroupedResultable of() {
        return new GroupedResulImpl(Collections.emptyList());
    }

    @Override
    public GroupedResultable of(Resultable... resultable) {
        return new GroupedResulImpl(Arrays.stream(resultable).collect(Collectors.toList()));
    }

    @Override
    public GroupedResultable of(Collection<Resultable> resultable) {
        return new GroupedResulImpl(resultable);
    }
}
