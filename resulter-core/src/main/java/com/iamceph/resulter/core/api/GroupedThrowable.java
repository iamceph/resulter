package com.iamceph.resulter.core.api;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(fluent = true)
@Getter
public class GroupedThrowable extends Throwable {
    private final List<Throwable> causes;

    public GroupedThrowable(List<Throwable> causes) {
        super(causes.size() > 1 ? "Multiple Throwable occurred, use method causes() to get them all." : causes.get(0).getMessage());
        this.causes = causes;
    }

    @Override
    public void printStackTrace() {
        causes.forEach(Throwable::printStackTrace);
    }
}
