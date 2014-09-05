package com.wadpam.tracker.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.sf.mardao.core.CursorPage;
import net.sf.mardao.core.dao.Dao;
import com.wadpam.tracker.domain.DParticipant;
import net.sf.mardao.core.geo.DLocation;

/**
 * DAO interface with finder methods for DParticipant entities.
 *
 * Generated on 2014-04-18T20:33:18.209+0200.
 * @author mardao DAO generator (net.sf.mardao.plugin.ProcessDomainMojo)
 */
public interface GeneratedDParticipantDao extends Dao<DParticipant, java.lang.Long> {

	/** Column name for primary key attribute is "id" */
	static final String COLUMN_NAME_ID = "id";


	/** Column name for field actionId is "actionId" */
	static final String COLUMN_NAME_ACTIONID = "actionId";
	/** Column name for field createdBy is "createdBy" */
	static final String COLUMN_NAME_CREATEDBY = "createdBy";
	/** Column name for field createdDate is "createdDate" */
	static final String COLUMN_NAME_CREATEDDATE = "createdDate";
	/** Column name for field extUserId is "extUserId" */
	static final String COLUMN_NAME_EXTUSERID = "extUserId";
	/** Column name for field raceId is "raceId" */
	static final String COLUMN_NAME_RACEID = "raceId";
	/** Column name for field status is "status" */
	static final String COLUMN_NAME_STATUS = "status";
	/** Column name for field updatedBy is "updatedBy" */
	static final String COLUMN_NAME_UPDATEDBY = "updatedBy";
	/** Column name for field updatedDate is "updatedDate" */
	static final String COLUMN_NAME_UPDATEDDATE = "updatedDate";
	/** Column name for field userId is "userId" */
	static final String COLUMN_NAME_USERID = "userId";

	/** The list of attribute names */
	static final List<String> COLUMN_NAMES = Arrays.asList(		COLUMN_NAME_ACTIONID,
		COLUMN_NAME_CREATEDBY,
		COLUMN_NAME_CREATEDDATE,
		COLUMN_NAME_EXTUSERID,
		COLUMN_NAME_RACEID,
		COLUMN_NAME_STATUS,
		COLUMN_NAME_UPDATEDBY,
		COLUMN_NAME_UPDATEDDATE,
		COLUMN_NAME_USERID);
	/** The list of Basic attribute names */
	static final List<String> BASIC_NAMES = Arrays.asList(		COLUMN_NAME_ACTIONID,
		COLUMN_NAME_CREATEDBY,
		COLUMN_NAME_CREATEDDATE,
		COLUMN_NAME_EXTUSERID,
		COLUMN_NAME_RACEID,
		COLUMN_NAME_STATUS,
		COLUMN_NAME_UPDATEDBY,
		COLUMN_NAME_UPDATEDDATE,
		COLUMN_NAME_USERID);
	/** The list of attribute names */
	static final List<String> MANY_TO_ONE_NAMES = Arrays.asList();


	// ----------------------- field finders -------------------------------
	/**
	 * find-by method for unique field actionId
	 * @param actionId the unique attribute
	 * @return the unique DParticipant for the specified actionId
	 */
	DParticipant findByActionId(java.lang.String actionId);

        /**
	 * find-key-by method for unique attribute field actionId
	 * @param actionId the unique attribute
	 * @return the unique DParticipant for the specified attribute
	 */
	java.lang.Long findKeyByActionId(java.lang.String actionId);

	/**
	 * query-by method for field createdBy
	 * @param createdBy the specified attribute
	 * @return an Iterable of DParticipants for the specified createdBy
	 */
	Iterable<DParticipant> queryByCreatedBy(java.lang.String createdBy);
		
	/**
	 * query-keys-by method for field createdBy
	 * @param createdBy the specified attribute
	 * @return an Iterable of DParticipants for the specified createdBy
	 */
	Iterable<java.lang.Long> queryKeysByCreatedBy(java.lang.String createdBy);

	/**
	 * query-page-by method for field createdBy
	 * @param createdBy the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DParticipants for the specified createdBy
	 */
	CursorPage<DParticipant> queryPageByCreatedBy(java.lang.String createdBy,
            int pageSize, String cursorString);


	/**
	 * query-by method for field createdDate
	 * @param createdDate the specified attribute
	 * @return an Iterable of DParticipants for the specified createdDate
	 */
	Iterable<DParticipant> queryByCreatedDate(java.util.Date createdDate);
		
	/**
	 * query-keys-by method for field createdDate
	 * @param createdDate the specified attribute
	 * @return an Iterable of DParticipants for the specified createdDate
	 */
	Iterable<java.lang.Long> queryKeysByCreatedDate(java.util.Date createdDate);

	/**
	 * query-page-by method for field createdDate
	 * @param createdDate the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DParticipants for the specified createdDate
	 */
	CursorPage<DParticipant> queryPageByCreatedDate(java.util.Date createdDate,
            int pageSize, String cursorString);


	/**
	 * query-by method for field extUserId
	 * @param extUserId the specified attribute
	 * @return an Iterable of DParticipants for the specified extUserId
	 */
	Iterable<DParticipant> queryByExtUserId(java.lang.String extUserId);
		
