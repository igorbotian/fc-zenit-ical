package me.igorbotian.fczenit.ical.core.rfc2445;

import java.util.Objects;

/**
 * @author Igor Botian
 */
public class Rfc2445Param {

    public static final char DELIMITER = ':';

    private final String name;
    private final String value;

    Rfc2445Param(String name, String value) {
        this.name = Objects.requireNonNull(name);
        this.value = Objects.requireNonNull(value);
    }

    public String name() {
        return name;
    }

    public String value() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
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

        Rfc2445Param p = (Rfc2445Param) obj;
        return Objects.equals(name, p.name) && Objects.equals(value, p.value);
    }

    @Override
    public String toString() {
        return name + DELIMITER + value;
    }
}
