package com.iamceph.resulter.core;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

import org.junit.jupiter.api.Test;

public class SimpleResultTest {

    @Test
    public void testOk() {
        final var result = SimpleResult.ok();

        assertTrue(result.isOk());

        assertFalse(result.isFail());
        assertFalse(result.isWarning());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "Ok.");
    }

    @Test
    public void testOkWithMessage() {
        final var result = SimpleResult.ok("test message");

        assertTrue(result.isOk());

        assertFalse(result.isFail());
        assertFalse(result.isWarning());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "test message");
    }

    @Test
    public void testFailWithMessage() {
        final var result = SimpleResult.fail("test message");

        assertTrue(result.isFail());

        assertFalse(result.isOk());
        assertFalse(result.isWarning());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "test message");
    }

    @Test
    public void testFailWithMessageAndThrowable() {
        final var result = SimpleResult.fail("test message", new UnsupportedOperationException("This is not supposed to happen. :)"));

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
        final var result = SimpleResult.fail(new UnsupportedOperationException("This is not supposed to happen. :)"));

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
        final var result = SimpleResult.warning("test message");

        assertTrue(result.isWarning());

        assertFalse(result.isOk());
        assertFalse(result.isFail());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "test message");
    }

    @Test
    public void testWarningWithMessageAndThrowable() {
        final var result = SimpleResult.warning("test message", new UnsupportedOperationException("This is not supposed to happen. :)"));

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
        final var result = SimpleResult.warning(new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isWarning());

        assertFalse(result.isOk());
        assertFalse(result.isFail());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "This is not supposed to happen. :)");

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }
}
