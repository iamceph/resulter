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
        assertNotNull(Resulters.resulter());
        assertNotNull(Resulters.dataResulter());
        assertNotNull(Resulters.convertor());

        Assertions.assertEquals(Resulters.resulter().ok(), Resultable.ok());
    }
}
