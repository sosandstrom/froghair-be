package com.wadpam.froghair.api;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.wadpam.mardao.oauth.domain.DOAuth2User;

/**
 * To serve check-ins and swarm requests.
 *
 * @author osandstrom Date: 2014-09-10 Time: 19:47
 */
@Path("api/nearby")
@Produces(MediaType.APPLICATION_JSON)
public class NearbyResource {

  static final Logger LOGGER = LoggerFactory.getLogger(NearbyResource.class);

  @Inject
  private HttpServletRequest request;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public List<DOAuth2User> checkIn(CheckInRequest body) {
    LOGGER.info(body.getClubGUID());

    final Index index = getSearchIndex();
    Long id = (Long)this.request.getAttribute("oauth2user.id");

    Document doc = Document.newBuilder()
      .addField(Field.newBuilder().setName(DOAuth2User.class.getSimpleName()).setAtom(id.toString()))
      .addField(Field.newBuilder().setName("clubGUID").setAtom(body.getClubGUID()))
      .build();
    index.put(doc);

    final String query = "clubGUID: " + body.getClubGUID();
    Results<ScoredDocument> results = index.search(query);

    ImmutableList.Builder<DOAuth2User> listBuilder = ImmutableList.builder();
    for (ScoredDocument scoredDocument : results) {
      String userId = scoredDocument.getOnlyField(DOAuth2User.class.getSimpleName()).getAtom();
      DOAuth2User user = new DOAuth2User();
      user.setId(Long.parseLong(userId));
      user.setUpdatedDate(new Date(doc.getRank() - scoredDocument.getRank()));
      listBuilder.add(user);
    }

    return listBuilder.build();
  }

  protected static Index getSearchIndex() {
    IndexSpec spec = IndexSpec.newBuilder().setName("checkIns").build();
    return SearchServiceFactory.getSearchService().getIndex(spec);
  }

  public static class CheckInRequest {
    private String clubGUID;

    public String getClubGUID() {
      return clubGUID;
    }

    public void setClubGUID(String clubGUID) {
      this.clubGUID = clubGUID;
    }
  }
}
