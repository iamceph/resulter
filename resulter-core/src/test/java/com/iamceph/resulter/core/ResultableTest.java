package com.iamceph.resulter.core;

import lombok.val;
import lombok.var;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ResultableTest {

    @Test
    void testOk() {
        final var result = Resultable.ok();

        assertTrue(result.isOk());

        assertFalse(result.isFail());
        assertFalse(result.isWarning());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals("Ok.", result.message());
    }

    @Test
    void testOkWithMessage() {
        final var result = Resultable.ok("test message");

        assertTrue(result.isOk());

        assertFalse(result.isFail());
        assertFalse(result.isWarning());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals("test message", result.message());
    }

    @Test
    void testFailWithMessage() {
        final var result = Resultable.fail("test message");

        assertTrue(result.isFail());

        assertFalse(result.isOk());
        assertFalse(result.isWarning());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals("test message", result.message());
    }

    @Test
    void testFailWithMessageAndThrowable() {
        final var result = Resultable.fail("test message", new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isFail());

        assertFalse(result.isOk());
        assertFalse(result.isWarning());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals("test message", result.message());

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    void testFailWithThrowable() {
        final var result = Resultable.fail(new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isFail());

        assertFalse(result.isOk());
        assertFalse(result.isWarning());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals("This is not supposed to happen. :)", result.message());

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    void testFailWithNullPointer() {
        final var result = Resultable.fail(new NullPointerException());

        assertTrue(result.isFail());

        assertFalse(result.isOk());
        assertFalse(result.isWarning());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals("", result.message());

        assertSame(Objects.requireNonNull(result.error()).getClass(), NullPointerException.class);
    }

    @Test
    void testWarningWithMessage() {
        final var result = Resultable.warning("test message");

        assertTrue(result.isWarning());

        assertFalse(result.isOk());
        assertFalse(result.isFail());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals("test message", result.message());
    }

    @Test
    void testWarningWithMessageAndThrowable() {
        final var result = Resultable.warning("test message", new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isWarning());

        assertFalse(result.isOk());
        assertFalse(result.isFail());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals("test message", result.message());

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    void testWarningWithThrowable() {
        final var result = Resultable.warning(new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isWarning());

        assertFalse(result.isOk());
        assertFalse(result.isFail());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals("This is not supposed to happen. :)", result.message());

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    void testWarningWithNullPointer() {
        final var result = Resultable.warning(new NullPointerException());

        assertTrue(result.isWarning());

        assertFalse(result.isOk());
        assertFalse(result.isFail());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals("", result.message());

        assertSame(Objects.requireNonNull(result.error()).getClass(), NullPointerException.class);
    }

    @Test
    void testFailToProtobuf() {
        final var result = Resultable.fail(new NullPointerException());

        assertTrue(result.isFail());

        assertFalse(result.isOk());
        assertFalse(result.isWarning());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals("", result.message());

        assertSame(Objects.requireNonNull(result.error()).getClass(), NullPointerException.class);

        val convert = result.asProto();

        assertNotNull(convert, "Converted is not present!");
        assertEquals("", convert.getMessage());

    }
}
