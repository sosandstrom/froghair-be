package com.wadpam.tracker.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.sf.mardao.core.CursorPage;
import net.sf.mardao.core.dao.Dao;
import com.wadpam.tracker.domain.DRace;
import net.sf.mardao.core.geo.DLocation;

/**
 * DAO interface with finder methods for DRace entities.
 *
 * Generated on 2014-04-18T20:33:18.209+0200.
 * @author mardao DAO generator (net.sf.mardao.plugin.ProcessDomainMojo)
 */
public interface GeneratedDRaceDao extends Dao<DRace, java.lang.Long> {

	/** Column name for primary key attribute is "id" */
	static final String COLUMN_NAME_ID = "id";


	/** Column name for field blobKey is "blobKey" */
	static final String COLUMN_NAME_BLOBKEY = "blobKey";
	/** Column name for field createdBy is "createdBy" */
	static final String COLUMN_NAME_CREATEDBY = "createdBy";
	/** Column name for field createdDate is "createdDate" */
	static final String COLUMN_NAME_CREATEDDATE = "createdDate";
	/** Column name for field displayName is "displayName" */
	static final String COLUMN_NAME_DISPLAYNAME = "displayName";
	/** Column name for field extractorClassname is "extractorClassname" */
	static final String COLUMN_NAME_EXTRACTORCLASSNAME = "extractorClassname";
	/** Column name for field imageUri is "imageUri" */
	static final String COLUMN_NAME_IMAGEURI = "imageUri";
	/** Column name for field queryUrl is "queryUrl" */
	static final String COLUMN_NAME_QUERYURL = "queryUrl";
	/** Column name for field startDate is "startDate" */
	static final String COLUMN_NAME_STARTDATE = "startDate";
	/** Column name for field timeZone is "timeZone" */
	static final String COLUMN_NAME_TIMEZONE = "timeZone";
	/** Column name for field updatedBy is "updatedBy" */
	static final String COLUMN_NAME_UPDATEDBY = "updatedBy";
	/** Column name for field updatedDate is "updatedDate" */
	static final String COLUMN_NAME_UPDATEDDATE = "updatedDate";

	/** The list of attribute names */
	static final List<String> COLUMN_NAMES = Arrays.asList(		COLUMN_NAME_BLOBKEY,
		COLUMN_NAME_CREATEDBY,
		COLUMN_NAME_CREATEDDATE,
		COLUMN_NAME_DISPLAYNAME,
		COLUMN_NAME_EXTRACTORCLASSNAME,
		COLUMN_NAME_IMAGEURI,
		COLUMN_NAME_QUERYURL,
		COLUMN_NAME_STARTDATE,
		COLUMN_NAME_TIMEZONE,
		COLUMN_NAME_UPDATEDBY,
		COLUMN_NAME_UPDATEDDATE);
	/** The list of Basic attribute names */
	static final List<String> BASIC_NAMES = Arrays.asList(		COLUMN_NAME_BLOBKEY,
		COLUMN_NAME_CREATEDBY,
		COLUMN_NAME_CREATEDDATE,
		COLUMN_NAME_DISPLAYNAME,
		COLUMN_NAME_EXTRACTORCLASSNAME,
		COLUMN_NAME_IMAGEURI,
		COLUMN_NAME_QUERYURL,
		COLUMN_NAME_STARTDATE,
		COLUMN_NAME_TIMEZONE,
		COLUMN_NAME_UPDATEDBY,
		COLUMN_NAME_UPDATEDDATE);
	/** The list of attribute names */
	static final List<String> MANY_TO_ONE_NAMES = Arrays.asList();


	// ----------------------- field finders -------------------------------
	/**
	 * query-by method for field blobKey
	 * @param blobKey the specified attribute
	 * @return an Iterable of DRaces for the specified blobKey
	 */
	Iterable<DRace> queryByBlobKey(com.google.appengine.api.blobstore.BlobKey blobKey);
		
	/**
	 * query-keys-by method for field blobKey
	 * @param blobKey the specified attribute
	 * @return an Iterable of DRaces for the specified blobKey
	 */
	Iterable<java.lang.Long> queryKeysByBlobKey(com.google.appengine.api.blobstore.BlobKey blobKey);

