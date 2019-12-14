package me.igorbotian.fczenit.ical.core.rfc2445;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

/**
 * @author Igor Botian
 */
class Rfc2445CalendarReaderTest {

    @Test
    void readerReturnsNoParamsIfStringIsEmpty() throws IOException {
        try (Rfc2445CalendarReader reader = new Rfc2445CalendarReader(new StringReader(""))) {
            assertNull(reader.readNextParam());
        }
    }

    @Test
    void readerReturnsProperParametersIfStringStreamIsValid() throws IOException {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("BEGIN", "VCALENDAR");
        params.put("VERSION", "2.0");
        params.put("METHOD", "PUBLISH");
        params.put("X-WR-TIMEZONE", "UTC");
        params.put("CALSCALE", "GREGORIAN");
        params.put("END", "VCALENDAR");
        String rfc2445String = params.entrySet()
                .stream()
                .map(
                        entry -> new Rfc2445Param(entry.getKey(), entry.getValue()).toString()
                )
                .collect(Collectors.joining("\r\n"));

        try (Rfc2445CalendarReader reader = new Rfc2445CalendarReader(new StringReader(rfc2445String))) {
            for (Map.Entry<String, String> expected : params.entrySet()) {
                Rfc2445Param actual = reader.readNextParam();
                assertNotNull(actual);
                assertEquals(expected.getKey(), actual.name());
                assertEquals(expected.getValue(), actual.value());
            }

            assertNull(reader.readNextParam());
        }
    }
}