	/**
	 * query-keys-by method for field extUserId
	 * @param extUserId the specified attribute
	 * @return an Iterable of DParticipants for the specified extUserId
	 */
	Iterable<java.lang.Long> queryKeysByExtUserId(java.lang.String extUserId);

	/**
	 * query-page-by method for field extUserId
	 * @param extUserId the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DParticipants for the specified extUserId
	 */
	CursorPage<DParticipant> queryPageByExtUserId(java.lang.String extUserId,
            int pageSize, String cursorString);


	/**
	 * query-by method for field raceId
	 * @param raceId the specified attribute
	 * @return an Iterable of DParticipants for the specified raceId
	 */
	Iterable<DParticipant> queryByRaceId(java.lang.Long raceId);
		
	/**
	 * query-keys-by method for field raceId
	 * @param raceId the specified attribute
	 * @return an Iterable of DParticipants for the specified raceId
	 */
	Iterable<java.lang.Long> queryKeysByRaceId(java.lang.Long raceId);

	/**
	 * query-page-by method for field raceId
	 * @param raceId the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DParticipants for the specified raceId
	 */
	CursorPage<DParticipant> queryPageByRaceId(java.lang.Long raceId,
            int pageSize, String cursorString);


	/**
	 * query-by method for field status
	 * @param status the specified attribute
	 * @return an Iterable of DParticipants for the specified status
	 */
	Iterable<DParticipant> queryByStatus(java.lang.Integer status);
		
	/**
	 * query-keys-by method for field status
	 * @param status the specified attribute
	 * @return an Iterable of DParticipants for the specified status
	 */
	Iterable<java.lang.Long> queryKeysByStatus(java.lang.Integer status);

	/**
	 * query-page-by method for field status
	 * @param status the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DParticipants for the specified status
	 */
	CursorPage<DParticipant> queryPageByStatus(java.lang.Integer status,
            int pageSize, String cursorString);


	/**
	 * query-by method for field updatedBy
	 * @param updatedBy the specified attribute
	 * @return an Iterable of DParticipants for the specified updatedBy
	 */
	Iterable<DParticipant> queryByUpdatedBy(java.lang.String updatedBy);
		
	/**
	 * query-keys-by method for field updatedBy
	 * @param updatedBy the specified attribute
	 * @return an Iterable of DParticipants for the specified updatedBy
	 */
	Iterable<java.lang.Long> queryKeysByUpdatedBy(java.lang.String updatedBy);

	/**
	 * query-page-by method for field updatedBy
	 * @param updatedBy the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DParticipants for the specified updatedBy
	 */
	CursorPage<DParticipant> queryPageByUpdatedBy(java.lang.String updatedBy,
            int pageSize, String cursorString);


	/**
	 * query-by method for field updatedDate
	 * @param updatedDate the specified attribute
	 * @return an Iterable of DParticipants for the specified updatedDate
	 */
	Iterable<DParticipant> queryByUpdatedDate(java.util.Date updatedDate);
		
	/**
	 * query-keys-by method for field updatedDate
	 * @param updatedDate the specified attribute
	 * @return an Iterable of DParticipants for the specified updatedDate
	 */
	Iterable<java.lang.Long> queryKeysByUpdatedDate(java.util.Date updatedDate);

	/**
	 * query-page-by method for field updatedDate
	 * @param updatedDate the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DParticipants for the specified updatedDate
	 */
	CursorPage<DParticipant> queryPageByUpdatedDate(java.util.Date updatedDate,
            int pageSize, String cursorString);


	/**
	 * query-by method for field userId
	 * @param userId the specified attribute
	 * @return an Iterable of DParticipants for the specified userId
	 */
	Iterable<DParticipant> queryByUserId(java.lang.Long userId);
		
	/**
	 * query-keys-by method for field userId
	 * @param userId the specified attribute
	 * @return an Iterable of DParticipants for the specified userId
	 */
	Iterable<java.lang.Long> queryKeysByUserId(java.lang.Long userId);

	/**
	 * query-page-by method for field userId
	 * @param userId the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DParticipants for the specified userId
	 */
	CursorPage<DParticipant> queryPageByUserId(java.lang.Long userId,
            int pageSize, String cursorString);


		  
	// ----------------------- one-to-one finders -------------------------

	// ----------------------- many-to-one finders -------------------------
	
	// ----------------------- many-to-many finders -------------------------

	// ----------------------- uniqueFields finders -------------------------
	
	/**
	 * find-by method for unique fields [raceId, userId]
	 * @param raceId the specified raceId
	 * @param userId the specified userId
	 * @return the unique DParticipant for the specified fields
	 */
	DParticipant findByRaceIdUserId(java.lang.Long raceId, java.lang.Long userId);

	
	// ----------------------- populate / persist method -------------------------

	/**
	 * Persist an entity given all attributes
	 */
	DParticipant persist(		java.lang.Long id, 
		java.lang.String actionId, 
		java.lang.String extUserId, 
		java.lang.Long raceId, 
		java.lang.Integer status, 
		java.lang.Long userId);	

	/**
	 * Persists an entity unless it already exists
	 */
	 DParticipant persist(java.lang.String actionId, 
                java.lang.String extUserId, 
                java.lang.Long raceId, 
                java.lang.Integer status, 
                java.lang.Long userId);

}
