Froghair-be
=============================

Backend for Froghair.

## Nearby Resource

### GET /api/nearby
To get nearby users and golf clubs.

#### Request parameters
*

#### Code
```java
  /**
   * Returns nearby users and golf clubs.
   * @param lat the latitude, defaults to X-AppEngine-CityLatLong
   * @param lon the longitude, defaults to X-AppEngine-CityLatLong
   * @param pageSize defaults to 10
   * @param maxDistance in meters, defaults to 10000
   * @param maxAge in seconds, defaults to 5h.
   * @return a {@link com.wadpam.mardao.util.Pair}&lt;List&lt;DOAuth2User&gt;, List&lt;DClub&gt;&gt;.
   */
  @GET
  public Pair getNearby(@QueryParam("lat") Float lat, @QueryParam("lon") Float lon,
                                                        @QueryParam("pageSize") @DefaultValue("10") int pageSize,
                                                        @QueryParam("maxDistance") @DefaultValue("10000") int maxDistance,
                                                        @QueryParam("maxAge") @DefaultValue("18000") int maxAge);
```