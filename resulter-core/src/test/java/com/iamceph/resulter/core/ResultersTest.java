package com.iamceph.resulter.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.iamceph.resulter.core.model.ResulterProviderImpl;

/**
 * @author Frantisek Novosad (fnovosad@monetplus.cz)
 */
public class ResultersTest {

    @Test
    public void testResulters() {
        assertNotNull(Resulters.RESULTER());
        assertNotNull(Resulters.DATA_RESULTER());
        assertNotNull(Resulters.CONVERTOR());

        Assertions.assertEquals(Resulters.RESULTER().ok(), ResulterProviderImpl.get().ok());
    }
}
