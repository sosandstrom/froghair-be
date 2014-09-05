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
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Scrapes splits from Vasaloppet's results page
 * @author osandstrom
 */
public class VasaloppsExtractor extends AbstractSplitsExtractor {

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
        
        final SimpleDateFormat sdf = DRaceDaoBean.getDateFormat(race.getTimeZone());
        final String raceDateTime = sdf.format(race.getStartDate());
        final String raceDate = raceDateTime.substring(0, 10);
        
        Pattern p;
        for (DSplit raceSplit : raceSplits.values()) {
            if (DSplitDao.NAME_START.equals(raceSplit.getName())) {
                p = Pattern.compile("<td class=\"desc\">Starttid</td>\\s*" +
                "<td class=\"f-__starttime last\">([\\d\\:]+)</td>");
            }
            else if (DSplitDao.NAME_FINISH.equals(raceSplit.getName())) {
                p = Pattern.compile("<td class=\"desc\">Mål</td>\\s*" +
                "<td class=\"time_day\">([\\d\\:]+)</td>");
            }
            else {
                p = Pattern.compile("<td class=\"desc\">" + raceSplit.getName() + "</td>\\s*" +
                "<td class=\"time_day\">([\\d\\:]+)</td>");
            }
            Matcher m = p.matcher(page);
            String time = null;
            if (m.find()) {
                time = m.group(1);
                String dateTime = raceDate + 'T' + time;
                try {
                    Date date = sdf.parse(dateTime);
                    final DSplit split = new DSplit();
                    split.setTimestamp(date.getTime());
                    split.setName(raceSplit.getName());
                    splitsMap.put(raceSplit, split);
                    
                    //LOGGER.debug("time is {} for pattern {}", dateTime, p);
                } catch (ParseException ex) {
                    LOGGER.warn("Could not parse dateTime {}", dateTime);
                }
            }
        }
        
        return splitsMap;
    }
    
    protected static String getEvent(final String url) {
        String event = null;
        final String prefix = "&event=";
        if (null != url) {
            int beginIndex = url.lastIndexOf(prefix);
            if (0 < beginIndex) {
                int endIndex = url.indexOf('&', beginIndex+prefix.length());
                if (beginIndex < endIndex) {
                    event = url.substring(beginIndex+prefix.length(), endIndex);
                }
                else {
                    event = url.substring(beginIndex+prefix.length());
                }
            }
        }
        return event;
    }
    
    @Override
    public TreeMap<String,String> searchForParticipants(DRace race, String searchName, String firstName) {
        
        final String event = getEvent(race.getQueryUrl());
        final String page = getSearchResponse(event, searchName, firstName);
        TreeMap<String, String> map = matchForParticipants(page);
        
        return map;
    }

    protected static TreeMap<String, String> matchForParticipants(final String page) {
        // <td>15827</td>
        // <td><img src="http://static.r.mikatiming.de/stages/blue/images/flags/16x16/SWE.png" alt="SWE" title="SWE"/></td>
        // <td>&#187; <a href="?content=detail&amp;fpid=search&amp;pid=search&amp;idp=9999991678885900001BAE65&amp;lang=SE&amp;event=ÖSM_9999991678885900000002FD">Nilsson, Alf (SWE)</a></td>
        // <td>H60</td>
        final String regex = "<td>([^<]*)</td>\\s*<td><img[^\\n]*\\n[^\\n]*;idp=([^&]+)&amp;[^>]*>([^<]+)[^\\n]*\\n\\s*<td>([^<]*)</td>";
        final Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(page);
        final TreeMap<String, String> map = new TreeMap<String, String>();
        while (m.find()) {
//            System.out.println(m.group(3) + ' ' + m.group(4) + " #" + m.group(1) + ", " + m.group(2));
            map.put(m.group(3) + ' ' + m.group(4) + " #" + m.group(1), m.group(2));
        }
        return map;
    }

    protected static String getSearchResponse(String event, String searchName, String firstName) {
        NetworkTemplate templ = new NetworkTemplate();
        String url = "http://goteborgsvarvet.r.mikatiming.de/2014/"; //http://results.vasaloppet.se/2012/";
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
          String page = getSearchResponse("ÖSM_9999991678885900000002FD", "Nilsson", "");
//        String page =
//        "<td>15827</td>\n" +
//        "<td><img src=\"http://static.r.mikatiming.de/stages/blue/images/flags/16x16/SWE.png\" alt=\"SWE\" title=\"SWE\"/></td>\n" +
//        "<td>&#187; <a href=\"?content=detail&amp;fpid=search&amp;pid=search&amp;idp=9999991678885900001BAE65&amp;lang=SE&amp;event=ÖSM_9999991678885900000002FD\">Nilsson, Alf (SWE)</a></td>\n" +
//        "<td>H60</td>";
        TreeMap<String, String> map = matchForParticipants(page);
    }

}
