package com.wadpam.tracker.extractor;

import com.google.common.collect.ImmutableMap;
import com.wadpam.mardao.social.NetworkTemplate;
import com.wadpam.tracker.dao.DRaceDaoBean;
import com.wadpam.tracker.dao.DSplitDao;
import com.wadpam.tracker.domain.DParticipant;
import com.wadpam.tracker.domain.DRace;
import com.wadpam.tracker.domain.DSplit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Scrapes splits from Stokcholm Marathon's results page
 * @author osandstrom
 */
public class MarathonExtractor extends AbstractSplitsExtractor {

    @Override
    public Map<DSplit,DSplit> getPassedSplits(DRace race, 
        TreeMap<Long, DSplit> raceSplits, DParticipant participant) throws IOException {
        
        final HashMap<DSplit, DSplit> splitsMap = new HashMap<DSplit, DSplit>();
        
        URL url = new URL(race.getQueryUrl() + participant.getExtUserId());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream is = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        
        StringBuilder sb = new StringBuilder();
        String s;
        while (null != (s = br.readLine())) {
            sb.append(s);
            sb.append('\n');
        }
        br.close();
        conn.disconnect();
        final String page = sb.toString();
        
        for (DSplit raceSplit : raceSplits.values()) {
//            String dateTime = null;
//            if (DSplitDao.NAME_START.equals(raceSplit.getName())) {
//                p = Pattern.compile("<td class=\"desc\">Starttid</td>\\s*" +
//                "<td class=\"f-__starttime last\">([\\d\\:]+)</td>");
//                dateTime = raceDateTime; // raceDate + "T12:00:00";
//            }
//            else if (DSplitDao.NAME_FINISH.equals(raceSplit.getName())) {
//                p = Pattern.compile("<td class=\"desc\">Sluttid</td>\\s*" +
//                "<td class=\"time_day\">([\\d\\:]+)</td>");
//            }
//            else {
//                p = Pattern.compile("<td class=\"desc\">" + raceSplit.getName() + "</td>\\s*" +
//                "<td class=\"time_day\">([\\d\\:]+)</td>");
//            }
//            Matcher m = p.matcher(page);
//            String time = null;
//            if (m.find()) {
//                time = m.group(1);
//                dateTime = raceDate + 'T' + time;
//            }
//            else {
////                <tr class=" f-time_09 split">
////                <td class="desc">40K</td>
////                <td class="time">-</td>
////                <td class="diff right right">-</td>
////                <td class="min_km right opt right opt">-</td>
////                <td class="kmh right opt right opt last">-</td>
////                </tr>
//                if (DSplitDao.NAME_FINISH.equals(raceSplit.getName())) {
//                    p = Pattern.compile("<td class=\"desc\">Sluttid</td>\\s*" +
//                    "<td class=\"time\">([\\d\\:]+)</td>");
//                }
//                else {
//                    p = Pattern.compile("<td class=\"desc\">" + raceSplit.getName() + "</td>\\s*" +
//                    "<td class=\"time\">([\\d\\:]+)</td>");
//                }
//                 m = p.matcher(page);
//                if (m.find()) {
//                    time = m.group(1);
//                    SimpleDateFormat hmmss = new SimpleDateFormat("H:mm:ss");
//                    hmmss.setTimeZone(TimeZone.getTimeZone("GMT"));
//                    try {
//                        Date delta = hmmss.parse(time);
//                        dateTime = sdf.format(new Date(race.getStartDate().getTime() + delta.getTime()));
//                    }
//                    catch (ParseException pe) {
//                        LOGGER.warn("Parsing {}", time);
//                    }
//                }
//            }
            Date date = parseDate(page, raceSplit.getName(), race.getStartDate(), race.getTimeZone());
            if (null != date && date.before(new Date())) {
                final DSplit split = new DSplit();
                split.setTimestamp(date.getTime());
                split.setName(raceSplit.getName());
                splitsMap.put(raceSplit, split);
            }
        }
        
        return splitsMap;
    }
    
    protected static Date parseDate(String page, String splitName, Date startDate, String timeZone) {
        final SimpleDateFormat sdf = DRaceDaoBean.getDateFormat(timeZone);
        final String raceDateTime = sdf.format(startDate);
        final String raceDate = raceDateTime.substring(0, 10);
        
        Pattern p;
        String dateTime = null;
        if (DSplitDao.NAME_START.equals(splitName)) {
            p = Pattern.compile("<td class=\"desc\">Starttid</td>\\s*" +
            "<td class=\"f-__starttime last\">([\\d\\:]+)</td>");
            dateTime = raceDateTime; // raceDate + "T12:00:00";
        }
        else if (DSplitDao.NAME_FINISH.equals(splitName)) {
            p = Pattern.compile("<td class=\"desc\">Sluttid</td>\\s*" +
            "<td class=\"time_day\">([\\d\\:]+)</td>");
        }
        else {
            p = Pattern.compile("<td class=\"desc\">" + splitName + "</td>\\s*" +
            "<td class=\"time_day\">([\\d\\:]+)</td>");
        }
        Matcher m = p.matcher(page);
        String time = null;
        if (m.find()) {
            time = m.group(1);
            dateTime = raceDate + 'T' + time;
        }
        else {
//                <tr class=" f-time_09 split">
//                <td class="desc">40K</td>
//                <td class="time">-</td>
//                <td class="diff right right">-</td>
//                <td class="min_km right opt right opt">-</td>
//                <td class="kmh right opt right opt last">-</td>
//                </tr>
            if (DSplitDao.NAME_FINISH.equals(splitName)) {
                p = Pattern.compile("<td class=\"desc\">Sluttid</td>\\s*" +
                "<td class=\"time\">([\\d\\.]+)</td>");
            }
            else {
                p = Pattern.compile("<td class=\"desc\">" + splitName + "</td>\\s*" +
                "<td class=\"time\">([\\d\\.]+)</td>");
            }
             m = p.matcher(page);
            if (m.find()) {
                time = m.group(1);
                SimpleDateFormat hmmss = new SimpleDateFormat("H.mm.ss");
                hmmss.setTimeZone(TimeZone.getTimeZone("GMT"));
                try {
                    Date delta = hmmss.parse(time);
                    dateTime = sdf.format(new Date(startDate.getTime() + delta.getTime()));
                }
                catch (ParseException pe) {
                    LOGGER.warn("Parsing {}", time);
                }
            }
        }
        if (null != dateTime) {
            try {
                Date date = sdf.parse(dateTime);
                return date;
            } catch (ParseException ex) {
                LOGGER.warn("Could not parse dateTime {}", dateTime);
            }
        }
        return null;
    }
    
