package com.wadpam.tracker.api;

import com.google.appengine.api.utils.SystemProperty;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.wadpam.tracker.dao.DParticipantDao;
import com.wadpam.tracker.dao.DRaceDao;
import static com.wadpam.tracker.dao.DRaceDao.DATE_TIME_FORMAT;
import com.wadpam.tracker.dao.DRaceDaoBean;
import com.wadpam.tracker.dao.DSplitDao;
import com.wadpam.tracker.domain.DParticipant;
import com.wadpam.tracker.domain.DRace;
import com.wadpam.tracker.domain.DSplit;
import com.wadpam.tracker.domain.TrackPoint;
import com.wadpam.tracker.extractor.DemoExtractor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author osandstrom
 */
@Path("pub")
@Produces(MediaType.APPLICATION_JSON)
public class PublicResource {
    
    static final Logger LOGGER = LoggerFactory.getLogger(PublicResource.class);

    @Inject
    private HttpServletRequest request;
    
    @Inject
    private DParticipantDao participantDao;
    
    private final DRaceDao raceDao;
    
    @Inject
    private DSplitDao splitDao;
    
    @Inject
    public PublicResource(DRaceDao raceDao) {
        this.raceDao = raceDao;
        if (SystemProperty.Environment.Value.Development == SystemProperty.environment.value()) {
            raceDao.persist(123L, null, "Demo ", DemoExtractor.class.getName(), 
                    "https://broker-web.appspot.com/img/FitnessTracker_1024.png", 
                    "", new Date(), "Europe/Stockholm");
        }
    }
    
