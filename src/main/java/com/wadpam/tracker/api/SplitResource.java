package com.wadpam.tracker.api;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import com.wadpam.mardao.crud.ParentedCrudResource;
import com.wadpam.tracker.dao.DParticipantDao;
import com.wadpam.tracker.dao.DSplitDao;
import com.wadpam.tracker.domain.DParticipant;
import com.wadpam.tracker.domain.DSplit;


/**
 * Created with IntelliJ IDEA.
 *
 * @author osandstrom
 * Date: 1/18/14 Time: 8:07 PM
 */
@Path("api/participant/{parentId}/split")
@Produces(MediaType.APPLICATION_JSON)
public class SplitResource extends ParentedCrudResource<DParticipant, Long, DParticipantDao, 
        DSplit, Long, DSplitDao> {

  @Inject
  public SplitResource(DParticipantDao parentDao, DSplitDao dao) {
    super(parentDao, dao);
  }

  
}
