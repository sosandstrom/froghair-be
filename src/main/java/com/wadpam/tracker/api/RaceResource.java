package com.wadpam.tracker.api;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import com.wadpam.mardao.crud.CrudResource;
import com.wadpam.tracker.dao.DRaceDao;
import com.wadpam.tracker.domain.DRace;


/**
 * Created with IntelliJ IDEA.
 *
 * @author osandstrom
 * Date: 1/18/14 Time: 8:07 PM
 */
@Path("api/race")
@Produces(MediaType.APPLICATION_JSON)
public class RaceResource extends CrudResource<DRace, Long, DRaceDao> {

  @Inject
  public RaceResource(DRaceDao dao) {
    super(dao);
    LOGGER.info("<init>");
  }

}
