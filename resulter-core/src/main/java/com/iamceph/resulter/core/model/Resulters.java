package com.iamceph.resulter.core.model;

import com.iamceph.resulter.core.api.DataResultable;
import com.iamceph.resulter.core.api.Resultable;
import com.iamceph.resulter.core.provider.DataResulterProvider;
import com.iamceph.resulter.core.extension.ConvertorExtension;
import com.iamceph.resulter.core.extension.ConvertorExtensionImpl;
import com.iamceph.resulter.core.provider.ResulterProvider;
import lombok.experimental.UtilityClass;

/**
 * A factory for any {@link Resultable}.
 * <p>
 * Goal of this factory is to have control over
 * creation of the results and provide you a way to create your own implementations.
 * <p>
 * You can supply your own {@link ResulterProvider} and thus modify the way {@link Resultable} are made.
 */
@UtilityClass
public class Resulters {
    /**
     * Provider for {@link Resultable}
     */
    private static ResulterProvider RESULTER_PROVIDER;
    /**
     * Provider for {@link DataResultable}
     */
    private static DataResulterProvider DATA_RESULTER_INSTANCE;

    private static ConvertorExtension CONVERTOR;

    static {
        RESULTER_PROVIDER = ResulterProviderImpl.get();
        DATA_RESULTER_INSTANCE = DataResulterProviderImpl.get();
        CONVERTOR = ConvertorExtensionImpl.get();
    }

    /**
     * @return current available {@link ResulterProvider}
     */
    public ResulterProvider RESULTER() {
        return RESULTER_PROVIDER;
    }

    /**
     * Replaces current {@link ResulterProvider} with new one. Implementation is on you :)
     *
     * @param provider the new provider
     */
    public void RESULTER(ResulterProvider provider) {
        Resulters.RESULTER_PROVIDER = provider;
    }

    public DataResulterProvider DATA_RESULTER() {
        return DATA_RESULTER_INSTANCE;
    }

    /**
     * Replaces current {@link DataResulterProvider} with new one. Implementation is on you :)
     *
     * @param provider the new provider
     */
    public void DATA_RESULTER(DataResulterProvider provider) {
        Resulters.DATA_RESULTER_INSTANCE = provider;
    }

    public ConvertorExtension CONVERTOR() {
        return CONVERTOR;
    }

    public void CONVERTOR(ConvertorExtension convertorExtension) {
        CONVERTOR = convertorExtension;
    }
}
