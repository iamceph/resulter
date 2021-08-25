package cz.iamceph.resulter.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cz.iamceph.resulter.core.model.ResulterProviderImpl;

/**
 * @author Frantisek Novosad (fnovosad@monetplus.cz)
 */
public class ResultersTest {

    @Test
    public void testResulters() {
        Assertions.assertNotNull(Resulters.RESULTER());
        Assertions.assertNotNull(Resulters.DATA_RESULTER());
        Assertions.assertNotNull(Resulters.CONVERTOR());

        Assertions.assertEquals(Resulters.RESULTER().ok(), ResulterProviderImpl.get().ok());
    }
}
