package com.wadpam.tracker.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import net.sf.mardao.core.CursorPage;
import net.sf.mardao.core.Filter;
import net.sf.mardao.core.dao.DaoImpl;
import net.sf.mardao.core.dao.TypeDaoImpl;
import net.sf.mardao.core.geo.DLocation;
import com.wadpam.tracker.domain.DRace;

/**
 * The DRace domain-object specific finders and methods go in this POJO.
 * 
 * Generated on 2014-04-18T20:33:18.209+0200.
 * @author mardao DAO generator (net.sf.mardao.plugin.ProcessDomainMojo)
 */
public class GeneratedDRaceDaoImpl extends TypeDaoImpl<DRace, java.lang.Long> 
	implements GeneratedDRaceDao {


    /** to list the property names for ManyToOne relations */
    @Override
    protected List<String> getBasicColumnNames() {
        return BASIC_NAMES;
    }

    /** to list the property names for ManyToOne relations */
    @Override
    protected List<String> getManyToOneColumnNames() {
        return MANY_TO_ONE_NAMES;
    }

    private final Map<String, DaoImpl> MANY_TO_ONE_DAOS = new TreeMap<String, DaoImpl>();

    /** Default constructor */
   public GeneratedDRaceDaoImpl() {
      super(DRace.class, java.lang.Long.class);
   }

   // ------ BEGIN DaoImpl overrides -----------------------------
   
   public String getPrimaryKeyColumnName() {
   		return COLUMN_NAME_ID;
   }
   
   public List<String> getColumnNames() {
        return COLUMN_NAMES;
   }

   @Override
   protected DaoImpl getManyToOneDao(String columnName) {
       return MANY_TO_ONE_DAOS.get(columnName);
   }

    @Override
    protected Object getDomainProperty(DRace domain, String name) {
        Object value;
        // simple key?
        if (COLUMN_NAME_ID.equals(name)) {
            value = domain.getId();
        }
        // fields
        else if (COLUMN_NAME_BLOBKEY.equals(name)) {
            value = domain.getBlobKey();
        }
        else if (COLUMN_NAME_CREATEDBY.equals(name)) {
            value = domain.getCreatedBy();
        }
        else if (COLUMN_NAME_CREATEDDATE.equals(name)) {
            value = domain.getCreatedDate();
        }
        else if (COLUMN_NAME_DISPLAYNAME.equals(name)) {
            value = domain.getDisplayName();
        }
        else if (COLUMN_NAME_EXTRACTORCLASSNAME.equals(name)) {
            value = domain.getExtractorClassname();
        }
        else if (COLUMN_NAME_IMAGEURI.equals(name)) {
            value = domain.getImageUri();
        }
        else if (COLUMN_NAME_QUERYURL.equals(name)) {
            value = domain.getQueryUrl();
        }
        else if (COLUMN_NAME_STARTDATE.equals(name)) {
            value = domain.getStartDate();
        }
        else if (COLUMN_NAME_TIMEZONE.equals(name)) {
            value = domain.getTimeZone();
        }
        else if (COLUMN_NAME_UPDATEDBY.equals(name)) {
            value = domain.getUpdatedBy();
        }
        else if (COLUMN_NAME_UPDATEDDATE.equals(name)) {
            value = domain.getUpdatedDate();
        }
        // one-to-ones
        // many-to-ones
        // many-to-manys
        else {
            value = super.getDomainProperty(domain, name);
        }

        return value;
    }

    /**
     * Returns the class of the domain property for specified column
     * @param name
     * @return the class of the domain property
     */
    public Class getColumnClass(String name) {
        Class clazz;
        // simple key?
        if (COLUMN_NAME_ID.equals(name)) {
            clazz = java.lang.Long.class;
        }
        // fields
        else if (COLUMN_NAME_BLOBKEY.equals(name)) {
            clazz = com.google.appengine.api.blobstore.BlobKey.class;
        }
        else if (COLUMN_NAME_CREATEDBY.equals(name)) {
            clazz = java.lang.String.class;
        }
        else if (COLUMN_NAME_CREATEDDATE.equals(name)) {
            clazz = java.util.Date.class;
        }
        else if (COLUMN_NAME_DISPLAYNAME.equals(name)) {
            clazz = java.lang.String.class;
        }
        else if (COLUMN_NAME_EXTRACTORCLASSNAME.equals(name)) {
            clazz = java.lang.String.class;
        }
        else if (COLUMN_NAME_IMAGEURI.equals(name)) {
            clazz = java.lang.String.class;
        }
        else if (COLUMN_NAME_QUERYURL.equals(name)) {
            clazz = java.lang.String.class;
        }
        else if (COLUMN_NAME_STARTDATE.equals(name)) {
            clazz = java.util.Date.class;
        }
        else if (COLUMN_NAME_TIMEZONE.equals(name)) {
            clazz = java.lang.String.class;
        }
        else if (COLUMN_NAME_UPDATEDBY.equals(name)) {
            clazz = java.lang.String.class;
        }
        else if (COLUMN_NAME_UPDATEDDATE.equals(name)) {
            clazz = java.util.Date.class;
        }
        // one-to-ones
        // many-to-ones
        // many-to-manys
        else {
            throw new IllegalArgumentException("No such column " + name);
        }

        return clazz;
    }
      
    @Override
    protected void setDomainProperty(final DRace domain, final String name, final Object value) {
        // simple key?
        if (COLUMN_NAME_ID.equals(name)) {
            domain.setId((java.lang.Long) value);
        }
        // fields
        else if (COLUMN_NAME_BLOBKEY.equals(name)) {
            domain.setBlobKey((com.google.appengine.api.blobstore.BlobKey) value);
        }
        else if (COLUMN_NAME_CREATEDBY.equals(name)) {
            domain.setCreatedBy((java.lang.String) value);
        }
        else if (COLUMN_NAME_CREATEDDATE.equals(name)) {
            domain.setCreatedDate((java.util.Date) value);
        }
        else if (COLUMN_NAME_DISPLAYNAME.equals(name)) {
            domain.setDisplayName((java.lang.String) value);
        }
        else if (COLUMN_NAME_EXTRACTORCLASSNAME.equals(name)) {
            domain.setExtractorClassname((java.lang.String) value);
        }
        else if (COLUMN_NAME_IMAGEURI.equals(name)) {
            domain.setImageUri((java.lang.String) value);
        }
        else if (COLUMN_NAME_QUERYURL.equals(name)) {
            domain.setQueryUrl((java.lang.String) value);
        }
        else if (COLUMN_NAME_STARTDATE.equals(name)) {
            domain.setStartDate((java.util.Date) value);
        }
        else if (COLUMN_NAME_TIMEZONE.equals(name)) {
            domain.setTimeZone((java.lang.String) value);
        }
        else if (COLUMN_NAME_UPDATEDBY.equals(name)) {
            domain.setUpdatedBy((java.lang.String) value);
        }
        else if (COLUMN_NAME_UPDATEDDATE.equals(name)) {
            domain.setUpdatedDate((java.util.Date) value);
        }
        // one-to-ones
        // many-to-ones
        // many-to-manys
        else {
            super.setDomainProperty(domain, name, value);
        }
    }

    @Override
    protected void setDomainStringProperty(final DRace domain, final String name, final Map<String, String> properties) {
        final String value = properties.get(name);
        Class clazz = getColumnClass(name);
        // many-to-ones
        setDomainProperty(domain, name, parseProperty(value, clazz));
    }

    /**
     * Overrides to substitute Entity properties with foreign keys
     */
    @Override
    protected void setCoreProperty(Object core, String name, Object value) {
        if (null == core || null == name) {
            return;
        }
        else if (null == value) {
            // do nothing in particular, will call super at end
        }
        super.setCoreProperty(core, name, value);
    }

   // ------ END DaoImpl overrides -----------------------------

        // DRace has no parent

        /**
         * @return the simple key for specified DRace domain object
         */
        public Long getSimpleKey(DRace domain) {
            if (null == domain) {
                return null;
            }
            return domain.getId();
        }

        /**
         * @return the simple key for specified DRace domain object
         */
        public void setSimpleKey(DRace domain, Long id) {
            domain.setId(id);
        }

        public String getCreatedByColumnName() {
            return COLUMN_NAME_CREATEDBY;
        }

        public String getCreatedBy(DRace domain) {
            if (null == domain) {
                return null;
            }
            return domain.getCreatedBy();
        }

        public void _setCreatedBy(DRace domain, String creator) {
            domain.setCreatedBy(creator);
        }

        public String getUpdatedByColumnName() {
            return COLUMN_NAME_UPDATEDBY;
        }

        public String getUpdatedBy(DRace domain) {
            if (null == domain) {
                return null;
            }
            return domain.getUpdatedBy();
        }

        public void _setUpdatedBy(DRace domain, String updator) {
            domain.setUpdatedBy(updator);
        }

        public String getCreatedDateColumnName() {
            return COLUMN_NAME_CREATEDDATE;
        }

        public Date getCreatedDate(DRace domain) {
            if (null == domain) {
                return null;
            }
            return domain.getCreatedDate();
        }

        public void _setCreatedDate(DRace domain, Date date) {
            domain.setCreatedDate(date);
        }

        public String getUpdatedDateColumnName() {
            return COLUMN_NAME_UPDATEDDATE;
        }

        public Date getUpdatedDate(DRace domain) {
            if (null == domain) {
                return null;
            }
            return domain.getUpdatedDate();
        }

        public void _setUpdatedDate(DRace domain, Date date) {
            domain.setUpdatedDate(date);
        }

	// ----------------------- field finders -------------------------------
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DRace> queryByBlobKey(com.google.appengine.api.blobstore.BlobKey blobKey) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_BLOBKEY, blobKey);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field blobKey
	 * @param blobKey the specified attribute
	 * @return an Iterable of keys to the DRaces with the specified attribute
	 */
	public final Iterable<java.lang.Long> queryKeysByBlobKey(com.google.appengine.api.blobstore.BlobKey blobKey) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_BLOBKEY, blobKey);
            return queryIterableKeys(0, -1, null, null, null, false, null, false, filter);
	}

	/**
	 * query-page-by method for field blobKey
	 * @param blobKey the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified blobKey
	 */
	public final CursorPage<DRace> queryPageByBlobKey(com.google.appengine.api.blobstore.BlobKey blobKey,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_BLOBKEY, blobKey);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DRace> queryByCreatedBy(java.lang.String createdBy) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_CREATEDBY, createdBy);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field createdBy
	 * @param createdBy the specified attribute
	 * @return an Iterable of keys to the DRaces with the specified attribute
	 */
	public final Iterable<java.lang.Long> queryKeysByCreatedBy(java.lang.String createdBy) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_CREATEDBY, createdBy);
            return queryIterableKeys(0, -1, null, null, null, false, null, false, filter);
	}

	/**
	 * query-page-by method for field createdBy
	 * @param createdBy the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified createdBy
	 */
	public final CursorPage<DRace> queryPageByCreatedBy(java.lang.String createdBy,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_CREATEDBY, createdBy);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DRace> queryByCreatedDate(java.util.Date createdDate) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_CREATEDDATE, createdDate);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field createdDate
	 * @param createdDate the specified attribute
	 * @return an Iterable of keys to the DRaces with the specified attribute
	 */
	public final Iterable<java.lang.Long> queryKeysByCreatedDate(java.util.Date createdDate) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_CREATEDDATE, createdDate);
            return queryIterableKeys(0, -1, null, null, null, false, null, false, filter);
	}

	/**
	 * query-page-by method for field createdDate
	 * @param createdDate the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified createdDate
	 */
	public final CursorPage<DRace> queryPageByCreatedDate(java.util.Date createdDate,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_CREATEDDATE, createdDate);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DRace> queryByDisplayName(java.lang.String displayName) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_DISPLAYNAME, displayName);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field displayName
	 * @param displayName the specified attribute
	 * @return an Iterable of keys to the DRaces with the specified attribute
	 */
	public final Iterable<java.lang.Long> queryKeysByDisplayName(java.lang.String displayName) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_DISPLAYNAME, displayName);
            return queryIterableKeys(0, -1, null, null, null, false, null, false, filter);
	}

	/**
	 * query-page-by method for field displayName
	 * @param displayName the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified displayName
	 */
	public final CursorPage<DRace> queryPageByDisplayName(java.lang.String displayName,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_DISPLAYNAME, displayName);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DRace> queryByExtractorClassname(java.lang.String extractorClassname) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_EXTRACTORCLASSNAME, extractorClassname);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field extractorClassname
	 * @param extractorClassname the specified attribute
	 * @return an Iterable of keys to the DRaces with the specified attribute
	 */
	public final Iterable<java.lang.Long> queryKeysByExtractorClassname(java.lang.String extractorClassname) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_EXTRACTORCLASSNAME, extractorClassname);
            return queryIterableKeys(0, -1, null, null, null, false, null, false, filter);
	}

	/**
	 * query-page-by method for field extractorClassname
	 * @param extractorClassname the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified extractorClassname
	 */
	public final CursorPage<DRace> queryPageByExtractorClassname(java.lang.String extractorClassname,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_EXTRACTORCLASSNAME, extractorClassname);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DRace> queryByImageUri(java.lang.String imageUri) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_IMAGEURI, imageUri);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field imageUri
	 * @param imageUri the specified attribute
	 * @return an Iterable of keys to the DRaces with the specified attribute
	 */
	public final Iterable<java.lang.Long> queryKeysByImageUri(java.lang.String imageUri) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_IMAGEURI, imageUri);
            return queryIterableKeys(0, -1, null, null, null, false, null, false, filter);
	}

	/**
	 * query-page-by method for field imageUri
	 * @param imageUri the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified imageUri
	 */
	public final CursorPage<DRace> queryPageByImageUri(java.lang.String imageUri,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_IMAGEURI, imageUri);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DRace> queryByQueryUrl(java.lang.String queryUrl) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_QUERYURL, queryUrl);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field queryUrl
	 * @param queryUrl the specified attribute
	 * @return an Iterable of keys to the DRaces with the specified attribute
	 */
	public final Iterable<java.lang.Long> queryKeysByQueryUrl(java.lang.String queryUrl) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_QUERYURL, queryUrl);
            return queryIterableKeys(0, -1, null, null, null, false, null, false, filter);
	}

	/**
	 * query-page-by method for field queryUrl
	 * @param queryUrl the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified queryUrl
	 */
	public final CursorPage<DRace> queryPageByQueryUrl(java.lang.String queryUrl,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_QUERYURL, queryUrl);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DRace> queryByStartDate(java.util.Date startDate) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_STARTDATE, startDate);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field startDate
	 * @param startDate the specified attribute
	 * @return an Iterable of keys to the DRaces with the specified attribute
	 */
	public final Iterable<java.lang.Long> queryKeysByStartDate(java.util.Date startDate) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_STARTDATE, startDate);
            return queryIterableKeys(0, -1, null, null, null, false, null, false, filter);
	}

	/**
	 * query-page-by method for field startDate
	 * @param startDate the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified startDate
	 */
	public final CursorPage<DRace> queryPageByStartDate(java.util.Date startDate,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_STARTDATE, startDate);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DRace> queryByTimeZone(java.lang.String timeZone) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_TIMEZONE, timeZone);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field timeZone
	 * @param timeZone the specified attribute
	 * @return an Iterable of keys to the DRaces with the specified attribute
	 */
	public final Iterable<java.lang.Long> queryKeysByTimeZone(java.lang.String timeZone) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_TIMEZONE, timeZone);
            return queryIterableKeys(0, -1, null, null, null, false, null, false, filter);
	}

	/**
	 * query-page-by method for field timeZone
	 * @param timeZone the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified timeZone
	 */
	public final CursorPage<DRace> queryPageByTimeZone(java.lang.String timeZone,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_TIMEZONE, timeZone);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DRace> queryByUpdatedBy(java.lang.String updatedBy) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_UPDATEDBY, updatedBy);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field updatedBy
	 * @param updatedBy the specified attribute
	 * @return an Iterable of keys to the DRaces with the specified attribute
	 */
	public final Iterable<java.lang.Long> queryKeysByUpdatedBy(java.lang.String updatedBy) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_UPDATEDBY, updatedBy);
            return queryIterableKeys(0, -1, null, null, null, false, null, false, filter);
	}

	/**
	 * query-page-by method for field updatedBy
	 * @param updatedBy the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified updatedBy
	 */
	public final CursorPage<DRace> queryPageByUpdatedBy(java.lang.String updatedBy,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_UPDATEDBY, updatedBy);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DRace> queryByUpdatedDate(java.util.Date updatedDate) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_UPDATEDDATE, updatedDate);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field updatedDate
	 * @param updatedDate the specified attribute
	 * @return an Iterable of keys to the DRaces with the specified attribute
	 */
	public final Iterable<java.lang.Long> queryKeysByUpdatedDate(java.util.Date updatedDate) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_UPDATEDDATE, updatedDate);
            return queryIterableKeys(0, -1, null, null, null, false, null, false, filter);
	}

	/**
	 * query-page-by method for field updatedDate
	 * @param updatedDate the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DRaces for the specified updatedDate
	 */
	public final CursorPage<DRace> queryPageByUpdatedDate(java.util.Date updatedDate,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_UPDATEDDATE, updatedDate);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	// ----------------------- one-to-one finders -------------------------

	// ----------------------- many-to-one finders -------------------------

	// ----------------------- many-to-many finders -------------------------

	// ----------------------- uniqueFields finders -------------------------

	// ----------------------- populate / persist method -------------------------

	/**
	 * Persist an entity given all attributes
	 */
	public DRace persist(		java.lang.Long id, 
		com.google.appengine.api.blobstore.BlobKey blobKey, 
		java.lang.String displayName, 
		java.lang.String extractorClassname, 
		java.lang.String imageUri, 
		java.lang.String queryUrl, 
		java.util.Date startDate, 
		java.lang.String timeZone) {

            DRace domain = null;
            // if primaryKey specified, use it
            if (null != id) {
                    domain = findByPrimaryKey(id);
            }
		
            // create new?
            if (null == domain) {
                    domain = new DRace();
                    // generate Id?
                    if (null != id) {
                            domain.setId(id);
                    }
                    // fields
                    domain.setBlobKey(blobKey);
                    domain.setDisplayName(displayName);
                    domain.setExtractorClassname(extractorClassname);
                    domain.setImageUri(imageUri);
                    domain.setQueryUrl(queryUrl);
                    domain.setStartDate(startDate);
                    domain.setTimeZone(timeZone);
                    // one-to-ones
                    // many-to-ones
			
                    persist(domain);
            }
            return domain;
	}



}