    @GET
    @Path("course/{keyString}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getCourse(@PathParam("keyString") String splitKeyString,
            @QueryParam("fb_source") @DefaultValue("DEFAULT") String fbSource,
            @QueryParam("lineStyleColor") @DefaultValue("7fff3f3f") String lineStyleColor,
            @QueryParam("polyStyleColor") @DefaultValue("7f00ff00") String polyStyleColor
            ) {
        
        // is this a referral from the item in a feed?
        // https://broker-web.appspot.com/pub/course/agxzfmJyb2tlci13ZWJyLAsSDERQYXJ0aWNpcGFudBiAgICAoqqRCgwLEgZEU3BsaXQYgICAgICumQoM?fb_source=708
        if (!"DEFAULT".equals(fbSource)) {
            String html = new StringBuilder()
                .append("<!DOCTYPE html>\n")
                .append("<html>\n")
                .append("  <head>\n")
                .append("    <meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">\n")
                .append("    <meta charset=\"utf-8\">\n")
                .append("    <title>Stockholm Marathon</title>\n")
                .append("    <style>\n")
                .append("      html, body, #map-canvas {\n")
                .append("        height: 100%;\n")
                .append("        margin: 0px;\n")
                .append("        padding: 0px\n")
                .append("      }\n")
                .append("    </style>\n")
                .append("    <script src=\"https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false\"></script>\n")
                .append("    <script>\n")
                .append("function initialize() {\n")
                .append("  var chicago = new google.maps.LatLng(59.332348,18.078742);\n")
                .append("  var mapOptions = {\n")
                .append("    zoom: 14,\n")
                .append("    center: chicago\n")
                .append("  }\n")
                .append("\n")
                .append("  var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);\n")
                .append("\n")
                .append("  var ctaLayer = new google.maps.KmlLayer({\n")
                .append("    url: 'https://broker-web.appspot.com/pub/kml/")
                .append(splitKeyString)
                .append("?lineStyleColor=" + lineStyleColor)
                .append("&polyStyleColor=" + polyStyleColor)
                .append("&tt=" + System.currentTimeMillis())
                .append("'\n")
                .append("  });\n")
                .append("  ctaLayer.setMap(map);\n")
                .append("}\n")
                .append("\n")
                .append("google.maps.event.addDomListener(window, 'load', initialize);\n")
                .append("\n")
                .append("    </script>\n")
                .append("  </head>\n")
                .append("  <body>\n")
                .append("    <div id=\"map-canvas\"></div>\n")
                .append("  </body>\n")
                .append("</html>")
                .toString();
            return Response.ok(html, MediaType.TEXT_HTML_TYPE).build();
        }
        
        // for participant splits, queryByParent is by queryByRaceKey():
        final Object splitKey = splitDao.getPrimaryKey(splitKeyString);
        final Object participantKey = splitDao.getParentKeyByPrimaryKey(splitKey);
        final DParticipant participant = participantDao.findByPrimaryKey(participantKey);
        final Iterable<DSplit> participantSplits = splitDao.queryByRaceKey(participantKey);
        
        // get the race splits via race id:
        final Object raceKey = raceDao.getPrimaryKey(null, participant.getRaceId());
        final DRace race = raceDao.findByPrimaryKey(raceKey);
        final Iterable<DSplit> raceSplits = splitDao.queryByRaceKey(raceKey);
        
        final StringBuilder sb = new StringBuilder()
                .append("<html>\n")
                .append("  <head prefix=\"og: http://ogp.me/ns# fb: http://ogp.me/ns/fb# fitness: http://ogp.me/ns/fitness#\">\n");
        
        meta(sb, "fb:app_id", TrackerResource.APP_ID);
        meta(sb, "og:type", "fitness.course");
        meta(sb, "og:title", race.getDisplayName());
        meta(sb, "og:site_name", "Fitness Tracker");
        meta(sb, "og:url", "https://broker-web.appspot.com/pub/course/" + splitKeyString);
        String ogImage = "http://fitness.wadpam.com/img/FT_mobile_large.png";
        if (!Strings.isNullOrEmpty(race.getImageUri())) {
            ogImage = race.getImageUri();
        }
        meta(sb, "og:image", ogImage);
        meta(sb, "fitness:live_text", "Send me cheers along " + race.getDisplayName() + " by liking or commenting on this post!");
        
        writeActivityDataPoints(sb, participant, participantSplits, raceKey, race, raceSplits);
        
        sb.append("  </head><body>\n</body></html>\n");
        return Response.ok(sb.toString()).build();
    }

    @GET
    @Path("kml/{keyString}")
    @Produces("application/vnd.google-earth.kml+xml")
    public Response getKML(@PathParam("keyString") String splitKeyString,
            @QueryParam("lineStyleColor") @DefaultValue("7f00ffff") String lineStyleColor,
            @QueryParam("polyStyleColor") @DefaultValue("7f00ff00") String polyStyleColor
            ) {
        StringBuilder kml = new StringBuilder();
        kml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

        // for participant splits, queryByParent is by queryByRaceKey():
        final Object splitKey = splitDao.getPrimaryKey(splitKeyString);
        final Object participantKey = splitDao.getParentKeyByPrimaryKey(splitKey);
        final DParticipant participant = participantDao.findByPrimaryKey(participantKey);
        final Iterable<DSplit> participantSplits = splitDao.queryByRaceKey(participantKey);
        
        // get the race splits via race id:
        final Object raceKey = raceDao.getPrimaryKey(null, participant.getRaceId());
        final DRace race = raceDao.findByPrimaryKey(raceKey);
        final Iterable<DSplit> raceSplits = splitDao.queryByRaceKey(raceKey);
    
        kml.append("<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n")
			.append("  <Document>\n")
			.append("    <name>Paths</name>\n")
			.append("    <description>Examples of paths. Note that the tessellate tag is by default\n")
			.append("      set to 0. If you want to create tessellated lines, they must be authored\n")
			.append("      (or edited) directly in KML.</description>\n")
			.append("    <Style id=\"yellowLineGreenPoly\">\n")
			.append("      <LineStyle>\n")
			.append("        <color>" + lineStyleColor + "</color>\n")
			.append("        <width>4</width>\n")
			.append("      </LineStyle>\n")
			.append("      <PolyStyle>\n")
			.append("        <color>" + polyStyleColor + "</color>\n")
			.append("      </PolyStyle>\n")
			.append("    </Style>\n")
			.append("    <Placemark>\n")
			.append("      <name>Absolute Extruded</name>\n")
			.append("      <description>Transparent green wall with yellow outlines</description>\n")
			.append("      <styleUrl>#yellowLineGreenPoly</styleUrl>\n")
			.append("      <LineString>\n")
			.append("        <extrude>1</extrude>\n")
			.append("        <tessellate>1</tessellate>\n")
			.append("        <altitudeMode>absolute</altitudeMode>");
        
        writeKmlLineString(kml, participant, participantSplits, raceKey, race, raceSplits);
        
        kml.append("</LineString>\n")
			.append("    </Placemark>\n");
        
        writeKmlPlacemarks(kml, participantSplits, race, raceSplits);
        
        kml.append("  </Document>\n")
			.append("</kml>");
        return Response.ok(kml.toString()).build();
    }
    
    private static void meta(StringBuilder sb, String property, String content) {
        sb.append("    <meta property=\"")
                .append(property)
                .append("\" content=\"")
                .append(content)
                .append("\" />\n");
    }

    private void writeKmlPlacemarks(StringBuilder kml, Iterable<DSplit> participantSplits, DRace race, Iterable<DSplit> raceSplits) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone(race.getTimeZone()));
        
