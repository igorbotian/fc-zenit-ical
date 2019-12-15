package me.igorbotian.fczenit.ical.core;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import me.igorbotian.fczenit.ical.core.rfc2445.Rfc2445Calendar;
import me.igorbotian.fczenit.ical.core.rfc2445.Rfc2445CalendarReader;

/**
 * @author Igor Botian
 */
public class FcZenitCalendar {

    private static final String CALENDAR_URL = "https://fc-zenit.ru/res/ical/ical.php?actionclass=ical&action=getfile";

    private URL url;

    private FcZenitCalendar(URL url) {
        this.url = Objects.requireNonNull(url);
    }

    public static FcZenitCalendar newInstance() {
        try {
            return new FcZenitCalendar(
                    new URL(CALENDAR_URL)
            );
        } catch (MalformedURLException e) {
            throw new IllegalStateException(
                    "Cannot initialize a URL to FC Zenit calendar", e
            );
        }
    }

    public Rfc2445Calendar downloadFromWebSite() throws IOException {
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        try {
            Charset contentEncoding = getContentEncoding(conn);

            try (InputStreamReader isr = new InputStreamReader(conn.getInputStream(), contentEncoding)) {
                try (Rfc2445CalendarReader reader = new Rfc2445CalendarReader(isr)) {
                    return reader.readCalendar();
                }
            }
        } finally {
            conn.disconnect();
        }
    }

    private Charset getContentEncoding(URLConnection conn) {
        String encoding = conn.getContentEncoding();
        return encoding != null ? Charset.forName(encoding) : StandardCharsets.UTF_8;
    }
}
