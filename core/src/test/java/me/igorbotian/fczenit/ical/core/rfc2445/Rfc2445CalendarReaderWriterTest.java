package me.igorbotian.fczenit.ical.core.rfc2445;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

/**
 * @author Igor Botian
 */
class Rfc2445CalendarReaderWriterTest {

    @Test
    void rfc2445StringBeenReadAndWrittenKeepsTheSame() throws URISyntaxException, IOException {
        String calResource = "/zenit.ics";
        URL calUrl = getClass().getResource(calResource);
        String expected = String.join(
                "\r\n",
                Files.readAllLines(
                        Paths.get(calUrl.toURI()), StandardCharsets.UTF_8
                )
        );

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            InputStream calStream = getClass().getResourceAsStream(calResource);

            try (Rfc2445CalendarReader reader = new Rfc2445CalendarReader(new InputStreamReader(calStream))) {
                try (Rfc2445CalendarWriter writer = new Rfc2445CalendarWriter(new OutputStreamWriter(baos))) {
                    Rfc2445Param param;

                    while ((param = reader.readParam()) != null) {
                        writer.writeParam(param);
                    }
                }

                String actual = new String(baos.toByteArray(), StandardCharsets.UTF_8);
                assertEquals(expected, actual);
            }
        }
    }
}
