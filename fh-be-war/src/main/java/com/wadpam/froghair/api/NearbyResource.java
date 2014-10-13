package com.wadpam.froghair.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.FieldExpression;
import com.google.appengine.api.search.GeoPoint;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.SortExpression;
import com.google.appengine.api.search.SortOptions;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.wadpam.mardao.oauth.dao.DOAuth2UserDaoBean;
import com.wadpam.mardao.oauth.domain.DOAuth2User;
import com.wadpam.mardao.oauth.web.OAuth2Filter;
import com.wadpam.mardao.util.Pair;
import com.wadpam.mardao.util.Rank;

/**
 * To serve check-ins and swarm requests.
 *
 * @author osandstrom Date: 2014-09-10 Time: 19:47
 */
@Path("api/nearby")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class NearbyResource {

  static final Logger LOGGER = LoggerFactory.getLogger(NearbyResource.class);
  public static final String FIELD_CLUB_GUID = "clubGUID";
  public static final String FIELD_DISTANCE_METERS = "distanceMeters";
  public static final String FIELD_USER_LOCATION = "userLocation";
  public static final String FIELD_USER_ID = DOAuth2User.class.getSimpleName();
  public static final String FIELD_DISPLAY_NAME = "displayName";
  public static final String FIELD_THUMBNAIL_URL = "thumbnailUrl";
  public static final String FIELD_CREATED_DATE = "createdSeconds";

  @Inject
  private HttpServletRequest request;

  private final DOAuth2UserDaoBean userDao;

  @Inject
  public NearbyResource(DOAuth2UserDaoBean userDao) {
    this.userDao = userDao;
  }

  /**
   * Checks in current authenticated user at specified golf club.
   * @param body
   * @return List of other users at the same specified golf club.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
  public List<DOAuth2User> checkIn(CheckInRequest body) throws IOException {
    LOGGER.info(body.getClubGUID());

    final Index index = getSearchIndex();
    Long id = (Long)this.request.getAttribute(OAuth2Filter.NAME_USER_ID);
    final DOAuth2User oauth2User = userDao.get(id);

    Float lat = body.getLat();
    Float lon = body.getLon();
    if (null == lat || null == lon) {
      final String latLong = request.getHeader("X-AppEngine-CityLatLong");
      lat = Float.parseFloat(latLong.substring(0, latLong.indexOf(',')));
      lon = Float.parseFloat(latLong.substring(latLong.indexOf(',')+1));
    }
    GeoPoint geoPoint = new GeoPoint(lat, lon);

    Document doc = Document.newBuilder()
      .addField(Field.newBuilder().setName(FIELD_USER_ID).setAtom(id.toString()))
      .addField(Field.newBuilder().setName(FIELD_CLUB_GUID).setAtom(body.getClubGUID()))
      .addField(Field.newBuilder().setName(FIELD_USER_LOCATION).setGeoPoint(geoPoint))
      .addField(Field.newBuilder().setName(FIELD_DISPLAY_NAME).setAtom(oauth2User.getDisplayName()))
      .addField(Field.newBuilder().setName(FIELD_CREATED_DATE).setNumber(Rank.getCurrentRank()))
      .addField(Field.newBuilder().setName(FIELD_THUMBNAIL_URL).setText(oauth2User.getThumbnailUrl()))
      .build();
    index.put(doc);
    LOGGER.info("Check-in at {} with ageInSeconds {} (currentRank {})", new Object[] {
      geoPoint, doc.getRank(), Rank.getCurrentRank()});

    final String query = FIELD_CLUB_GUID + ": " + body.getClubGUID();
    Results<ScoredDocument> results = index.search(query);

    ArrayList<String> removeDocs = new ArrayList<String>();
    ImmutableList.Builder<DOAuth2User> listBuilder = ImmutableList.builder();
    for (ScoredDocument scoredDocument : results) {
      final Set<String> fieldNames = scoredDocument.getFieldNames();
      if (fieldNames.containsAll(ImmutableList.of(
        FIELD_CREATED_DATE, FIELD_DISPLAY_NAME, FIELD_THUMBNAIL_URL, FIELD_USER_LOCATION))) {
        String userId = scoredDocument.getOnlyField(FIELD_USER_ID).getAtom();
        DOAuth2User user = new DOAuth2User();
        user.setCreatedDate(Rank.getDate(scoredDocument.getOnlyField(FIELD_CREATED_DATE).getNumber().intValue()));
        user.setDisplayName(scoredDocument.getOnlyField(FIELD_DISPLAY_NAME).getAtom());
        user.setId(Long.parseLong(userId));
        user.setThumbnailUrl(scoredDocument.getOnlyField(FIELD_THUMBNAIL_URL).getText());
        user.setUpdatedBy(scoredDocument.getOnlyField(FIELD_USER_LOCATION).getGeoPoint().toString());
        user.setUpdatedDate(Rank.getDate(scoredDocument.getRank()));
        listBuilder.add(user);
      }
      else {
        removeDocs.add(scoredDocument.getId());
      }
    }
    index.delete(removeDocs);

    return listBuilder.build();
  }

  /**
   * Returns nearby users and golf clubs, as a Pair<List<NearbyUser>, List<DClub>>.
   * @param lat the latitude, defaults to X-AppEngine-CityLatLong
   * @param lon the longitude, defaults to X-AppEngine-CityLatLong
   * @param pageSize defaults to 10
   * @param maxDistance in meters, defaults to 10000
   * @param maxAge in seconds, defaults to 5h.
   * @return a {@link com.wadpam.mardao.util.Pair}&lt;List&lt;DOAuth2User&gt;, List&lt;DClub&gt;&gt;.
   */
  @GET
  public Pair getNearby(@QueryParam("lat") Float lat,
                        @QueryParam("lon") Float lon,
                        @QueryParam("pageSize") @DefaultValue("10") int pageSize,
                        @QueryParam("maxDistance") @DefaultValue("10000") int maxDistance,
                        @QueryParam("maxAge") @DefaultValue("18000") int maxAge) {
    if (null == lat || null == lon) {
      final String latLong = request.getHeader("X-AppEngine-CityLatLong");
      lat = Float.parseFloat(latLong.substring(0, latLong.indexOf(',')));
      lon = Float.parseFloat(latLong.substring(latLong.indexOf(',')+1));
    }

    List<NearbyUser> users = getNearbyUsers(lat, lon, pageSize, maxDistance, maxAge);

    return Pair.of(users, null);
  }

  private static List<NearbyUser> getNearbyUsers(float lat, float lon, int pageSize, int maxDistance, int maxAge) {
    // build sort options
    final String distanceExpression = "distance(" + FIELD_USER_LOCATION + ", geopoint(" + lat + ", " + lon + "))";
    SortOptions sortOptions = SortOptions.newBuilder()
      .addSortExpression(SortExpression.newBuilder()
        .setExpression(distanceExpression)
        .setDirection(SortExpression.SortDirection.ASCENDING)
        .setDefaultValueNumeric(Double.MAX_VALUE))
      .build();

    // build query options
    QueryOptions queryOptions = QueryOptions.newBuilder()
      .addExpressionToReturn(FieldExpression.newBuilder()
        .setExpression(distanceExpression)
        .setName(FIELD_DISTANCE_METERS)
        .build())
      .setSortOptions(sortOptions)
      .setLimit(pageSize)
      .build();

    final String queryString =
      distanceExpression + " < " + maxDistance +
      " AND " +
      FIELD_CREATED_DATE + " > " + Integer.toString(Rank.getCurrentRank() - maxAge);
    LOGGER.info("getNearby {}", queryString);

    Query query = Query.newBuilder()
      .setOptions(queryOptions)
      .build(queryString);

    Results<ScoredDocument> results = getSearchIndex().search(query);

    ImmutableList.Builder<NearbyUser> listBuilder = ImmutableList.builder();
    for (ScoredDocument scoredDocument : results) {
      //LOGGER.debug("doc {}", scoredDocument);
      NearbyUser user = new NearbyUser();
      String userId = scoredDocument.getOnlyField(FIELD_USER_ID).getAtom();
      user.setId(Long.parseLong(userId));
      user.setCreatedDate(Rank.getDate(scoredDocument.getOnlyField(FIELD_CREATED_DATE).getNumber().intValue()));
      user.setDocumentId(scoredDocument.getId());
      for (Field f : scoredDocument.getExpressions()) {
        if (FIELD_DISTANCE_METERS.equals(f.getName())) {
          user.setDistance(f.getNumber());
        }
      }
      user.setDisplayName(scoredDocument.getOnlyField(FIELD_DISPLAY_NAME).getAtom());
      user.setThumbnailUrl(scoredDocument.getOnlyField(FIELD_THUMBNAIL_URL).getText());
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
    private Float lat;
    private Float lon;

    public String getClubGUID() {
      return clubGUID;
    }

    public void setClubGUID(String clubGUID) {
      this.clubGUID = clubGUID;
    }

    public Float getLat() {
      return lat;
    }

    public void setLat(Float lat) {
      this.lat = lat;
    }

    public Float getLon() {
      return lon;
    }

    public void setLon(Float lon) {
      this.lon = lon;
    }
  }

  public static class NearbyUser {
    private long id;
    private double distance;
    private Date createdDate;
    private String documentId;
    private String displayName;
    private String thumbnailUrl;

    public long getId() {
      return id;
    }

    public void setId(long id) {
      this.id = id;
    }

    public double getDistance() {
      return distance;
    }

    public void setDistance(double distance) {
      this.distance = distance;
    }

    public Date getCreatedDate() {
      return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
      this.createdDate = createdDate;
    }

    public String getDocumentId() {
      return documentId;
    }

    public void setDocumentId(String documentId) {
      this.documentId = documentId;
    }

    public String getDisplayName() {
      return displayName;
    }

    public void setDisplayName(String displayName) {
      this.displayName = displayName;
    }

    public String getThumbnailUrl() {
      return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
      this.thumbnailUrl = thumbnailUrl;
    }
  }
}