        for (DSplit ps : participantSplits) {
            kml.append("  <Placemark>\n")
			.append("    <name>" + ps.getName() + "</name>\n")
			.append("    <description>" + sdf.format(new Date(ps.getTimestamp())) + "</description>\n")
			.append("    <Point>\n")
			.append("      <coordinates>")
                        .append(Float.toString(ps.getPoint().getLongitude()))
                        .append(',')
                        .append(Float.toString(ps.getPoint().getLatitude()))
                        .append(',')
                        .append(Float.valueOf(ps.getElevation()).intValue())
                        .append("</coordinates>\n")
			.append("    </Point>\n")
			.append("  </Placemark>");
        }
    }
    
    @GET
    @Path("course/{raceId}/split")
    public Response getSplits(@PathParam("raceId") Long raceId) {
        final Object raceKey = raceDao.getPrimaryKey(null, raceId);
        TreeMap<Long, DSplit> splits = splitDao.mapByParentKey(raceKey);
        return Response.ok(splits.values()).build();
    }

    @GET
    @Path("course")
    public Response getRaces() {
        Iterable<DRace> races = raceDao.queryOpen(new Date());
        return Response.ok(races).build();
    }
    
    @GET
    @Path("race/{raceId}")
    public Response getRace(@PathParam("raceId") Long raceId) {
        final DRace dRace = raceDao.findByPrimaryKey(raceId);
        if (null == dRace) {
            return Response.status(Status.NOT_FOUND).build();
        }
        UpdateRaceRequest race = new UpdateRaceRequest();
        race.setDisplayName(dRace.getDisplayName());
        race.setImageUri(dRace.getImageUri());
        race.setTimeZone(dRace.getTimeZone());
        SimpleDateFormat sdf = DRaceDaoBean.getDateFormat(dRace.getTimeZone());
        race.setStartTime(null != dRace.getStartDate() ? 
                sdf.format(dRace.getStartDate()) : "");
        
        return Response.ok(race).build();
    }
    
    @GET
    @Path("timezone")
    public Response getTimezoneIDs() {
        return Response.ok(TimeZone.getAvailableIDs()).build();
    }

    public void writeActivityDataPoints(StringBuilder sb, 
            DParticipant participant, Iterable<DSplit> participantSplits, 
            Object raceKey, DRace race, Iterable<DSplit> raceSplits) {
        
        // find latest race timestamp (stored in trackPointId)
        final TreeMap<Long, DSplit> splitMap = new TreeMap<Long, DSplit>();
        for (DSplit split : participantSplits) {
            splitMap.put(split.getTimestamp(), split);
        }
        final long maxTimestamp = splitMap.isEmpty() ? 0L : splitMap.lastKey();
        LOGGER.debug("maxTimestamp is {}, splits.size={}", maxTimestamp, splitMap.size());
        
        // iterate track points until timestamp
        List<TrackPoint> points = raceDao.getTrack(race.getBlobKey());
        Iterator<TrackPoint> ptIterator = points.iterator();
        
        long prevRaceSplit = 0L, prevPartSplit = 0L, t = -1L, startTime = 0L;
        Iterable<DSplit> i = splitMap.isEmpty() ? raceSplits : splitMap.values();
        TrackPoint trkpt;
        boolean beforeStart = true;
        DSplit next = null;
        for (Iterator<DSplit> iterator = i.iterator(); iterator.hasNext(); ) {
            next = iterator.next();
            // LOGGER.debug("prevPartSplit={}, prevRaceSplit={}", prevPartSplit, prevRaceSplit);
            LOGGER.debug("next.timestamp {}, course.timestamp {}", next.getTimestamp(), next.getTrackPointId());
            
            // linear interpolation between splits
            double factor = ((double) (next.getTimestamp() - prevPartSplit)) / ((double) (next.getTrackPointId() - prevRaceSplit));
            //LOGGER.debug("factor for split {} is {}", next.getName(), factor);
            
            do {
                trkpt = ptIterator.next();
                
                // have we reached the start split?
                if (beforeStart) {
                    beforeStart = trkpt.getT() < next.getTrackPointId();
                    startTime = next.getTimestamp();
                }
                
                // only output if on or past start split
                if (!beforeStart) {
                    t = prevPartSplit + Math.round(factor * (trkpt.getT() - prevRaceSplit));
                    PublicResource.writeActivityDataPoint(sb, trkpt, t);
                    if (maxTimestamp <= t) break;
                }
                
            } while (trkpt.getT() < next.getTrackPointId());
            LOGGER.debug("At split, t is {}, trkpt.T is {}", t, trkpt.getT());

            // The Place additional property can only be used when the user is 
            // at the physical location at the time the action happens.
//            if (DSplitDao.NAME_START.equals(next.getName())) {
//                meta(sb, "place:location:latitude", Float.toString(trkpt.getLat()));
//               meta(sb, "place:location:longitude", Float.toString(trkpt.getLon()));
//            }
            
            // tag split metrics with distance 
            meta(sb, "fitness:metrics:distance:value", Float.toString(next.getDistance()/1000.0f));
            meta(sb, "fitness:metrics:distance:units", "km");

            // and add a split
            meta(sb, "fitness:splits:values:units", "km");
            meta(sb, "fitness:splits:values:value", Float.toString(next.getDistance()/1000.0f));
            meta(sb, "fitness:splits:values:units", "s");
            meta(sb, "fitness:splits:values:value", Long.toString((next.getTimestamp()-startTime)/1000));
            if (10.0 < next.getDistance()) {
                meta(sb, "fitness:splits:values:units", "s/m");
                meta(sb, "fitness:splits:values:value", Float.toString(((next.getTimestamp()-startTime)/1000)/next.getDistance()));
            }
            
            prevPartSplit = next.getTimestamp();
            prevRaceSplit = next.getTrackPointId();
        }
        
        // tag course with distance 
        meta(sb, "fitness:distance:units", "km");
        meta(sb, "fitness:distance:value", Float.toString(next.getDistance()/1000.0f));
        // tag course with duration
        meta(sb, "fitness:duration:units", "s");
        meta(sb, "fitness:duration:value", Long.toString((prevPartSplit-startTime)/1000));
        // tag course with pace
        if (10.0 < next.getDistance()) {
            meta(sb, "fitness:pace:units", "s/m");
            meta(sb, "fitness:pace:value", Float.toString(((prevPartSplit-startTime)/1000)/next.getDistance()));
        }
    }

    public static void writeActivityDataPoint(StringBuilder sb, TrackPoint trkpt, long t) {
//        <meta property="fitness:metrics:location:latitude"  content="37.416382" />
//        <meta property="fitness:metrics:location:longitude" content="-122.152659" />
//        <meta property="fitness:metrics:location:altitude"  content="42" />
//        <meta property="fitness:metrics:timestamp" content="2011-01-26T00:00" />
//        <meta property="fitness:metrics:distance:value" content="0" />
//        <meta property="fitness:metrics:distance:units" content="mi" />
//        <meta property="fitness:metrics:pace:value" content="0" />
//        <meta property="fitness:metrics:pace:units" content="s/m" />
//        <meta property="fitness:metrics:calories" content="0" />
//        <meta property="fitness:metrics:custom_quantity:value" content="0" />
//        <meta property="fitness:metrics:custom_quantity:units" content="SOME_UNIT_URL" />

        meta(sb, "fitness:metrics:location:latitude", Float.toString(trkpt.getLat()));
        meta(sb, "fitness:metrics:location:longitude", Float.toString(trkpt.getLon()));
        meta(sb, "fitness:metrics:location:altitude", Float.toString(trkpt.getAlt()));
        meta(sb, "fitness:metrics:timestamp", DRaceDaoBean.SDF.format(new Date(t)));
    }
    
    public static void writeKmlCoordinate(StringBuilder sb, TrackPoint trkpt, long t) {
/*         <coordinates> -112.2550785337791,36.07954952145647,2357
          -112.2549277039738,36.08117083492122,2357
          -112.2552505069063,36.08260761307279,2357
          -112.2564540158376,36.08395660588506,2357
          -112.2580238976449,36.08511401044813,2357
          -112.2595218489022,36.08584355239394,2357
          -112.2608216347552,36.08612634548589,2357
          -112.262073428656,36.08626019085147,2357
          -112.2633204928495,36.08621519860091,2357
          -112.2644963846444,36.08627897945274,2357
          -112.2656969554589,36.08649599090644,2357 
        </coordinates>*/
        sb.append(Float.toString(trkpt.getLon()))
                .append(',')
                .append(Float.toString(trkpt.getLat()))
                .append(',')
                .append(Float.valueOf(trkpt.getAlt()).intValue())
                .append("\n");
        // meta(sb, "fitness:metrics:timestamp", DRaceDaoBean.SDF.format(new Date(t)));
    }
    
    public void writeKmlLineString(StringBuilder sb, 
            DParticipant participant, Iterable<DSplit> participantSplits, 
            Object raceKey, DRace race, Iterable<DSplit> raceSplits) {
        
        // find latest race timestamp (stored in trackPointId)
        final TreeMap<Long, DSplit> splitMap = new TreeMap<Long, DSplit>();
        for (DSplit split : participantSplits) {
            splitMap.put(split.getTimestamp(), split);
        }
        final long maxTimestamp = splitMap.isEmpty() ? 0L : splitMap.lastKey();
        LOGGER.debug("maxTimestamp is {}, splits.size={}", maxTimestamp, splitMap.size());
        
        // iterate track points until timestamp
        List<TrackPoint> points = raceDao.getTrack(race.getBlobKey());
        Iterator<TrackPoint> ptIterator = points.iterator();
        
        sb.append("<coordinates>");
        long prevRaceSplit = 0L, prevPartSplit = 0L, t = -1L, startTime = 0L;
        Iterable<DSplit> i = splitMap.isEmpty() ? raceSplits : splitMap.values();
        TrackPoint trkpt;
        boolean beforeStart = true;
        DSplit next = null;
        for (Iterator<DSplit> iterator = i.iterator(); iterator.hasNext(); ) {
            next = iterator.next();
            // LOGGER.debug("prevPartSplit={}, prevRaceSplit={}", prevPartSplit, prevRaceSplit);
            LOGGER.debug("next.timestamp {}, course.timestamp {}", next.getTimestamp(), next.getTrackPointId());
            
            // linear interpolation between splits
            double factor = ((double) (next.getTimestamp() - prevPartSplit)) / ((double) (next.getTrackPointId() - prevRaceSplit));
            //LOGGER.debug("factor for split {} is {}", next.getName(), factor);
            
            do {
                trkpt = ptIterator.next();
                
                // have we reached the start split?
                if (beforeStart) {
                    beforeStart = trkpt.getT() < next.getTrackPointId();
                    startTime = next.getTimestamp();
                }
                
                // only output if on or past start split
                if (!beforeStart) {
                    t = prevPartSplit + Math.round(factor * (trkpt.getT() - prevRaceSplit));
                    PublicResource.writeKmlCoordinate(sb, trkpt, t);
                    if (maxTimestamp <= t) break;
                }
                
            } while (trkpt.getT() < next.getTrackPointId());
            LOGGER.debug("At split, t is {}, trkpt.T is {}", t, trkpt.getT());

//            // tag split metrics with distance 
//            meta(sb, "fitness:metrics:distance:value", Float.toString(next.getDistance()/1000.0f));
//            meta(sb, "fitness:metrics:distance:units", "km");
//
//            // and add a split
//            meta(sb, "fitness:splits:values:units", "km");
//            meta(sb, "fitness:splits:values:value", Float.toString(next.getDistance()/1000.0f));
//            meta(sb, "fitness:splits:values:units", "s");
//            meta(sb, "fitness:splits:values:value", Long.toString((next.getTimestamp()-startTime)/1000));
//            if (10.0 < next.getDistance()) {
//                meta(sb, "fitness:splits:values:units", "s/m");
//                meta(sb, "fitness:splits:values:value", Float.toString(((next.getTimestamp()-startTime)/1000)/next.getDistance()));
//            }
            
            prevPartSplit = next.getTimestamp();
            prevRaceSplit = next.getTrackPointId();
        }
        
        sb.append("</coordinates>");
//        // tag course with distance 
//        meta(sb, "fitness:distance:units", "km");
//        meta(sb, "fitness:distance:value", Float.toString(next.getDistance()/1000.0f));
//        // tag course with duration
//        meta(sb, "fitness:duration:units", "s");
//        meta(sb, "fitness:duration:value", Long.toString((prevPartSplit-startTime)/1000));
//        // tag course with pace
//        if (10.0 < next.getDistance()) {
//            meta(sb, "fitness:pace:units", "s/m");
//            meta(sb, "fitness:pace:value", Float.toString(((prevPartSplit-startTime)/1000)/next.getDistance()));
//        }
    }

    @GET
    @Path("img")
    public Response redirectToCDN(@QueryParam("camId") String camId) throws URISyntaxException {
        URI uri = new URI("https://legend-passbook.appspot.com/images/pass.se.bassac.stamps/storeCard/logo.png?uid=123&camId=" + camId);
        
        return Response.status(302).location(uri).build();
    }
    
    @GET
    @Path("imgrecs")
    @Produces("image/png")
    public Response getImageWithCookie(@QueryParam("cookieValue") String cookieValue) throws IOException {
        InputStream in = getClass().getResourceAsStream("/theBluePumpkin.png");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte b[] = new byte[1024];
        int count;
        while (0 < (count = in.read(b))) {
            baos.write(b, 0, count);
        }
        in.close();
        return Response.ok(baos.toByteArray(), "image/png")
                .cookie(new NewCookie("pumpkin", cookieValue))
                .build();
    }
    
    @GET
    @Path("imgclick")
    public Response onImageClick(@CookieParam("pumpkin") String cookieValue) {
        LOGGER.info("cookie pumpkin has value {}", cookieValue);
        Map<String, String> body = new HashMap<String,String>();
        body.put("pumpkin", cookieValue);
        return Response.ok(body).build();
    }

}
