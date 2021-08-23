package cz.iamceph.resulter.common;

import cz.iamceph.resulter.common.model.DataResulterProviderImpl;
import cz.iamceph.resulter.common.model.ResulterProviderImpl;
import cz.iamceph.resulter.common.provider.DataResulterProvider;
import cz.iamceph.resulter.common.provider.ResulterProvider;

public class Resulters {
    private static ResulterProvider RESULTER_PROVIDER;
    private static DataResulterProvider DATA_RESULTER_INSTANCE;

    static {
        RESULTER_PROVIDER = ResulterProviderImpl.get();
        DATA_RESULTER_INSTANCE = DataResulterProviderImpl.get();
    }

    public static ResulterProvider RESULTER() {
        return RESULTER_PROVIDER;
    }

    public static void RESULTER(ResulterProvider resulterProvider) {
        Resulters.RESULTER_PROVIDER = resulterProvider;
    }

    public static DataResulterProvider DATA_RESULTER() {
        return DATA_RESULTER_INSTANCE;
    }

    public static void DATA_RESULTER(DataResulterProvider dataResulterProvider) {
        Resulters.DATA_RESULTER_INSTANCE = dataResulterProvider;
    }
}
