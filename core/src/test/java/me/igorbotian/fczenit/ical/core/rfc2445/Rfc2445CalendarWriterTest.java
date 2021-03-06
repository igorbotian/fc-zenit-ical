package me.igorbotian.fczenit.ical.core.rfc2445;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * @author Igor Botian
 */
class Rfc2445CalendarWriterTest {

    @Test
    void emptyCalendarIsSerializedToEmptyString() throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Rfc2445CalendarWriter writer = new Rfc2445CalendarWriter(new OutputStreamWriter(baos));
            writer.close();
            String actual = new String(baos.toByteArray(), StandardCharsets.UTF_8);
            assertEquals("", actual);
        }
    }

    @Test
    void nonEmptyCalendarIsSerializedToValidRfc2445String() throws IOException {
        Rfc2445Calendar cal = TestRfc2445Calendar.newInstance();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (Rfc2445CalendarWriter writer = new Rfc2445CalendarWriter(new OutputStreamWriter(baos))) {
                writer.writeCalendar(cal);
            }

            String expected = cal.toString();
            String actual = new String(baos.toByteArray(), StandardCharsets.UTF_8);
            assertEquals(expected, actual);
        }
    }
}