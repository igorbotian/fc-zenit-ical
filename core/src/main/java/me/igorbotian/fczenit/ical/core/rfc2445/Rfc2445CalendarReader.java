package me.igorbotian.fczenit.ical.core.rfc2445;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Igor Botian
 */
public class Rfc2445CalendarReader implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Rfc2445CalendarReader.class);

    private final BufferedReader reader;
    private int linesRead;

    public Rfc2445CalendarReader(Reader reader) {
        Objects.requireNonNull(reader);
        this.reader = new BufferedReader(reader);
    }

    public Rfc2445Param readParam() throws IOException {
        String line = reader.readLine();

        if (line == null) {
            LOGGER.debug("All lines have been processed");
            return null;
        }

        linesRead++;
        return parseLine(line);
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
                            linesRead, line
                    )
            );
        } else if (delimPos == 0) {
            throw new Rfc2445FormatException(
                    String.format(
                            "Line %d is not a valid parameter line since it starts with the delimiter: %s",
                            linesRead, line
                    )
            );
        }
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
