package com.iamceph.resulter.core.model;

import com.iamceph.resulter.core.api.DataResultable;
import com.iamceph.resulter.core.api.GroupedResultable;
import com.iamceph.resulter.core.api.Resultable;
import com.iamceph.resulter.core.api.provider.DataResulterProvider;
import com.iamceph.resulter.core.api.provider.GroupedResulterProvider;
import com.iamceph.resulter.core.api.provider.ResulterProvider;
import com.iamceph.resulter.core.extension.ConvertorExtension;
import com.iamceph.resulter.core.extension.ConvertorExtensionImpl;
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
public final class Resulters {
    /**
     * Provider for {@link Resultable}
     */
    private static ResulterProvider RESULTER_PROVIDER;
    /**
     * Provider for {@link DataResultable}
     */
    private static DataResulterProvider DATA_RESULTER_INSTANCE;
    /**
     * Provider for {@link GroupedResultable}
     */
    private static GroupedResulterProvider GROUPED_RESULTER_INSTANCE;
    /**
     * Provider for {@link ConvertorExtension}
     */
    private static ConvertorExtension CONVERTOR;

    static {
        RESULTER_PROVIDER = ResulterProviderImpl.get();
        DATA_RESULTER_INSTANCE = DataResulterProviderImpl.get();
        GROUPED_RESULTER_INSTANCE = GroupedResulterProviderImpl.get();
        CONVERTOR = ConvertorExtensionImpl.get();
    }

    /**
     * @return current available {@link ResulterProvider}
     */
    public ResulterProvider resulter() {
        return RESULTER_PROVIDER;
    }

    /**
     * Replaces current {@link ResulterProvider} with new one. Implementation is on you :)
     *
     * @param provider the new provider
     */
    public void resulter(ResulterProvider provider) {
        Resulters.RESULTER_PROVIDER = provider;
    }

    /**
     * @return current available {@link DataResulterProvider}
     */
    public DataResulterProvider dataResulter() {
        return DATA_RESULTER_INSTANCE;
    }

    /**
     * Replaces current {@link DataResulterProvider} with new one. Implementation is on you :)
     *
     * @param provider the new provider
     */
    public void dataResulter(DataResulterProvider provider) {
        Resulters.DATA_RESULTER_INSTANCE = provider;
    }

    /**
     * @return current available {@link GroupedResulterProvider}
     */
    public GroupedResulterProvider groupedResulter() {
        return GROUPED_RESULTER_INSTANCE;
    }

    /**
     * Replaces current {@link GroupedResulterProvider} with new one. Implementation is on you :)
     *
     * @param provider the new provider
     */
    public void groupedResulter(GroupedResulterProvider provider) {
        Resulters.GROUPED_RESULTER_INSTANCE = provider;
    }

    /**
     * @return current available {@link ConvertorExtension}
     */
    public ConvertorExtension convertor() {
        return CONVERTOR;
    }

    /**
     * Replaces current {@link ConvertorExtension} with new one. Implementation is on you :)
     *
     * @param convertorExtension the new extension
     */
    public void convertor(ConvertorExtension convertorExtension) {
        CONVERTOR = convertorExtension;
    }
}
