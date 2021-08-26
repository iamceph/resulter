package com.iamceph.resulter.core;

import com.iamceph.resulter.core.model.Resulters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Frantisek Novosad (fnovosad@monetplus.cz)
 */
public class ResultersTest {

    @Test
    public void testResulters() {
        assertNotNull(Resulters.RESULTER());
        assertNotNull(Resulters.DATA_RESULTER());
        assertNotNull(Resulters.CONVERTOR());

        Assertions.assertEquals(Resulters.RESULTER().ok(), SimpleResult.ok());
    }
}
