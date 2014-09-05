package com.wadpam.tracker.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.sf.mardao.core.CursorPage;
import net.sf.mardao.core.dao.Dao;
import com.wadpam.tracker.domain.DSplit;
import net.sf.mardao.core.geo.DLocation;

/**
 * DAO interface with finder methods for DSplit entities.
 *
 * Generated on 2014-04-18T20:33:18.209+0200.
 * @author mardao DAO generator (net.sf.mardao.plugin.ProcessDomainMojo)
 */
public interface GeneratedDSplitDao extends Dao<DSplit, java.lang.Long> {

	/** Column name for primary key attribute is "id" */
	static final String COLUMN_NAME_ID = "id";

	/** Column name for parent raceKey is "raceKey" */
	static final String COLUMN_NAME_RACEKEY = "raceKey";


	/** Column name for field createdBy is "createdBy" */
	static final String COLUMN_NAME_CREATEDBY = "createdBy";
	/** Column name for field createdDate is "createdDate" */
	static final String COLUMN_NAME_CREATEDDATE = "createdDate";
	/** Column name for field distance is "distance" */
	static final String COLUMN_NAME_DISTANCE = "distance";
	/** Column name for field elevation is "elevation" */
	static final String COLUMN_NAME_ELEVATION = "elevation";
	/** Column name for field name is "name" */
	static final String COLUMN_NAME_NAME = "name";
	/** Column name for field point is "point" */
	static final String COLUMN_NAME_POINT = "point";
	/** Column name for field timestamp is "timestamp" */
	static final String COLUMN_NAME_TIMESTAMP = "timestamp";
	/** Column name for field trackPointId is "trackPointId" */
	static final String COLUMN_NAME_TRACKPOINTID = "trackPointId";
	/** Column name for field updatedBy is "updatedBy" */
	static final String COLUMN_NAME_UPDATEDBY = "updatedBy";
	/** Column name for field updatedDate is "updatedDate" */
	static final String COLUMN_NAME_UPDATEDDATE = "updatedDate";

	/** The list of attribute names */
	static final List<String> COLUMN_NAMES = Arrays.asList(		COLUMN_NAME_CREATEDBY,
		COLUMN_NAME_CREATEDDATE,
		COLUMN_NAME_DISTANCE,
		COLUMN_NAME_ELEVATION,
		COLUMN_NAME_NAME,
		COLUMN_NAME_POINT,
		COLUMN_NAME_TIMESTAMP,
		COLUMN_NAME_TRACKPOINTID,
		COLUMN_NAME_UPDATEDBY,
		COLUMN_NAME_UPDATEDDATE);
	/** The list of Basic attribute names */
	static final List<String> BASIC_NAMES = Arrays.asList(		COLUMN_NAME_CREATEDBY,
		COLUMN_NAME_CREATEDDATE,
		COLUMN_NAME_DISTANCE,
		COLUMN_NAME_ELEVATION,
		COLUMN_NAME_NAME,
		COLUMN_NAME_POINT,
		COLUMN_NAME_TIMESTAMP,
		COLUMN_NAME_TRACKPOINTID,
		COLUMN_NAME_UPDATEDBY,
		COLUMN_NAME_UPDATEDDATE);
	/** The list of attribute names */
	static final List<String> MANY_TO_ONE_NAMES = Arrays.asList();


	// ----------------------- parent finder -------------------------------
	/**
	 * query-by method for parent field raceKey
	 * @param raceKey the specified attribute
	 * @return an Iterable of DSplits with the specified parent
	 */
	Iterable<DSplit> queryByRaceKey(Object raceKey);
		
	/**
	 * query-keys-by method for parent field raceKey
	 * @param raceKey the specified attribute
	 * @return an Iterable of DSplits with the specified parent
	 */
	Iterable<java.lang.Long> queryKeysByRaceKey(Object raceKey);

	/**
	 * query-page-by method for parent field raceKey
	 * @param raceKey the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DSplits for the specified raceKey (parent)
	 */
	CursorPage<DSplit> queryPageByRaceKey(java.lang.Object raceKey,
            int pageSize, String cursorString);

	// ----------------------- field finders -------------------------------
	/**
	 * query-by method for field createdBy
	 * @param createdBy the specified attribute
	 * @return an Iterable of DSplits for the specified createdBy
	 */
	Iterable<DSplit> queryByCreatedBy(java.lang.String createdBy);
		