    protected static String getEvent(final String url) {
        return "STHM";
    }
    
    @Override
    public TreeMap<String,String> searchForParticipants(DRace race, String searchName, String firstName) {
        
        final String event = getEvent(race.getQueryUrl());
        final String page = getSearchResponse(event, searchName, firstName);
        TreeMap<String, String> map = matchForParticipants(page);
        
        return map;
    }

    protected static TreeMap<String, String> matchForParticipants(final String page) {
//<tr>
//  <td>&nbsp;</td>
//  <td>8049</td>
//  <td>&#187; <a href="?content=detail&amp;fpid=startlist_list&amp;pid=startlist_list&amp;idp=9999991386F5940000053DCA&amp;lang=SE&amp;event=STHM">Bo Sandström (SWE)</a></td>
//  <td>Bromma</td>
//  <td>75</td>
//  <td class="right">&nbsp;</td>
//  <td><a target="_blank" href="http://www.marathonfoto.com/index.cfm?RaceOID=23572013S1&amp;LastName=Sandström&amp;BibNumber=8049"><img src="http://static.r.mikatiming.de/stages/blue/images/icon_photo_small.gif" alt="" title="Foto"/></a></td>
//  <td><a target="_blank" href="http://mysports.tv/events/SM14/redirect.asp?r=8049"><img src="http://static.r.mikatiming.de/stages/blue/images/camera.gif" alt="" title="MySports TV"/></a></td>
//  <td style="border-right:0;"><a href="?lang=SE&amp;pgid=&amp;pid=startlist_list&amp;search%5Bname%5D=Sandstr%C3%B6m&amp;search%5Bage_class%5D=%25&amp;search%5Bsex%5D=%25&amp;search%5Bstart_group%5D=%25&amp;search%5Bnation%5D=%25&amp;search%5Bstate%5D=%25&amp;search%5Bcity%5D=%25&amp;search_sort=name&amp;event=STHM&amp;favorite_add=9999991386F5940000053DCA"><img src="http://static.r.mikatiming.de/stages/blue/images/icon_favorite_add.png" alt="Lägg till favorit" title="Lägg till favorit" /></a></td>
//</tr>
        final String regex = "<td>([^<]*)</td>\\s*<td>[^\\n]*;idp=([^&]+)&amp;[^>]*>([^<]+)</a></td>\\s*<td>([^<]*)</td>";
        final Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(page);
        final TreeMap<String, String> map = new TreeMap<String, String>();
        while (m.find()) {
            // System.out.println(m.group(1) + " " + m.group(2));
            map.put('#' + m.group(1) + ' ' + m.group(3) + ", " + m.group(4), m.group(2));
        }
        return map;
    }

    protected static String getSearchResponse(String event, String searchName, String firstName) {
        NetworkTemplate templ = new NetworkTemplate();
        String url = "http://results.marathon.se/2014/index.php?pid=startlist_list"; //http://goteborgsvarvet.r.mikatiming.de/2014/"; //http://results.vasaloppet.se/2012/";
        ImmutableMap<String, String> headers = ImmutableMap.<String, String>builder()
                .put(NetworkTemplate.CONTENT_TYPE, NetworkTemplate.MIME_FORM) //"multipart/form-data; boundary=----WebKitFormBoundarywfmgztRSGXfO03xA")
                .build();
        ImmutableMap<String, String> request = ImmutableMap.<String, String>builder()
                .put("pid", "search")
                //.put("format", "csv")
                .put("lang", "SE")
                //.put("content", "export_list")
                .put("event_main_group", "2014")
                .put("event", event)
                .put("search[name]", searchName)
                .put("search[firstname]", firstName)
                .put("num_results", "250")
                .build();
        LOGGER.info("Search {}", request);
        String page = templ.post(url, headers, request, String.class);
        return page;
    }

    public static void main(String args[]) {
          String page = getSearchResponse("STHM", "Sandstr", "Ola");
//          System.out.println(page);
//          String page = "<td>8049</td>\n" +
//"  <td>&#187; <a href=\"?content=detail&amp;fpid=startlist_list&amp;pid=startlist_list&amp;idp=9999991386F5940000053DCA&amp;lang=SE&amp;event=STHM\">Bo Sandström (SWE)</a></td>\n" +
//"  <td>Bromma</td>";
        TreeMap<String, String> map = matchForParticipants(page);
        System.out.println(map);
    }

}
