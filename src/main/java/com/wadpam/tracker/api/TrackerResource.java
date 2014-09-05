package com.wadpam.tracker.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.wadpam.mardao.oauth.web.OAuth2Filter;
import com.wadpam.mardao.social.NetworkTemplate;
import com.wadpam.tracker.dao.DParticipantDao;
import com.wadpam.tracker.dao.DRaceDao;
import com.wadpam.tracker.domain.DParticipant;
import com.wadpam.tracker.domain.DRace;
import com.wadpam.tracker.extractor.AbstractSplitsExtractor;
import com.wadpam.tracker.opengraph.StandardObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author osandstrom
 */
@Path("api/tracker")
@Produces(MediaType.APPLICATION_JSON)
public class TrackerResource {
    public static final String APP_ID = "478161362293304"; //255653361131262";
    
    static final Logger LOGGER = LoggerFactory.getLogger(TrackerResource.class);
    
    @Inject
    private HttpServletRequest request;
    
    private final DParticipantDao participantDao;
    private final DRaceDao raceDao;
    
    @Inject
    public TrackerResource(DParticipantDao participantDao, DRaceDao raceDao) {
        this.participantDao = participantDao;
        this.raceDao = raceDao;
    }
    
    @GET
    @Path("search/{raceId}")
    public Response searchForParticipants(@PathParam("raceId") Long raceId,
            @QueryParam("searchName") String searchName,
            @QueryParam("firstName") String firstName) {
    
        final DRace race = raceDao.findByPrimaryKey(raceId);
        AbstractSplitsExtractor userIdExtractor = AdminResource.createExtractor(race, raceDao, participantDao, null);
        if (null != userIdExtractor) {
            TreeMap<String, String> map = userIdExtractor.searchForParticipants(race, searchName, firstName);
            return Response.ok(map.entrySet()).build();
        }
        
        return Response.serverError().build();
    }
    
    @GET
    public Response registerParticipant(@QueryParam("raceId") Long raceId,
            @QueryParam("extUserId") String extUserId) {
        Long userId = OAuth2Filter.getUserId(request);
        DParticipant participant = participantDao.persist(null, null, extUserId, 
                raceId, DParticipantDao.STATUS_PENDING, userId);
        return Response.ok(participant).build();
    }

    public static void append(Map<String,Object> map, String property, String content) {
        map.put(property, content);
    }

    public static void append(StringBuilder sb, String property, String content) {
        if (0 < sb.length()) {
            sb.append('&');
        }
        try {
            sb.append(property.replace(":", "%3A"))
                    .append('=')
                    .append(URLEncoder.encode(content, "UTF-8"));
        } catch (UnsupportedEncodingException willNeverHappen) {
        }
    }
    
    private String getAccessToken() {
        return (String) request.getAttribute(OAuth2Filter.NAME_ACCESS_TOKEN);
    }

    public static <R> R postStandardObject(String path, String accessToken, 
            Class<R> responseClass,
            StandardObject obj, Map... extras) {
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("access_token", accessToken);
        
        try {
            params.put("object", NetworkTemplate.MAPPER.writeValueAsString(obj));
        } catch (JsonProcessingException ex) {
            LOGGER.error("Converting StandardObject", ex);
        }

        if (null != extras && 0 < extras.length) {
            params.putAll(extras[0]);
        }
        
        NetworkTemplate template = new NetworkTemplate();
        template.setAccept("*/*");
        Map<String, String> requestHeaders = ImmutableMap.of(NetworkTemplate.CONTENT_TYPE, NetworkTemplate.MIME_FORM);
        R response = template.post("https://graph.facebook.com" + path, requestHeaders, params, responseClass);
        return response;
    }

}
