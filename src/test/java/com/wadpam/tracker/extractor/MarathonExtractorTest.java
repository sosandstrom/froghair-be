/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wadpam.tracker.extractor;

import com.wadpam.tracker.dao.DRaceDao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author osandstrom
 */
public class MarathonExtractorTest {

    Date startDate;
    SimpleDateFormat SDF;
    
    @Before
    public void setUp() throws ParseException {
        SDF = new SimpleDateFormat(DRaceDao.DATE_TIME_FORMAT);
        startDate = SDF.parse("2014-05-31T12:00:00");
    }
    
    @Test
    public void testParseTimeNotPassed() {
        String page = "<tr class=\" f-time_09 split\">\n" +
"<td class=\"desc\">40K</td>\n" +
"<td class=\"time\">-</td>\n" +
"<td class=\"diff right right\">-</td>\n" +
"<td class=\"min_km right opt right opt\">-</td>\n" +
"<td class=\"kmh right opt right opt last\">-</td>\n" +
"</tr>";
        Date date = MarathonExtractor.parseDate(page, "40K", startDate, "Europe/Stockholm");
        assertNull(date);
    }
    @Test
    public void testParseTime() {
        String page = "<tr class=\" f-time_09 split\">\n" +
"<td class=\"desc\">40K</td>\n" +
"<td class=\"time\">3.34.51</td>\n" +
"<td class=\"diff right right\">-</td>\n" +
"<td class=\"min_km right opt right opt\">-</td>\n" +
"<td class=\"kmh right opt right opt last\">-</td>\n" +
"</tr>";
        Date date = MarathonExtractor.parseDate(page, "40K", startDate, "Europe/Stockholm");
        String actual = SDF.format(date);
        assertEquals("2014-05-31T15:34:51", actual);
    }
}
