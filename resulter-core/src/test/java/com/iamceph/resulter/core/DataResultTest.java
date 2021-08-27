package com.iamceph.resulter.core;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import com.iamceph.resulter.core.api.DataResultable;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Simple testing for DataResultable.
 */
public class DataResultTest {

    @Test
    public void testOkWithData() {
        final var result = DataResult.ok("test data");

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
    public void testOkWithDataAndMessage() {
        final var result = DataResult.ok("test data", "test message");

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
    public void testFailWithMessage() {
        final var result = DataResult.fail("test message");

        assertTrue(result.isFail());

        assertFalse(result.isOk());
        assertFalse(result.hasData());
        assertFalse(result.isWarning());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "test message");
    }

    @Test
    public void testFailWithThrowable() {
        final var result = DataResult.fail(new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isFail());

        assertFalse(result.isOk());
        assertFalse(result.hasData());
        assertFalse(result.isWarning());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "This is not supposed to happen. :)");

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    public void testFailWithDataAndMessage() {
        final var result = DataResult.fail("test data", "test message");

        assertTrue(result.isFail());
        assertTrue(result.hasData());

        assertFalse(result.isOk());
        assertFalse(result.isWarning());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "test message");
        assertEquals(result.data(), "test data");
    }

    @Test
    public void testFailWithDataAndMessageAndThrowable() {
        final var result = DataResult.fail("test data", "test message", new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isFail());
        assertTrue(result.hasData());

        assertFalse(result.isOk());
        assertFalse(result.isWarning());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "test message");
        assertEquals(result.data(), "test data");

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    public void testFailWithMessageAndThrowable() {
        final var result = DataResult.fail("test message", new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isFail());

        assertFalse(result.hasData());
        assertFalse(result.isOk());
        assertFalse(result.isWarning());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "test message");

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    public void testWarningWithMessage() {
        final var result = DataResult.warning("test message");

        assertTrue(result.isWarning());

        assertFalse(result.isOk());
        assertFalse(result.hasData());
        assertFalse(result.isFail());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "test message");
    }

    @Test
    public void testWarningWithThrowable() {
        final var result = DataResult.warning(new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isWarning());

        assertFalse(result.isOk());
        assertFalse(result.hasData());
        assertFalse(result.isFail());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "This is not supposed to happen. :)");

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    public void testWarningWithDataAndMessage() {
        final var result = DataResult.warning("test data", "test message");

        assertTrue(result.isWarning());
        assertTrue(result.hasData());

        assertFalse(result.isOk());
        assertFalse(result.isFail());

        assertNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "test message");
        assertEquals(result.data(), "test data");
    }

    @Test
    public void testWarningWithDataAndMessageAndThrowable() {
        final var result = DataResult.warning("test data", "test message", new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isWarning());
        assertTrue(result.hasData());

        assertFalse(result.isOk());
        assertFalse(result.isFail());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "test message");
        assertEquals(result.data(), "test data");

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    public void testWarningWithMessageAndThrowable() {
        final var result = DataResult.warning("test message", new UnsupportedOperationException("This is not supposed to happen. :)"));

        assertTrue(result.isWarning());

        assertFalse(result.hasData());
        assertFalse(result.isOk());
        assertFalse(result.isFail());

        assertNotNull(result.error());
        assertNotNull(result.message());

        assertEquals(result.message(), "test message");

        assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    public void testDataMono() {
        final var testMono = Mono.just("Test");

        assertNotNull(testMono);
        StepVerifier.create(DataResult.mono(testMono))
                .assertNext(DataResultable::hasData)
                .verifyComplete();
    }

    @Test
    public void testEmptyMono() {
        final var testMono = Mono.empty();

        assertNotNull(testMono);
        StepVerifier.create(DataResult.mono(testMono))
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
