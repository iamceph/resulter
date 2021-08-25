package cz.iamceph.resulter.core.extension;

import cz.iamceph.resulter.core.api.Resultable;

/**
 * @author Frantisek Novosad (fnovosad@monetplus.cz)
 */
public interface ConvertorExtension {

    Resultable convert(Object input);

    <T> T convert(Resultable resultable, Class<T> target);

    String json(Resultable resultable);
}
