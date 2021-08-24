package cz.iamceph.resulter.common.extension;

import cz.iamceph.resulter.common.api.Resultable;

/**
 * @author Frantisek Novosad (fnovosad@monetplus.cz)
 */
public interface ConvertorExtension<T extends Resultable> {

    T convert(Object input);

    <K> K convert(T resultable, Class<T> target);

    String json(T resultable);
}
