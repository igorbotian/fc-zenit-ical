package me.igorbotian.fczenit.ical.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import me.igorbotian.fczenit.ical.core.rfc2445.Rfc2445Param;

/**
 * @author Igor Botian
 */
class FcZenitCalendarEventParamFixerTest {

    private FcZenitCalendarEventParamFixer fixer;

    @BeforeEach
    void setUp() {
        fixer = new FcZenitCalendarEventParamFixer();
    }

    @Test
    void paramKeepsTheSameIfItIsNotSummary() {
        Rfc2445Param param = new Rfc2445Param("BEGIN", "VEVENT");
        assertSame(param, fixer.fix(param));
    }

    @Test
    void summaryParamValueIsFixed() {
        String summary = "Российская Премьер-Лига\\, 18 тур\\, Урал – Зенит";
        String expected = "Урал – Зенит\\, 18 тур\\, Российская Премьер-Лига";
        Rfc2445Param param = new Rfc2445Param("SUMMARY", summary);
        Rfc2445Param fixed = fixer.fix(param);
        String actual = fixed.value();
        assertEquals(expected, actual);
    }

    @Test
    void summaryParamValueKeepsTheSameIfItNotChunked() {
        String summary = "Summary without commas";
        Rfc2445Param param = new Rfc2445Param("SUMMARY", summary);
        Rfc2445Param fixed = fixer.fix(param);
        String actual = fixed.value();
        assertEquals(summary, actual);
    }
}