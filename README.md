Froghair-be
=============================

Backend for Froghair.

## Nearby Resource
```java
@Path("api/nearby")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
```

### GET /api/nearby

```java
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
                        @QueryParam("maxAge") @DefaultValue("18000") int maxAge);
```

### POST /api/nearby

```java
  /**
   * Checks in current authenticated user at specified golf club.
   * @param body
   * @return List of other users at the same specified golf club.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public List<DOAuth2User> checkIn(CheckInRequest body);

  public static class CheckInRequest {
    private String clubGUID;
    private Float lat;
    private Float lon;
  }
```
The CheckInRequest body is passed as JSON.
lat/lon defaults to X-AppEngine-CityLatLong