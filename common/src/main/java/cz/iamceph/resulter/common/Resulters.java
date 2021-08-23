package cz.iamceph.resulter.common;

public class Resulters {
    private static AbstractResulter RESULTER_INSTANCE;
    private static AbstractDataResulter DATA_RESULTER_INSTANCE;

    static AbstractResulter RESULTER() {
        return RESULTER_INSTANCE;
    }

    public static void RESULTER(AbstractResulter resulter) {
        Resulters.RESULTER_INSTANCE = resulter;
    }

    static AbstractResulter DATA_RESULTER() {
        return RESULTER_INSTANCE;
    }

    public static void DATA_RESULTER(AbstractDataResulter dataResulter) {
        Resulters.DATA_RESULTER_INSTANCE = dataResulter;
    }
}