	/**
	 * query-page-by method for field blobKey
	 * @param blobKey the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified blobKey
	 */
	CursorPage<DRace> queryPageByBlobKey(com.google.appengine.api.blobstore.BlobKey blobKey,
            int pageSize, String cursorString);


	/**
	 * query-by method for field createdBy
	 * @param createdBy the specified attribute
	 * @return an Iterable of DRaces for the specified createdBy
	 */
	Iterable<DRace> queryByCreatedBy(java.lang.String createdBy);
		
	/**
	 * query-keys-by method for field createdBy
	 * @param createdBy the specified attribute
	 * @return an Iterable of DRaces for the specified createdBy
	 */
	Iterable<java.lang.Long> queryKeysByCreatedBy(java.lang.String createdBy);

	/**
	 * query-page-by method for field createdBy
	 * @param createdBy the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified createdBy
	 */
	CursorPage<DRace> queryPageByCreatedBy(java.lang.String createdBy,
            int pageSize, String cursorString);


	/**
	 * query-by method for field createdDate
	 * @param createdDate the specified attribute
	 * @return an Iterable of DRaces for the specified createdDate
	 */
	Iterable<DRace> queryByCreatedDate(java.util.Date createdDate);
		
	/**
	 * query-keys-by method for field createdDate
	 * @param createdDate the specified attribute
	 * @return an Iterable of DRaces for the specified createdDate
	 */
	Iterable<java.lang.Long> queryKeysByCreatedDate(java.util.Date createdDate);

	/**
	 * query-page-by method for field createdDate
	 * @param createdDate the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified createdDate
	 */
	CursorPage<DRace> queryPageByCreatedDate(java.util.Date createdDate,
            int pageSize, String cursorString);


	/**
	 * query-by method for field displayName
	 * @param displayName the specified attribute
	 * @return an Iterable of DRaces for the specified displayName
	 */
	Iterable<DRace> queryByDisplayName(java.lang.String displayName);
		
	/**
	 * query-keys-by method for field displayName
	 * @param displayName the specified attribute
	 * @return an Iterable of DRaces for the specified displayName
	 */
	Iterable<java.lang.Long> queryKeysByDisplayName(java.lang.String displayName);

	/**
	 * query-page-by method for field displayName
	 * @param displayName the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified displayName
	 */
	CursorPage<DRace> queryPageByDisplayName(java.lang.String displayName,
            int pageSize, String cursorString);


	/**
	 * query-by method for field extractorClassname
	 * @param extractorClassname the specified attribute
	 * @return an Iterable of DRaces for the specified extractorClassname
	 */
	Iterable<DRace> queryByExtractorClassname(java.lang.String extractorClassname);
		
	/**
	 * query-keys-by method for field extractorClassname
	 * @param extractorClassname the specified attribute
	 * @return an Iterable of DRaces for the specified extractorClassname
	 */
	Iterable<java.lang.Long> queryKeysByExtractorClassname(java.lang.String extractorClassname);

	/**
	 * query-page-by method for field extractorClassname
	 * @param extractorClassname the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified extractorClassname
	 */
	CursorPage<DRace> queryPageByExtractorClassname(java.lang.String extractorClassname,
            int pageSize, String cursorString);


	/**
	 * query-by method for field imageUri
	 * @param imageUri the specified attribute
	 * @return an Iterable of DRaces for the specified imageUri
	 */
	Iterable<DRace> queryByImageUri(java.lang.String imageUri);
		
	/**
	 * query-keys-by method for field imageUri
	 * @param imageUri the specified attribute
	 * @return an Iterable of DRaces for the specified imageUri
	 */
	Iterable<java.lang.Long> queryKeysByImageUri(java.lang.String imageUri);

	/**
	 * query-page-by method for field imageUri
	 * @param imageUri the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified imageUri
	 */
	CursorPage<DRace> queryPageByImageUri(java.lang.String imageUri,
            int pageSize, String cursorString);


	/**
	 * query-by method for field queryUrl
	 * @param queryUrl the specified attribute
	 * @return an Iterable of DRaces for the specified queryUrl
	 */
	Iterable<DRace> queryByQueryUrl(java.lang.String queryUrl);
		