	/**
	 * query-keys-by method for field createdBy
	 * @param createdBy the specified attribute
	 * @return an Iterable of DSplits for the specified createdBy
	 */
	Iterable<java.lang.Long> queryKeysByCreatedBy(java.lang.String createdBy);

	/**
	 * query-page-by method for field createdBy
	 * @param createdBy the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DSplits for the specified createdBy
	 */
	CursorPage<DSplit> queryPageByCreatedBy(java.lang.String createdBy,
            int pageSize, String cursorString);


	/**
	 * query-by method for field createdDate
	 * @param createdDate the specified attribute
	 * @return an Iterable of DSplits for the specified createdDate
	 */
	Iterable<DSplit> queryByCreatedDate(java.util.Date createdDate);
		
	/**
	 * query-keys-by method for field createdDate
	 * @param createdDate the specified attribute
	 * @return an Iterable of DSplits for the specified createdDate
	 */
	Iterable<java.lang.Long> queryKeysByCreatedDate(java.util.Date createdDate);

	/**
	 * query-page-by method for field createdDate
	 * @param createdDate the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DSplits for the specified createdDate
	 */
	CursorPage<DSplit> queryPageByCreatedDate(java.util.Date createdDate,
            int pageSize, String cursorString);


	/**
	 * query-by method for field distance
	 * @param distance the specified attribute
	 * @return an Iterable of DSplits for the specified distance
	 */
	Iterable<DSplit> queryByDistance(java.lang.Float distance);
		
	/**
	 * query-keys-by method for field distance
	 * @param distance the specified attribute
	 * @return an Iterable of DSplits for the specified distance
	 */
	Iterable<java.lang.Long> queryKeysByDistance(java.lang.Float distance);

	/**
	 * query-page-by method for field distance
	 * @param distance the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DSplits for the specified distance
	 */
	CursorPage<DSplit> queryPageByDistance(java.lang.Float distance,
            int pageSize, String cursorString);


	/**
	 * query-by method for field elevation
	 * @param elevation the specified attribute
	 * @return an Iterable of DSplits for the specified elevation
	 */
	Iterable<DSplit> queryByElevation(java.lang.Float elevation);
		
	/**
	 * query-keys-by method for field elevation
	 * @param elevation the specified attribute
	 * @return an Iterable of DSplits for the specified elevation
	 */
	Iterable<java.lang.Long> queryKeysByElevation(java.lang.Float elevation);

	/**
	 * query-page-by method for field elevation
	 * @param elevation the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DSplits for the specified elevation
	 */
	CursorPage<DSplit> queryPageByElevation(java.lang.Float elevation,
            int pageSize, String cursorString);


	/**
	 * query-by method for field name
	 * @param name the specified attribute
	 * @return an Iterable of DSplits for the specified name
	 */
	Iterable<DSplit> queryByName(java.lang.String name);
		
	/**
	 * query-keys-by method for field name
	 * @param name the specified attribute
	 * @return an Iterable of DSplits for the specified name
	 */
	Iterable<java.lang.Long> queryKeysByName(java.lang.String name);

	/**
	 * query-page-by method for field name
	 * @param name the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DSplits for the specified name
	 */
	CursorPage<DSplit> queryPageByName(java.lang.String name,
            int pageSize, String cursorString);


	/**
	 * query-by method for field point
	 * @param point the specified attribute
	 * @return an Iterable of DSplits for the specified point
	 */
	Iterable<DSplit> queryByPoint(net.sf.mardao.core.geo.DLocation point);
		
	/**
	 * query-keys-by method for field point
	 * @param point the specified attribute
	 * @return an Iterable of DSplits for the specified point
	 */
	Iterable<java.lang.Long> queryKeysByPoint(net.sf.mardao.core.geo.DLocation point);

	/**
	 * query-page-by method for field point
	 * @param point the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DSplits for the specified point
	 */
	CursorPage<DSplit> queryPageByPoint(net.sf.mardao.core.geo.DLocation point,
            int pageSize, String cursorString);


	/**
	 * query-by method for field timestamp
	 * @param timestamp the specified attribute
	 * @return an Iterable of DSplits for the specified timestamp
	 */
	Iterable<DSplit> queryByTimestamp(java.lang.Long timestamp);
		
