package com.iamceph.resulter.core.api.provider;

import com.iamceph.resulter.core.api.DataResultable;
import com.iamceph.resulter.core.api.GroupedResultable;
import com.iamceph.resulter.core.api.Resultable;

import java.util.Collection;

/**
 * Service that provides {@link DataResultable}.
 */
public interface GroupedResulterProvider {

    GroupedResultable of();

    GroupedResultable of(Resultable... resultable);

    GroupedResultable of(Collection<Resultable> resultable);
}