	/**
	 * query-keys-by method for field queryUrl
	 * @param queryUrl the specified attribute
	 * @return an Iterable of DRaces for the specified queryUrl
	 */
	Iterable<java.lang.Long> queryKeysByQueryUrl(java.lang.String queryUrl);

	/**
	 * query-page-by method for field queryUrl
	 * @param queryUrl the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified queryUrl
	 */
	CursorPage<DRace> queryPageByQueryUrl(java.lang.String queryUrl,
            int pageSize, String cursorString);


	/**
	 * query-by method for field startDate
	 * @param startDate the specified attribute
	 * @return an Iterable of DRaces for the specified startDate
	 */
	Iterable<DRace> queryByStartDate(java.util.Date startDate);
		
	/**
	 * query-keys-by method for field startDate
	 * @param startDate the specified attribute
	 * @return an Iterable of DRaces for the specified startDate
	 */
	Iterable<java.lang.Long> queryKeysByStartDate(java.util.Date startDate);

	/**
	 * query-page-by method for field startDate
	 * @param startDate the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified startDate
	 */
	CursorPage<DRace> queryPageByStartDate(java.util.Date startDate,
            int pageSize, String cursorString);


	/**
	 * query-by method for field timeZone
	 * @param timeZone the specified attribute
	 * @return an Iterable of DRaces for the specified timeZone
	 */
	Iterable<DRace> queryByTimeZone(java.lang.String timeZone);
		
	/**
	 * query-keys-by method for field timeZone
	 * @param timeZone the specified attribute
	 * @return an Iterable of DRaces for the specified timeZone
	 */
	Iterable<java.lang.Long> queryKeysByTimeZone(java.lang.String timeZone);

	/**
	 * query-page-by method for field timeZone
	 * @param timeZone the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified timeZone
	 */
	CursorPage<DRace> queryPageByTimeZone(java.lang.String timeZone,
            int pageSize, String cursorString);


	/**
	 * query-by method for field updatedBy
	 * @param updatedBy the specified attribute
	 * @return an Iterable of DRaces for the specified updatedBy
	 */
	Iterable<DRace> queryByUpdatedBy(java.lang.String updatedBy);
		
	/**
	 * query-keys-by method for field updatedBy
	 * @param updatedBy the specified attribute
	 * @return an Iterable of DRaces for the specified updatedBy
	 */
	Iterable<java.lang.Long> queryKeysByUpdatedBy(java.lang.String updatedBy);

	/**
	 * query-page-by method for field updatedBy
	 * @param updatedBy the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified updatedBy
	 */
	CursorPage<DRace> queryPageByUpdatedBy(java.lang.String updatedBy,
            int pageSize, String cursorString);


	/**
	 * query-by method for field updatedDate
	 * @param updatedDate the specified attribute
	 * @return an Iterable of DRaces for the specified updatedDate
	 */
	Iterable<DRace> queryByUpdatedDate(java.util.Date updatedDate);
		
	/**
	 * query-keys-by method for field updatedDate
	 * @param updatedDate the specified attribute
	 * @return an Iterable of DRaces for the specified updatedDate
	 */
	Iterable<java.lang.Long> queryKeysByUpdatedDate(java.util.Date updatedDate);

	/**
	 * query-page-by method for field updatedDate
	 * @param updatedDate the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified updatedDate
	 */
	CursorPage<DRace> queryPageByUpdatedDate(java.util.Date updatedDate,
            int pageSize, String cursorString);


		  
	// ----------------------- one-to-one finders -------------------------

	// ----------------------- many-to-one finders -------------------------
	
	// ----------------------- many-to-many finders -------------------------

	// ----------------------- uniqueFields finders -------------------------
	
	
	// ----------------------- populate / persist method -------------------------

	/**
	 * Persist an entity given all attributes
	 */
	DRace persist(		java.lang.Long id, 
		com.google.appengine.api.blobstore.BlobKey blobKey, 
		java.lang.String displayName, 
		java.lang.String extractorClassname, 
		java.lang.String imageUri, 
		java.lang.String queryUrl, 
		java.util.Date startDate, 
		java.lang.String timeZone);	

}
