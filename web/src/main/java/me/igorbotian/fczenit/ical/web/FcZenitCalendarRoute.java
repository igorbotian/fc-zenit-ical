package me.igorbotian.fczenit.ical.web;

import me.igorbotian.fczenit.ical.core.FcZenitCalendar;
import me.igorbotian.fczenit.ical.core.FcZenitCalendarEventParamFixer;
import me.igorbotian.fczenit.ical.core.rfc2445.Rfc2445Calendar;
import me.igorbotian.fczenit.ical.core.rfc2445.Rfc2445Param;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.List;

class FcZenitCalendarRoute implements Route {

    private final FcZenitCalendar zenitCalendar;
    private final FcZenitCalendarEventParamFixer calParamFixer;

    FcZenitCalendarRoute() {
        zenitCalendar = FcZenitCalendar.newInstance();
        calParamFixer = new FcZenitCalendarEventParamFixer();
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Rfc2445Calendar cal = zenitCalendar.downloadFromWebSite();
        Rfc2445Calendar fixed = fix(cal);
        response.type("text/calendar; charset=utf-8; method=REQUEST");
        response.header("pragma", "no-cache");
        return fixed.toString();
    }

    private Rfc2445Calendar fix(Rfc2445Calendar cal) {
        List<Rfc2445Param> fixed = new ArrayList<>(cal.params().size());
        cal.params().forEach(
                p -> fixed.add(calParamFixer.fix(p))
        );
        return new Rfc2445Calendar(fixed);
    }
}
