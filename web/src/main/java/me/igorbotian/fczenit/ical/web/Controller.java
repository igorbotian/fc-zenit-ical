package me.igorbotian.fczenit.ical.web;

import me.igorbotian.fczenit.ical.core.FcZenitCalendar;
import me.igorbotian.fczenit.ical.core.FcZenitCalendarEventParamFixer;
import me.igorbotian.fczenit.ical.core.rfc2445.Rfc2445Calendar;
import me.igorbotian.fczenit.ical.core.rfc2445.Rfc2445Param;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Controller {

    private final FcZenitCalendar zenitCalendar;
    private final FcZenitCalendarEventParamFixer calParamFixer;

    Controller() {
        zenitCalendar = FcZenitCalendar.newInstance();
        calParamFixer = new FcZenitCalendarEventParamFixer();
    }

    public String handleGet(Request request, Response response) throws IOException {
        Objects.requireNonNull(request);
        Objects.requireNonNull(response);

        Rfc2445Calendar cal = zenitCalendar.downloadFromWebSite();
        Rfc2445Calendar fixedCal = fix(cal);
        response.type("text/calendar; charset=utf-8; method=REQUEST");
        response.header("pragma", "no-cache");

        return fixedCal.toString();
    }

    private Rfc2445Calendar fix(Rfc2445Calendar cal) {
        List<Rfc2445Param> fixed = new ArrayList<>(cal.params().size());
        cal.params().forEach(
                p -> fixed.add(calParamFixer.fix(p))
        );
        return new Rfc2445Calendar(fixed);
    }
}
