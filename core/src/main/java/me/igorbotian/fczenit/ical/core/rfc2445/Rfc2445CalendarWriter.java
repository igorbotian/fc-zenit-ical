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
    private boolean firstParamFlag = true;

    public Rfc2445CalendarWriter(Writer writer) {
        Objects.requireNonNull(writer);
        this.writer = new BufferedWriter(writer);
    }

    public void writeParam(Rfc2445Param param) throws IOException {
        Objects.requireNonNull(param);

        if (firstParamFlag) {
            firstParamFlag = false;
        } else {
            writer.write("\r\n");
        }

        writer.write(param.toString());
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
