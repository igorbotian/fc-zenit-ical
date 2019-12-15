package me.igorbotian.fczenit.ical.core.rfc2445;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

/**
 * @author Igor Botian
 */
public class Rfc2445CalendarWriter implements AutoCloseable {

    private final BufferedWriter writer;

    public Rfc2445CalendarWriter(Writer writer) {
        Objects.requireNonNull(writer);
        this.writer = new BufferedWriter(writer);
    }

    public void writeCalendar(Rfc2445Calendar calendar) throws IOException {
        Objects.requireNonNull(calendar);
        writer.write(calendar.toString());
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
