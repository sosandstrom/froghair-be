package com.wadpam.tracker.api;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.wadpam.mardao.oauth.dao.DConnectionDao;
import com.wadpam.mardao.oauth.dao.DOAuth2UserDao;
import com.wadpam.mardao.social.NetworkTemplate;
import static com.wadpam.tracker.api.TrackerResource.APP_ID;
import com.wadpam.tracker.dao.DParticipantDao;
import com.wadpam.tracker.dao.DRaceDao;
import com.wadpam.tracker.dao.DRaceDaoBean;
import com.wadpam.tracker.dao.DSplitDao;
import com.wadpam.tracker.domain.DParticipant;
import com.wadpam.tracker.domain.DRace;
import com.wadpam.tracker.domain.DSplit;
import com.wadpam.tracker.domain.TrackPoint;
import com.wadpam.tracker.extractor.AbstractSplitsExtractor;
import com.wadpam.tracker.extractor.DemoExtractor;
import com.wadpam.tracker.opengraph.FitnessRuns;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import net.sf.mardao.core.geo.DLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author osandstrom
 */
@Path("_adm")
@Produces(MediaType.APPLICATION_JSON)
public class AdminResource {
    
    static final Logger LOGGER = LoggerFactory.getLogger(AdminResource.class);
    
    @Inject
    private HttpServletRequest request;
    
    @Inject
    private DConnectionDao connectionDao;
    
    @Inject
    private DParticipantDao participantDao;
    
    @Inject
    private DRaceDao raceDao;
    
    @Inject
    private DSplitDao splitDao;
    
    @Inject
    private DOAuth2UserDao userDao;

    @GET
    @Path("cron/activeRaces")
    public Response checkActiveRaces() {
        final Date now = new Date();
        Iterable<Long> races = raceDao.queryActive(now);
        for (Long raceId : races) {
            checkActiveRace(raceId);
        }
        return Response.ok(races).build();
    }
    
    @POST
    @Path("participant/{participantId}")
    public Response checkParticipant(@PathParam("participantId") Long participantId,
            @FormParam("raceId") Long raceId) {
        final DRace race = raceDao.findByPrimaryKey(raceId);
        AbstractSplitsExtractor extractor = createExtractor(race,
                raceDao, participantDao, splitDao);
        if (null != extractor) {
            extractor.setAdminResource(this);
            extractor.processParticipant(participantId);
        }
        
        return Response.ok().build();
    }
    
    @POST
    @Path("course/{raceId}/split")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCourseSplit(@PathParam("raceId") Long raceId,
            CreateSplitRequest body) {
        final Object raceKey = raceDao.getPrimaryKey(null, raceId);
        long minTimestamp = (null != body.getElapsedSeconds() && 0 < body.getElapsedSeconds().length()) ?
                Long.parseLong(body.getElapsedSeconds()) : 0L;
        LOGGER.info("elapsedSeconds {} parses into {}", body.getElapsedSeconds(), minTimestamp);
        Float lat = null, lon = null;
        if (null != body.getLatLong() && 0 < body.getLatLong().length()) {
            String[] ll = body.getLatLong().split(",");
            if (2 == ll.length) {
                lat = Float.valueOf(ll[0]);
                lon = Float.valueOf(ll[1]);
            }
        }
        TrackPoint nearest = raceDao.findNearest(raceKey, minTimestamp, lat, lon);
        DSplit split = splitDao.persist(raceKey, null, 
                body.getDistance(), nearest.getAlt(), 
                body.getName(), new DLocation(nearest.getLat(), nearest.getLon()),
                nearest.getT(), nearest.getT());
        return Response.ok(split).build();
    }
    
    @POST
    @Path("participant/{participantId}/split")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createParticipantSplit(@PathParam("participantId") Long participantId,
            CreateSplitRequest body) {
        final Object participantKey = participantDao.getPrimaryKey(null, participantId);
        final DParticipant participant = participantDao.findByPrimaryKey(participantKey);
        final Object raceKey = raceDao.getPrimaryKey(null, participant.getRaceId());
        long timestamp = Strings.isNullOrEmpty(body.getElapsedSeconds()) ? 
                System.currentTimeMillis() : Long.parseLong(body.getElapsedSeconds());

        DSplit courseSplit = splitDao.findByPrimaryKey(raceKey, body.getRaceSplitId());
        DSplit split = splitDao.persist(participantKey, null, 
                courseSplit.getDistance(), courseSplit.getElevation(), 
                courseSplit.getName(), courseSplit.getPoint(), 
                timestamp, courseSplit.getTimestamp());
        
        // post / update fitness object
        upsertFitnessObject(participant, split);

        // update status to disable future checks
        if (DSplitDao.NAME_FINISH.equals(courseSplit.getName())) {
            participant.setStatus(DParticipantDao.STATUS_FINISHED);
            participantDao.update(participant);
        }
        
        return Response.ok(split).build();
    }
    
    @DELETE
    @Path("course/{raceId}/split/{splitId}")
    public Response deleteSplit(@PathParam("raceId") Long raceId,
            @PathParam("splitId") Long splitId) {
        final Object raceKey = raceDao.getPrimaryKey(null, raceId);
        boolean found = splitDao.delete(raceKey, splitId);
        return Response.status(found ? Status.OK : Status.NO_CONTENT).build();
    }
    
