package com.wadpam.tracker.api;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import com.wadpam.mardao.crud.CrudResource;
import com.wadpam.mardao.oauth.web.OAuth2Filter;
import com.wadpam.tracker.dao.DParticipantDao;
import com.wadpam.tracker.domain.DParticipant;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.core.Response;


/**
 * Created with IntelliJ IDEA.
 *
 * @author osandstrom
 * Date: 1/18/14 Time: 8:07 PM
 */
@Path("api/participant")
@Produces(MediaType.APPLICATION_JSON)
public class ParticipantResource extends CrudResource<DParticipant, Long, DParticipantDao> {

    @Inject
    private HttpServletRequest request;
    
  @Inject
  public ParticipantResource(DParticipantDao dao) {
    super(dao);
    LOGGER.debug("<init>");
  }

  @GET
  @Path("me")
  public Response getMyRaces() {
        Iterable<DParticipant> parts = dao.queryByUserId(OAuth2Filter.getUserId(request));
        return Response.ok(parts).build();
  }
}
