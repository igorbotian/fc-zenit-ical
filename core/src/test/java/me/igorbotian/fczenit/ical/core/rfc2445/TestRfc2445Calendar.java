package me.igorbotian.fczenit.ical.core.rfc2445;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Igor Botian
 */
class TestRfc2445Calendar {

    private static final Map<String, String> PARAMS;

    static {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("BEGIN", "VCALENDAR");
        params.put("VERSION", "2.0");
        params.put("METHOD", "PUBLISH");
        params.put("X-WR-TIMEZONE", "UTC");
        params.put("CALSCALE", "GREGORIAN");
        params.put("END", "VCALENDAR");
        PARAMS = Collections.unmodifiableMap(params);
    }

    Map<String, String> asMap() {
        return PARAMS;
    }

    List<Rfc2445Param> asParams() {
        return asMap().entrySet()
                .stream()
                .map(entry -> new Rfc2445Param(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    String asString() {
        return asParams().stream()
                .map(Rfc2445Param::toString)
                .collect(Collectors.joining("\r\n"));
    }
}
