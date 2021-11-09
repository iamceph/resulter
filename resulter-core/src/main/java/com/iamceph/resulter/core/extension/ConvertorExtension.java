package com.iamceph.resulter.core.extension;

import com.iamceph.resulter.core.Resultable;

/**
 * @author Frantisek Novosad (fnovosad@monetplus.cz)
 */
public interface ConvertorExtension {

    Resultable convert(Object input);

    <T> T convert(Resultable resultable, Class<T> target);

    String json(Resultable resultable);
}
