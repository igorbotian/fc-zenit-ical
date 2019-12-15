package me.igorbotian.fczenit.ical.core.rfc2445;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Igor Botian
 */
public class Rfc2445Calendar {

    public static final String LINE_SEPARATOR = "\r\n";

    private final List<Rfc2445Param> params;

    public Rfc2445Calendar(List<Rfc2445Param> params) {
        Objects.requireNonNull(params);
        this.params = Collections.unmodifiableList(new ArrayList<>(params));
    }

    public List<Rfc2445Param> params() {
        return params;
    }

    @Override
    public int hashCode() {
        return Objects.hash(params);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        }

        Rfc2445Calendar cal = (Rfc2445Calendar) obj;
        return params.equals(cal.params);
    }

    @Override
    public String toString() {
        return params.stream()
                .map(Rfc2445Param::toString)
                .collect(Collectors.joining(LINE_SEPARATOR));
    }
}
