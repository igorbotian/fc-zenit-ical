package me.igorbotian.fczenit.ical.core.rfc2445;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

/**
 * @author Igor Botian
 */
class Rfc2445CalendarReaderTest {

    @Test
    void readerReturnsNoParamsIfStringIsEmpty() throws IOException {
        try (Rfc2445CalendarReader reader = new Rfc2445CalendarReader(new StringReader(""))) {
            assertNull(reader.readParam());
        }
    }

    @Test
    void readerReturnsProperParametersIfStringStreamIsValid() throws IOException {
        TestRfc2445Calendar cal = new TestRfc2445Calendar();

        try (Rfc2445CalendarReader reader = new Rfc2445CalendarReader(new StringReader(cal.asString()))) {
            for (Rfc2445Param expected : cal.asParams()) {
                Rfc2445Param actual = reader.readParam();
                assertNotNull(actual);
                assertEquals(expected, actual);
            }

            assertNull(reader.readParam());
        }
    }
}