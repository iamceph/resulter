package cz.iamceph.resulter.core;

import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cz.iamceph.resulter.core.api.DataResultable;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Simple testing for DataResultable.
 */
public class DataResultTest {

    @Test
    public void testOkWithData() {
        final var result = DataResult.ok("test data");

        Assertions.assertTrue(result.isOk());
        Assertions.assertTrue(result.hasData());

        Assertions.assertFalse(result.isFail());
        Assertions.assertFalse(result.isWarning());

        Assertions.assertNull(result.error());
        Assertions.assertNotNull(result.message());

        Assertions.assertEquals(result.data(), "test data");
        Assertions.assertEquals(result.message(), "Ok.");
    }

    @Test
    public void testOkWithDataAndMessage() {
        final var result = DataResult.ok("test data", "test message");

        Assertions.assertTrue(result.isOk());
        Assertions.assertTrue(result.hasData());

        Assertions.assertFalse(result.isFail());
        Assertions.assertFalse(result.isWarning());

        Assertions.assertNull(result.error());
        Assertions.assertNotNull(result.message());

        Assertions.assertEquals(result.data(), "test data");
        Assertions.assertEquals(result.message(), "test message");
    }

    @Test
    public void testFailWithMessage() {
        final var result = DataResult.fail("test message");

        Assertions.assertTrue(result.isFail());

        Assertions.assertFalse(result.isOk());
        Assertions.assertFalse(result.hasData());
        Assertions.assertFalse(result.isWarning());

        Assertions.assertNull(result.error());
        Assertions.assertNotNull(result.message());

        Assertions.assertEquals(result.message(), "test message");
    }

    @Test
    public void testFailWithThrowable() {
        final var result = DataResult.fail(new UnsupportedOperationException("This is not supposed to happen. :)"));

        Assertions.assertTrue(result.isFail());

        Assertions.assertFalse(result.isOk());
        Assertions.assertFalse(result.hasData());
        Assertions.assertFalse(result.isWarning());

        Assertions.assertNotNull(result.error());
        Assertions.assertNotNull(result.message());

        Assertions.assertEquals(result.message(), "This is not supposed to happen. :)");

        Assertions.assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    public void testFailWithDataAndMessage() {
        final var result = DataResult.fail("test data", "test message");

        Assertions.assertTrue(result.isFail());
        Assertions.assertTrue(result.hasData());

        Assertions.assertFalse(result.isOk());
        Assertions.assertFalse(result.isWarning());

        Assertions.assertNull(result.error());
        Assertions.assertNotNull(result.message());

        Assertions.assertEquals(result.message(), "test message");
        Assertions.assertEquals(result.data(), "test data");
    }

    @Test
    public void testFailWithDataAndMessageAndThrowable() {
        final var result = DataResult.fail("test data", "test message", new UnsupportedOperationException("This is not supposed to happen. :)"));

        Assertions.assertTrue(result.isFail());
        Assertions.assertTrue(result.hasData());

        Assertions.assertFalse(result.isOk());
        Assertions.assertFalse(result.isWarning());

        Assertions.assertNotNull(result.error());
        Assertions.assertNotNull(result.message());

        Assertions.assertEquals(result.message(), "test message");
        Assertions.assertEquals(result.data(), "test data");

        Assertions.assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    public void testFailWithMessageAndThrowable() {
        final var result = DataResult.fail("test message", new UnsupportedOperationException("This is not supposed to happen. :)"));

        Assertions.assertTrue(result.isFail());

        Assertions.assertFalse(result.hasData());
        Assertions.assertFalse(result.isOk());
        Assertions.assertFalse(result.isWarning());

        Assertions.assertNotNull(result.error());
        Assertions.assertNotNull(result.message());

        Assertions.assertEquals(result.message(), "test message");

        Assertions.assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    public void testWarningWithMessage() {
        final var result = DataResult.warning("test message");

        Assertions.assertTrue(result.isWarning());

        Assertions.assertFalse(result.isOk());
        Assertions.assertFalse(result.hasData());
        Assertions.assertFalse(result.isFail());

        Assertions.assertNull(result.error());
        Assertions.assertNotNull(result.message());

        Assertions.assertEquals(result.message(), "test message");
    }

    @Test
    public void testWarningWithThrowable() {
        final var result = DataResult.warning(new UnsupportedOperationException("This is not supposed to happen. :)"));

        Assertions.assertTrue(result.isWarning());

        Assertions.assertFalse(result.isOk());
        Assertions.assertFalse(result.hasData());
        Assertions.assertFalse(result.isFail());

        Assertions.assertNotNull(result.error());
        Assertions.assertNotNull(result.message());

        Assertions.assertEquals(result.message(), "This is not supposed to happen. :)");

        Assertions.assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    public void testWarningWithDataAndMessage() {
        final var result = DataResult.warning("test data", "test message");

        Assertions.assertTrue(result.isWarning());
        Assertions.assertTrue(result.hasData());

        Assertions.assertFalse(result.isOk());
        Assertions.assertFalse(result.isFail());

        Assertions.assertNull(result.error());
        Assertions.assertNotNull(result.message());

        Assertions.assertEquals(result.message(), "test message");
        Assertions.assertEquals(result.data(), "test data");
    }

    @Test
    public void testWarningWithDataAndMessageAndThrowable() {
        final var result = DataResult.warning("test data", "test message", new UnsupportedOperationException("This is not supposed to happen. :)"));

        Assertions.assertTrue(result.isWarning());
        Assertions.assertTrue(result.hasData());

        Assertions.assertFalse(result.isOk());
        Assertions.assertFalse(result.isFail());

        Assertions.assertNotNull(result.error());
        Assertions.assertNotNull(result.message());

        Assertions.assertEquals(result.message(), "test message");
        Assertions.assertEquals(result.data(), "test data");

        Assertions.assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    public void testWarningWithMessageAndThrowable() {
        final var result = DataResult.warning("test message", new UnsupportedOperationException("This is not supposed to happen. :)"));

        Assertions.assertTrue(result.isWarning());

        Assertions.assertFalse(result.hasData());
        Assertions.assertFalse(result.isOk());
        Assertions.assertFalse(result.isFail());

        Assertions.assertNotNull(result.error());
        Assertions.assertNotNull(result.message());

        Assertions.assertEquals(result.message(), "test message");

        Assertions.assertSame(Objects.requireNonNull(result.error()).getClass(), UnsupportedOperationException.class);
    }

    @Test
    public void testDataMono() {
        final var testMono = Mono.just("Test");

        StepVerifier.create(DataResult.mono(testMono))
                .assertNext(DataResultable::hasData)
                .verifyComplete();
    }

    @Test
    public void testEmptyMono() {
        final var testMono = Mono.empty();

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
