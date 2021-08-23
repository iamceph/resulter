package cz.iamceph.resulter.common;

public abstract class AbstractDataResulter {
    private static AbstractDataResulter provider;

    public static SimpleResult ok() {
        checkProviderOrThrow();
        return provider.ok0();
    }

    protected abstract SimpleResult ok0();

    protected static void checkProviderOrThrow() {
        if (provider == null) {
            throw new UnsupportedOperationException("ResultProvider is not initialized.");
        }
    }
}
