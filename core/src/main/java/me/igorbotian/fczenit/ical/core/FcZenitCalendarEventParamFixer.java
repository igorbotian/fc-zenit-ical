package me.igorbotian.fczenit.ical.core;

import java.util.Objects;
import java.util.StringTokenizer;

import me.igorbotian.fczenit.ical.core.rfc2445.Rfc2445Param;

/**
 * @author Igor Botian
 */
public class FcZenitCalendarEventParamFixer {

    private static final String SUMMARY_DELIMITER = "\\,";

    public Rfc2445Param fix(Rfc2445Param param) {
        Objects.requireNonNull(param);
        if ("SUMMARY".equals(param.name())) {
            return new Rfc2445Param(
                    param.name(), reverseSummaryChunks(param.value())
            );
        } else {
            return param;
        }
    }

    private String reverseSummaryChunks(String summary) {
        StringBuilder result = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(summary, SUMMARY_DELIMITER);
        String delim = SUMMARY_DELIMITER + " ";

        if (tokenizer.hasMoreElements()) {
            while (tokenizer.hasMoreElements()) {
                String chunk = ((String) tokenizer.nextElement()).trim();

                if (result.length() > 0) {
                    result.insert(0, delim);
                }

                result.insert(0, chunk);
            }

            return result.toString();
        }

        return summary;
    }
}
