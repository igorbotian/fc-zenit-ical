package me.igorbotian.fczenit.ical.web;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.igorbotian.fczenit.ical.core.FcZenitCalendar;
import me.igorbotian.fczenit.ical.core.FcZenitCalendarEventParamFixer;
import me.igorbotian.fczenit.ical.core.rfc2445.Rfc2445Calendar;
import me.igorbotian.fczenit.ical.core.rfc2445.Rfc2445Param;

/**
 * @author Igor Botian
 */
@WebServlet(
        name = "FcZenitCalendarServlet",
        urlPatterns = {"/zenit.ics"}
)
public class FcZenitCalendarServlet extends HttpServlet {

    private FcZenitCalendar zenitCalendar;
    private FcZenitCalendarEventParamFixer calParamFixer;

    @Override
    public void init() {
        zenitCalendar = FcZenitCalendar.newInstance();
        calParamFixer = new FcZenitCalendarEventParamFixer();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        process(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        process(resp);
    }

    private void process(HttpServletResponse resp) throws IOException {
        Rfc2445Calendar cal = zenitCalendar.downloadFromWebSite();
        Rfc2445Calendar fixed = fix(cal);
        String response = fixed.toString();
        resp.setContentType("text/calendar; charset=utf-8; method=REQUEST");
        resp.setHeader("pragma", "no-cache");
        resp.getWriter().print(response);
    }

    private Rfc2445Calendar fix(Rfc2445Calendar cal) {
        List<Rfc2445Param> fixed = new ArrayList<>(cal.params().size());
        cal.params().forEach(
                p -> fixed.add(calParamFixer.fix(p))
        );
        return new Rfc2445Calendar(fixed);
    }
}
