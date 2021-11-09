package com.iamceph.resulter.core;

import com.iamceph.resulter.core.api.GroupedThrowable;
import com.iamceph.resulter.core.model.Resulters;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * A Resultable that determines its state from other Resultables.
 * This could be useful when more Resultables needs to be merged into one.
 */
public interface GroupedResultable extends Resultable {


    /**
     * Creates empty {@link GroupedResultable}
     *
     * @return empty {@link GroupedResultable}.
     */
    static GroupedResultable of() {
        return Resulters.groupedResulter().of();
    }

    /**
     * Creates new {@link GroupedResultable} with provided {@link Resultable}s.
     *
     * @return new {@link GroupedResultable}.
     */
    static GroupedResultable of(Resultable... resultable) {
        return Resulters.groupedResulter().of(resultable);
    }

    /**
     * Creates new {@link GroupedResultable} with provided {@link Resultable}s.
     *
     * @return new {@link GroupedResultable}.
     */
    static GroupedResultable of(Collection<Resultable> resultable) {
        return Resulters.groupedResulter().of(resultable);
    }

    static GroupedResultable.Builder builder() {
        return new GroupedResultableBuilder();
    }

    static GroupedResultable.Builder builder(List<Resultable> from) {
        return new GroupedResultableBuilder(from);
    }

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
        return GroupedResultable.builder(resultables());
    }

    /**
     * Builder for GroupedResultable
     */
    interface Builder {
        Builder with(Resultable... resultable);

        Builder with(Collection<Resultable> resultable);

        /**
         * Creates new GroupedResutable from the builder.
         *
         * @return
         */
        GroupedResultable build();
    }
}
