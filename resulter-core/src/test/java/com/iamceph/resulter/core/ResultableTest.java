package com.iamceph.resulter.core;

import lombok.var;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ResultableTest {

    @Test
    public void testOk() {
        final var result = Resultable.ok();

        assertTrue(result.isOk());

        assertFalse(result.isFail());
        assertFalse(result.isWarning());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "Ok.");
    }

    @Test
    public void testOkWithMessage() {
        final var result = Resultable.ok("test message");

        assertTrue(result.isOk());

        assertFalse(result.isFail());
        assertFalse(result.isWarning());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "test message");
    }

    @Test
    public void testFailWithMessage() {
        final var result = Resultable.fail("test message");

        assertTrue(result.isFail());

        assertFalse(result.isOk());
        assertFalse(result.isWarning());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "test message");
    }

    @Test
    public void testFailWithMessageAndThrowable() {
        final var result = Resultable.fail("test message", new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isFail());

        assertFalse(result.isOk());
        assertFalse(result.isWarning());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "test message");

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    public void testFailWithThrowable() {
        final var result = Resultable.fail(new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isFail());

        assertFalse(result.isOk());
        assertFalse(result.isWarning());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "This is not supposed to happen. :)");

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    public void testWarningWithMessage() {
        final var result = Resultable.warning("test message");

        assertTrue(result.isWarning());

        assertFalse(result.isOk());
        assertFalse(result.isFail());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "test message");
    }

    @Test
    public void testWarningWithMessageAndThrowable() {
        final var result = Resultable.warning("test message", new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isWarning());

        assertFalse(result.isOk());
        assertFalse(result.isFail());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "test message");

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    public void testWarningWithThrowable() {
        final var result = Resultable.warning(new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isWarning());

        assertFalse(result.isOk());
        assertFalse(result.isFail());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "This is not supposed to happen. :)");

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }
}
