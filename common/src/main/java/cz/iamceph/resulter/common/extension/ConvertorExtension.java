package cz.iamceph.resulter.common.extension;

import cz.iamceph.resulter.common.api.Resultable;

/**
 * @author Frantisek Novosad (fnovosad@monetplus.cz)
 */
public interface ConvertorExtension {

    Resultable convert(Object input);

    <T> T convert(Resultable resultable, Class<T> target);

    String json(Resultable resultable);
}
