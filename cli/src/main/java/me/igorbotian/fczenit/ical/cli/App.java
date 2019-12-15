package me.igorbotian.fczenit.ical.cli;

import java.io.IOException;

import me.igorbotian.fczenit.ical.core.FcZenitCalendar;
import me.igorbotian.fczenit.ical.core.FcZenitCalendarEventParamFixer;
import me.igorbotian.fczenit.ical.core.rfc2445.Rfc2445Calendar;
import me.igorbotian.fczenit.ical.core.rfc2445.Rfc2445Param;

/**
 * @author Igor Botian
 */
public class App {

    public static void main(String[] args) throws IOException {
        Rfc2445Calendar cal = FcZenitCalendar.newInstance().downloadFromWebSite();
        FcZenitCalendarEventParamFixer fixer = new FcZenitCalendarEventParamFixer();

        for (Rfc2445Param param : cal.params()) {
            Rfc2445Param fixed = fixer.fix(param);
            System.out.println(fixed);
        }
    }
}
