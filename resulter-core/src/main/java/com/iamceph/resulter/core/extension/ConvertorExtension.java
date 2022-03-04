package com.iamceph.resulter.core.extension;

import org.jetbrains.annotations.NotNull;

import com.iamceph.resulter.core.Resultable;

/**
 * Converts the Resultable from/to types.
 */
public interface ConvertorExtension {

    /**
     * Tries to convert the Input into Resultable.
     *
     * @param input input to convert
     * @return Converted Resultable or Failed Resultable if cannot convert.
     */
    @NotNull
    Resultable convert(Object input);

    <T> T convert(Resultable resultable, Class<T> target);

    String json(Resultable resultable);
}
