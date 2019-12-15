package me.igorbotian.fczenit.ical.core.rfc2445;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
            Rfc2445Calendar cal = reader.readCalendar();
            assertTrue(cal.params().isEmpty());
        }
    }

    @Test
    void readerReturnsProperParametersIfStringStreamIsValid() throws IOException {
        Rfc2445Calendar cal = TestRfc2445Calendar.newInstance();

        try (Rfc2445CalendarReader reader = new Rfc2445CalendarReader(new StringReader(cal.toString()))) {
            Rfc2445Calendar actual = reader.readCalendar();
            assertEquals(cal, actual);
        }
    }
}