    private void checkActiveRace(final Long raceId) {
        final DRace race = raceDao.findByPrimaryKey(raceId);
        LOGGER.info("Active race: {} url {}", race.getDisplayName(), race.getQueryUrl());
        
        AbstractSplitsExtractor extractor = createExtractor(race,
                raceDao, participantDao, splitDao);
        if (null != extractor) {
            extractor.setAdminResource(this);
            extractor.process(race);
        }
        
        if (DemoExtractor.class.getName().equals(race.getExtractorClassname())) {
            updateDemoRace(race, System.currentTimeMillis());
        }
    }


    public static AbstractSplitsExtractor createExtractor(DRace race, DRaceDao raceDao, DParticipantDao participantDao, DSplitDao splitDao) {
        AbstractSplitsExtractor extractor = null;
        if (null != race.getExtractorClassname()) {
            try {
                Class extractorClass = Class.forName(race.getExtractorClassname());
                extractor = (AbstractSplitsExtractor) extractorClass.newInstance();
                extractor.setParticipantDao(participantDao);
                extractor.setRaceDao(raceDao);
                extractor.setSplitDao(splitDao);
                extractor.setRace(race);
            } catch (InstantiationException ex) {
                LOGGER.error("Instantiating extractor", ex);
            } catch (IllegalAccessException ex) {
                LOGGER.error("Accessing extractor", ex);
            } catch (ClassNotFoundException ex) {
                LOGGER.error("Extractor", ex);
            }
        }
        return extractor;
    }
    
    private void upsertFitnessObject(DParticipant participant, DSplit split) {
        
        // get most recent access token
        final Object userKey = userDao.getPrimaryKey(null, participant.getUserId());
        final String accessToken = connectionDao.getAccessToken(userKey, DConnectionDao.PROVIDER_ID_FACEBOOK);
        
        if (null != accessToken) {
            
            // stuff needed to create fitness run:
            final Object splitKey = splitDao.getPrimaryKey(split);
            final String splitKeyString = splitDao.getKeyString(splitKey);
            String courseUrl = "https://broker-web.appspot.com/pub/course/" + splitKeyString;
            
            if (null == participant.getActionId()) {
                
                final DRace race = raceDao.findByPrimaryKey(null, participant.getRaceId());

                String actionId = createFitnessRun(courseUrl, APP_ID, accessToken,
                        race.getDisplayName(), split.getTimestamp());
                participant.setActionId(actionId);
                participantDao.update(participant);
            }
            else {
                if (DSplitDao.NAME_FINISH.equals(split.getName())) {
                    // update status to disable future checks
                    participant.setStatus(DParticipantDao.STATUS_FINISHED);
                    participantDao.update(participant);
                }        
                
                // update fitness run
                updateFitnessRun(participant.getActionId(), 
                        courseUrl, 
                        split, accessToken);
            }
        }
    }

    private String createFitnessRun(String courseUrl, String appId, 
            String accessToken, String title, Long startTime) {
        FitnessRuns run = new FitnessRuns();
        run.setApp_id(appId);
        run.setTitle(title);
        //run.setCourse(courseUrl);
        
        Map<String,String> response = TrackerResource.postStandardObject("/me/fitness.runs", 
                accessToken, Map.class, run,
                ImmutableMap.builder().put("course", courseUrl)
                .put("start_time", DRaceDaoBean.SDF.format(new Date(startTime)))
                .put("expires_in", "86400") // 24h
                .put("fb:explicitly_shared", "true")
                //.put("privacy", "{\"value\":\"SELF\"}")
                .build());
        return response.get("id");
    }

    private Boolean updateFitnessRun(String actionId, String courseUrl, 
            DSplit split, String accessToken) {

        NetworkTemplate template = new NetworkTemplate();
        Map<String, String> params = new HashMap<String, String>();
        params.put("method", "POST");
        params.put("suppress_http_code", "1");
        params.put("course", courseUrl);
        params.put("access_token", accessToken);
        
        if (DSplitDao.NAME_FINISH.equals(split.getName())) {
            params.put("end_time", DRaceDaoBean.SDF.format(new Date(split.getTimestamp())));
        }
        
        return template.get("https://graph.facebook.com/" + actionId, 
                Boolean.class, params);
    }

    @POST
    @Path("course/{raceId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCourse(@PathParam("raceId") Long raceId,
        UpdateRaceRequest body) {
        
        DRace race = raceDao.findByPrimaryKey(raceId);
        if (null == race) {
            return Response.status(Status.NOT_FOUND).build();
        }
        race.setDisplayName(body.getDisplayName());
        race.setImageUri(body.getImageUri());
        race.setTimeZone(body.getTimeZone());
        try {
            SimpleDateFormat sdf = DRaceDaoBean.getDateFormat(body.getTimeZone());
            race.setStartDate(sdf.parse(body.getStartTime()));
        } catch (ParseException unixTimestamp) {
            LOGGER.warn("Parsing {}", body.getStartTime());
        }
        raceDao.update(race);
        
        return Response.ok().build();
    }

    private void updateDemoRace(final DRace race, final long currentTimeMillis) {
        // about to close (14h past start date) ?
        if (race.getStartDate().getTime() + 12L*60L*60L*1000L <= currentTimeMillis) {
            race.setStartDate(new Date(currentTimeMillis));
            raceDao.update(race);
            LOGGER.info("Updated demo race start time.");
        }
    }
}
