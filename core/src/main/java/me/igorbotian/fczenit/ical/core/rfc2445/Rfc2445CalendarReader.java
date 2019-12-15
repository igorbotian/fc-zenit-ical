package me.igorbotian.fczenit.ical.core.rfc2445;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Igor Botian
 */
public class Rfc2445CalendarReader implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Rfc2445CalendarReader.class);

    private final LineNumberReader reader;

    public Rfc2445CalendarReader(Reader reader) {
        Objects.requireNonNull(reader);
        this.reader = new LineNumberReader(
                new BufferedReader(reader)
        );
    }

    public Rfc2445Calendar readCalendar() throws IOException {
        List<Rfc2445Param> params = new ArrayList<>();
        Rfc2445Param param;

        while ((param = readParam()) != null) {
            params.add(param);
        }

        return new Rfc2445Calendar(params);
    }

    private Rfc2445Param readParam() throws IOException {
        String line = reader.readLine();

        if (line == null) {
            LOGGER.debug("All lines have been processed");
            return null;
        }

        return parseLine(line.trim());
    }

    private Rfc2445Param parseLine(String line) {
        int pos = line.indexOf(Rfc2445Param.DELIMITER);
        validate(line, pos);
        String name = line.substring(0, pos);
        String value = pos == line.length() - 1 ? "" : line.substring(pos + 1);
        return new Rfc2445Param(name, value);
    }

    private void validate(String line, int delimPos) {
        if (delimPos == -1) {
            throw new Rfc2445FormatException(
                    String.format(
                            "Line %d is not a valid parameter line since it doesn't contain the delimiter: %s",
                            reader.getLineNumber(), line
                    )
            );
        } else if (delimPos == 0) {
            throw new Rfc2445FormatException(
                    String.format(
                            "Line %d is not a valid parameter line since it starts with the delimiter: %s",
                            reader.getLineNumber(), line
                    )
            );
        }
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
