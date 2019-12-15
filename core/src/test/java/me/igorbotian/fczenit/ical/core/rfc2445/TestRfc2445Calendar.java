package me.igorbotian.fczenit.ical.core.rfc2445;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Botian
 */
class TestRfc2445Calendar {

    private TestRfc2445Calendar() {
        throw new UnsupportedOperationException();
    }

    static Rfc2445Calendar newInstance() {
        List<Rfc2445Param> params = new ArrayList<>();
        params.add(new Rfc2445Param("BEGIN", "VCALENDAR"));
        params.add(new Rfc2445Param("VERSION", "2.0"));
        params.add(new Rfc2445Param("METHOD", "PUBLISH"));
        params.add(new Rfc2445Param("X-WR-TIMEZONE", "UTC"));
        params.add(new Rfc2445Param("CALSCALE", "GREGORIAN"));
        params.add(new Rfc2445Param("END", "VCALENDAR"));
        return new Rfc2445Calendar(params);
    }
}
