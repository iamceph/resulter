package com.iamceph.resulter.core.api;

import com.iamceph.resulter.core.GroupedResult;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * A Resultable that determines its state from other Resultables.
 * This could be useful when more Resultables needs to be merged into one.
 */
public interface GroupedResultable extends Resultable {

    /**
     * @return a {@link GroupedThrowable}
     */
    @Override
    @Nullable GroupedThrowable error();

    /**
     * Creates new {@link GroupedResultable}.
     * This copies current saved Resultables and new one provided.
     *
     * @param resultables resultables to merge with
     * @return new grouped result
     */
    GroupedResultable merge(Resultable... resultables);

    /**
     * Creates new {@link GroupedResultable}.
     * This copies current saved Resultables and new one provided.
     *
     * @param resultables resultables to merge with
     * @return new grouped result
     */
    GroupedResultable merge(Collection<Resultable> resultables);

    /**
     * @return Separated messages of each {@link Resultable}
     */
    List<String> messages();

    /**
     * @return separated {@link Resultable} for this result.
     */
    List<Resultable> resultables();

    default Builder toBuilder() {
        return GroupedResult.builder(resultables());
    }

    /**
     * Builder for GroupedResultable
     */
    interface Builder {
        Builder with(Resultable... resultable);

        Builder with(Collection<Resultable> resultable);

        /**
         * Creates new GroupedResutable from the builder.
         * @return
         */
        GroupedResultable build();
    }
}
