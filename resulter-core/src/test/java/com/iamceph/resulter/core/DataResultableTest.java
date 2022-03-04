package com.iamceph.resulter.core;

import lombok.var;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple testing for DataResultable.
 */
class DataResultableTest {

    @Test
    void testOkWithData() {
        final var result = DataResultable.ok("test data");

        assertTrue(result.isOk());
        assertTrue(result.hasData());

        assertFalse(result.isFail());
        assertFalse(result.isWarning());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.data(), "test data");
        assertEquals(result.message(), "Ok.");
    }

    @Test
    void testOkWithDataAndMessage() {
        final var result = DataResultable.ok("test data", "test message");

        assertTrue(result.isOk());
        assertTrue(result.hasData());

        assertFalse(result.isFail());
        assertFalse(result.isWarning());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.data(), "test data");
        assertEquals(result.message(), "test message");
    }

    @Test
    void testFailWithMessage() {
        final var result = DataResultable.fail("test message");

        assertTrue(result.isFail());

        assertFalse(result.isOk());
        assertFalse(result.hasData());
        assertFalse(result.isWarning());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals("test message", result.message());
    }

    @Test
    void testFailWithThrowable() {
        final var result = DataResultable.fail(new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isFail());

        assertFalse(result.isOk());
        assertFalse(result.hasData());
        assertFalse(result.isWarning());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals("This is not supposed to happen. :)", result.message());

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    void testFailWithDataAndMessage() {
        final var result = DataResultable.fail("test data", "test message");

        assertTrue(result.isFail());
        assertTrue(result.hasData());

        assertFalse(result.isOk());
        assertFalse(result.isWarning());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals("test message", result.message());
        assertEquals("test data", result.data());
    }

    @Test
    void testFailWithDataAndMessageAndThrowable() {
        final var result = DataResultable.fail("test data", "test message", new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isFail());
        assertTrue(result.hasData());

        assertFalse(result.isOk());
        assertFalse(result.isWarning());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals("test message", result.message());
        assertEquals("test data", result.data());

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    void testFailWithMessageAndThrowable() {
        final var result = DataResultable.fail("test message", new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isFail());

        assertFalse(result.hasData());
        assertFalse(result.isOk());
        assertFalse(result.isWarning());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals("test message", result.message());

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    void testWarningWithMessage() {
        final var result = DataResultable.warning("test message");

        assertTrue(result.isWarning());

        assertFalse(result.isOk());
        assertFalse(result.hasData());
        assertFalse(result.isFail());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals("test message", result.message());
    }

    @Test
    void testWarningWithThrowable() {
        final var result = DataResultable.warning(new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isWarning());

        assertFalse(result.isOk());
        assertFalse(result.hasData());
        assertFalse(result.isFail());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals("This is not supposed to happen. :)", result.message());

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    void testWarningWithDataAndMessage() {
        final var result = DataResultable.warning("test data", "test message");

        assertTrue(result.isWarning());
        assertTrue(result.hasData());

        assertFalse(result.isOk());
        assertFalse(result.isFail());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals("test message", result.message());
        assertEquals("test data", result.data());
    }

    @Test
    void testWarningWithDataAndMessageAndThrowable() {
        final var result = DataResultable.warning("test data", "test message", new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isWarning());
        assertTrue(result.hasData());

        assertFalse(result.isOk());
        assertFalse(result.isFail());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals("test message", result.message());
        assertEquals("test data", result.data());

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    void testWarningWithMessageAndThrowable() {
        final var result = DataResultable.warning("test message", new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isWarning());

        assertFalse(result.hasData());
        assertFalse(result.isOk());
        assertFalse(result.isFail());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals("test message", result.message());

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    void testDataMono() {
        final var testMono = Mono.just("Test");

        assertNotNull(testMono);
        StepVerifier.create(DataResultable.mono(testMono))
                .assertNext(DataResultable::hasData)
                .verifyComplete();
    }

    @Test
    void testEmptyMono() {
        final var testMono = Mono.empty();

        assertNotNull(testMono);
        StepVerifier.create(DataResultable.mono(testMono))
                .assertNext(next -> {
                    if (next.hasData()) {
                        throw new AssertionError("empty mono cannot have data!");
                    }

                    if (next.isFail()) {
                        return;
                    }

                    if (next.isOk() || next.isWarning()) {
                        throw new AssertionError("data result should've failed.");
                    }
                })
                .verifyComplete();
    }
}