	/**
	 * query-keys-by method for field timestamp
	 * @param timestamp the specified attribute
	 * @return an Iterable of DSplits for the specified timestamp
	 */
	Iterable<java.lang.Long> queryKeysByTimestamp(java.lang.Long timestamp);

	/**
	 * query-page-by method for field timestamp
	 * @param timestamp the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DSplits for the specified timestamp
	 */
	CursorPage<DSplit> queryPageByTimestamp(java.lang.Long timestamp,
            int pageSize, String cursorString);


	/**
	 * query-by method for field trackPointId
	 * @param trackPointId the specified attribute
	 * @return an Iterable of DSplits for the specified trackPointId
	 */
	Iterable<DSplit> queryByTrackPointId(java.lang.Long trackPointId);
		
	/**
	 * query-keys-by method for field trackPointId
	 * @param trackPointId the specified attribute
	 * @return an Iterable of DSplits for the specified trackPointId
	 */
	Iterable<java.lang.Long> queryKeysByTrackPointId(java.lang.Long trackPointId);

	/**
	 * query-page-by method for field trackPointId
	 * @param trackPointId the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DSplits for the specified trackPointId
	 */
	CursorPage<DSplit> queryPageByTrackPointId(java.lang.Long trackPointId,
            int pageSize, String cursorString);


	/**
	 * query-by method for field updatedBy
	 * @param updatedBy the specified attribute
	 * @return an Iterable of DSplits for the specified updatedBy
	 */
	Iterable<DSplit> queryByUpdatedBy(java.lang.String updatedBy);
		
	/**
	 * query-keys-by method for field updatedBy
	 * @param updatedBy the specified attribute
	 * @return an Iterable of DSplits for the specified updatedBy
	 */
	Iterable<java.lang.Long> queryKeysByUpdatedBy(java.lang.String updatedBy);

	/**
	 * query-page-by method for field updatedBy
	 * @param updatedBy the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DSplits for the specified updatedBy
	 */
	CursorPage<DSplit> queryPageByUpdatedBy(java.lang.String updatedBy,
            int pageSize, String cursorString);


	/**
	 * query-by method for field updatedDate
	 * @param updatedDate the specified attribute
	 * @return an Iterable of DSplits for the specified updatedDate
	 */
	Iterable<DSplit> queryByUpdatedDate(java.util.Date updatedDate);
		
	/**
	 * query-keys-by method for field updatedDate
	 * @param updatedDate the specified attribute
	 * @return an Iterable of DSplits for the specified updatedDate
	 */
	Iterable<java.lang.Long> queryKeysByUpdatedDate(java.util.Date updatedDate);

	/**
	 * query-page-by method for field updatedDate
	 * @param updatedDate the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DSplits for the specified updatedDate
	 */
	CursorPage<DSplit> queryPageByUpdatedDate(java.util.Date updatedDate,
            int pageSize, String cursorString);


		  
	// ----------------------- one-to-one finders -------------------------

	// ----------------------- many-to-one finders -------------------------
	
	// ----------------------- many-to-many finders -------------------------

	// ----------------------- uniqueFields finders -------------------------
	
	/**
	 * find-by method for unique fields [name, raceKey]
	 * @param name the specified name
	 * @param raceKey the specified raceKey
	 * @return the unique DSplit for the specified fields
	 */
	DSplit findByNameRaceKey(java.lang.String name, java.lang.Object raceKey);

	/**
	 * find-by method for unique fields [raceKey, timestamp]
	 * @param raceKey the specified raceKey
	 * @param timestamp the specified timestamp
	 * @return the unique DSplit for the specified fields
	 */
	DSplit findByRaceKeyTimestamp(java.lang.Object raceKey, java.lang.Long timestamp);

	
	// ----------------------- populate / persist method -------------------------

	/**
	 * Persist an entity given all attributes
	 */
	DSplit persist(Object raceKey,  	
		java.lang.Long id, 
		java.lang.Float distance, 
		java.lang.Float elevation, 
		java.lang.String name, 
		net.sf.mardao.core.geo.DLocation point, 
		java.lang.Long timestamp, 
		java.lang.Long trackPointId);	

}
