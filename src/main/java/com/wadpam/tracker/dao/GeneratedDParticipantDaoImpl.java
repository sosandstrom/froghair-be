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
import com.wadpam.tracker.domain.DParticipant;

/**
 * The DParticipant domain-object specific finders and methods go in this POJO.
 * 
 * Generated on 2014-04-18T20:33:18.209+0200.
 * @author mardao DAO generator (net.sf.mardao.plugin.ProcessDomainMojo)
 */
public class GeneratedDParticipantDaoImpl extends TypeDaoImpl<DParticipant, java.lang.Long> 
	implements GeneratedDParticipantDao {


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
   public GeneratedDParticipantDaoImpl() {
      super(DParticipant.class, java.lang.Long.class);
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
    protected Object getDomainProperty(DParticipant domain, String name) {
        Object value;
        // simple key?
        if (COLUMN_NAME_ID.equals(name)) {
            value = domain.getId();
        }
        // fields
        else if (COLUMN_NAME_ACTIONID.equals(name)) {
            value = domain.getActionId();
        }
        else if (COLUMN_NAME_CREATEDBY.equals(name)) {
            value = domain.getCreatedBy();
        }
        else if (COLUMN_NAME_CREATEDDATE.equals(name)) {
            value = domain.getCreatedDate();
        }
        else if (COLUMN_NAME_EXTUSERID.equals(name)) {
            value = domain.getExtUserId();
        }
        else if (COLUMN_NAME_RACEID.equals(name)) {
            value = domain.getRaceId();
        }
        else if (COLUMN_NAME_STATUS.equals(name)) {
            value = domain.getStatus();
        }
        else if (COLUMN_NAME_UPDATEDBY.equals(name)) {
            value = domain.getUpdatedBy();
        }
        else if (COLUMN_NAME_UPDATEDDATE.equals(name)) {
            value = domain.getUpdatedDate();
        }
        else if (COLUMN_NAME_USERID.equals(name)) {
            value = domain.getUserId();
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
        else if (COLUMN_NAME_ACTIONID.equals(name)) {
            clazz = java.lang.String.class;
        }
        else if (COLUMN_NAME_CREATEDBY.equals(name)) {
            clazz = java.lang.String.class;
        }
        else if (COLUMN_NAME_CREATEDDATE.equals(name)) {
            clazz = java.util.Date.class;
        }
        else if (COLUMN_NAME_EXTUSERID.equals(name)) {
            clazz = java.lang.String.class;
        }
        else if (COLUMN_NAME_RACEID.equals(name)) {
            clazz = java.lang.Long.class;
        }
        else if (COLUMN_NAME_STATUS.equals(name)) {
            clazz = java.lang.Integer.class;
        }
        else if (COLUMN_NAME_UPDATEDBY.equals(name)) {
            clazz = java.lang.String.class;
        }
        else if (COLUMN_NAME_UPDATEDDATE.equals(name)) {
            clazz = java.util.Date.class;
        }
        else if (COLUMN_NAME_USERID.equals(name)) {
            clazz = java.lang.Long.class;
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
    protected void setDomainProperty(final DParticipant domain, final String name, final Object value) {
        // simple key?
        if (COLUMN_NAME_ID.equals(name)) {
            domain.setId((java.lang.Long) value);
        }
        // fields
        else if (COLUMN_NAME_ACTIONID.equals(name)) {
            domain.setActionId((java.lang.String) value);
        }
        else if (COLUMN_NAME_CREATEDBY.equals(name)) {
            domain.setCreatedBy((java.lang.String) value);
        }
        else if (COLUMN_NAME_CREATEDDATE.equals(name)) {
            domain.setCreatedDate((java.util.Date) value);
        }
        else if (COLUMN_NAME_EXTUSERID.equals(name)) {
            domain.setExtUserId((java.lang.String) value);
        }
        else if (COLUMN_NAME_RACEID.equals(name)) {
            domain.setRaceId((java.lang.Long) value);
        }
        else if (COLUMN_NAME_STATUS.equals(name)) {
            domain.setStatus((java.lang.Integer) value);
        }
        else if (COLUMN_NAME_UPDATEDBY.equals(name)) {
            domain.setUpdatedBy((java.lang.String) value);
        }
        else if (COLUMN_NAME_UPDATEDDATE.equals(name)) {
            domain.setUpdatedDate((java.util.Date) value);
        }
        else if (COLUMN_NAME_USERID.equals(name)) {
            domain.setUserId((java.lang.Long) value);
        }
        // one-to-ones
        // many-to-ones
        // many-to-manys
        else {
            super.setDomainProperty(domain, name, value);
        }
    }

    @Override
    protected void setDomainStringProperty(final DParticipant domain, final String name, final Map<String, String> properties) {
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

        // DParticipant has no parent

        /**
         * @return the simple key for specified DParticipant domain object
         */
        public Long getSimpleKey(DParticipant domain) {
            if (null == domain) {
                return null;
            }
            return domain.getId();
        }

        /**
         * @return the simple key for specified DParticipant domain object
         */
        public void setSimpleKey(DParticipant domain, Long id) {
            domain.setId(id);
        }

        public String getCreatedByColumnName() {
            return COLUMN_NAME_CREATEDBY;
        }

        public String getCreatedBy(DParticipant domain) {
            if (null == domain) {
                return null;
            }
            return domain.getCreatedBy();
        }

        public void _setCreatedBy(DParticipant domain, String creator) {
            domain.setCreatedBy(creator);
        }

        public String getUpdatedByColumnName() {
            return COLUMN_NAME_UPDATEDBY;
        }

        public String getUpdatedBy(DParticipant domain) {
            if (null == domain) {
                return null;
            }
            return domain.getUpdatedBy();
        }

        public void _setUpdatedBy(DParticipant domain, String updator) {
            domain.setUpdatedBy(updator);
        }

        public String getCreatedDateColumnName() {
            return COLUMN_NAME_CREATEDDATE;
        }

        public Date getCreatedDate(DParticipant domain) {
            if (null == domain) {
                return null;
            }
            return domain.getCreatedDate();
        }

        public void _setCreatedDate(DParticipant domain, Date date) {
            domain.setCreatedDate(date);
        }

        public String getUpdatedDateColumnName() {
            return COLUMN_NAME_UPDATEDDATE;
        }

        public Date getUpdatedDate(DParticipant domain) {
            if (null == domain) {
                return null;
            }
            return domain.getUpdatedDate();
        }

        public void _setUpdatedDate(DParticipant domain, Date date) {
            domain.setUpdatedDate(date);
        }

	// ----------------------- field finders -------------------------------
	/**
	 * find-by method for unique attribute field actionId
	 * @param actionId the unique attribute
	 * @return the unique DParticipant for the specified attribute
	 */
	public final DParticipant findByActionId(java.lang.String actionId) {
                Filter filter = createEqualsFilter(COLUMN_NAME_ACTIONID, actionId);
		return findUniqueBy(filter);
	}

	/**
	 * find-key-by method for unique attribute field actionId
	 * @param actionId the unique attribute
	 * @return the unique DParticipant for the specified attribute
	 */
	public final java.lang.Long findKeyByActionId(java.lang.String actionId) {
                Filter filter = createEqualsFilter(COLUMN_NAME_ACTIONID, actionId);
		return findUniqueKeyBy(filter);
	}
	 
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DParticipant> queryByCreatedBy(java.lang.String createdBy) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_CREATEDBY, createdBy);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field createdBy
	 * @param createdBy the specified attribute
	 * @return an Iterable of keys to the DParticipants with the specified attribute
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
	 * @return a Page of DParticipants for the specified createdBy
	 */
	public final CursorPage<DParticipant> queryPageByCreatedBy(java.lang.String createdBy,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_CREATEDBY, createdBy);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DParticipant> queryByCreatedDate(java.util.Date createdDate) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_CREATEDDATE, createdDate);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field createdDate
	 * @param createdDate the specified attribute
	 * @return an Iterable of keys to the DParticipants with the specified attribute
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
	 * @return a Page of DParticipants for the specified createdDate
	 */
	public final CursorPage<DParticipant> queryPageByCreatedDate(java.util.Date createdDate,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_CREATEDDATE, createdDate);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DParticipant> queryByExtUserId(java.lang.String extUserId) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_EXTUSERID, extUserId);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field extUserId
	 * @param extUserId the specified attribute
	 * @return an Iterable of keys to the DParticipants with the specified attribute
	 */
	public final Iterable<java.lang.Long> queryKeysByExtUserId(java.lang.String extUserId) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_EXTUSERID, extUserId);
            return queryIterableKeys(0, -1, null, null, null, false, null, false, filter);
	}

	/**
	 * query-page-by method for field extUserId
	 * @param extUserId the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DParticipants for the specified extUserId
	 */
	public final CursorPage<DParticipant> queryPageByExtUserId(java.lang.String extUserId,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_EXTUSERID, extUserId);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DParticipant> queryByRaceId(java.lang.Long raceId) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_RACEID, raceId);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field raceId
	 * @param raceId the specified attribute
	 * @return an Iterable of keys to the DParticipants with the specified attribute
	 */
	public final Iterable<java.lang.Long> queryKeysByRaceId(java.lang.Long raceId) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_RACEID, raceId);
            return queryIterableKeys(0, -1, null, null, null, false, null, false, filter);
	}

	/**
	 * query-page-by method for field raceId
	 * @param raceId the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DParticipants for the specified raceId
	 */
	public final CursorPage<DParticipant> queryPageByRaceId(java.lang.Long raceId,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_RACEID, raceId);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DParticipant> queryByStatus(java.lang.Integer status) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_STATUS, status);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field status
	 * @param status the specified attribute
	 * @return an Iterable of keys to the DParticipants with the specified attribute
	 */
	public final Iterable<java.lang.Long> queryKeysByStatus(java.lang.Integer status) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_STATUS, status);
            return queryIterableKeys(0, -1, null, null, null, false, null, false, filter);
	}

	/**
	 * query-page-by method for field status
	 * @param status the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DParticipants for the specified status
	 */
	public final CursorPage<DParticipant> queryPageByStatus(java.lang.Integer status,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_STATUS, status);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DParticipant> queryByUpdatedBy(java.lang.String updatedBy) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_UPDATEDBY, updatedBy);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field updatedBy
	 * @param updatedBy the specified attribute
	 * @return an Iterable of keys to the DParticipants with the specified attribute
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
	 * @return a Page of DParticipants for the specified updatedBy
	 */
	public final CursorPage<DParticipant> queryPageByUpdatedBy(java.lang.String updatedBy,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_UPDATEDBY, updatedBy);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DParticipant> queryByUpdatedDate(java.util.Date updatedDate) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_UPDATEDDATE, updatedDate);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field updatedDate
	 * @param updatedDate the specified attribute
	 * @return an Iterable of keys to the DParticipants with the specified attribute
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
	 * @return a Page of DParticipants for the specified updatedDate
	 */
	public final CursorPage<DParticipant> queryPageByUpdatedDate(java.util.Date updatedDate,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_UPDATEDDATE, updatedDate);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	/**
         * {@inheritDoc}
	 */
	public final Iterable<DParticipant> queryByUserId(java.lang.Long userId) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_USERID, userId);
            return queryIterable(false, 0, -1, null, null, null, false, null, false, filter);
	}
	
	/**
	 * query-key-by method for attribute field userId
	 * @param userId the specified attribute
	 * @return an Iterable of keys to the DParticipants with the specified attribute
	 */
	public final Iterable<java.lang.Long> queryKeysByUserId(java.lang.Long userId) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_USERID, userId);
            return queryIterableKeys(0, -1, null, null, null, false, null, false, filter);
	}

	/**
	 * query-page-by method for field userId
	 * @param userId the specified attribute
         * @param pageSize the number of domain entities in the page
         * @param cursorString non-null if get next page
	 * @return a Page of DParticipants for the specified userId
	 */
	public final CursorPage<DParticipant> queryPageByUserId(java.lang.Long userId,
            int pageSize, String cursorString) {
            final Filter filter = createEqualsFilter(COLUMN_NAME_USERID, userId);
            return queryPage(false, pageSize, null, null, null, false, null, false, cursorString, filter);
        }

	 
	// ----------------------- one-to-one finders -------------------------

	// ----------------------- many-to-one finders -------------------------

	// ----------------------- many-to-many finders -------------------------

	// ----------------------- uniqueFields finders -------------------------
	/**
	 * find-by method for unique attributes
	 * @param raceId the specified raceId
	 * @param userId the specified userId
	 * @return the unique DParticipant for the specified fields
	 */
	public final DParticipant findByRaceIdUserId(java.lang.Long raceId, java.lang.Long userId) {
		final Filter[] filters = new Filter[2];
                int i = 0;
                filters[i++] = createEqualsFilter(COLUMN_NAME_RACEID, raceId);
                filters[i++] = createEqualsFilter(COLUMN_NAME_USERID, userId);
		return findUniqueBy(filters);
	}

	// ----------------------- populate / persist method -------------------------

	/**
	 * Persist an entity given all attributes
	 */
	public DParticipant persist(		java.lang.Long id, 
		java.lang.String actionId, 
		java.lang.String extUserId, 
		java.lang.Long raceId, 
		java.lang.Integer status, 
		java.lang.Long userId) {

            DParticipant domain = null;
            // if primaryKey specified, use it
            if (null != id) {
                    domain = findByPrimaryKey(id);
            }
		
            // create new?
            if (null == domain) {
                    domain = new DParticipant();
                    // generate Id?
                    if (null != id) {
                            domain.setId(id);
                    }
                    // fields
                    domain.setActionId(actionId);
                    domain.setExtUserId(extUserId);
                    domain.setRaceId(raceId);
                    domain.setStatus(status);
                    domain.setUserId(userId);
                    // one-to-ones
                    // many-to-ones
			
                    persist(domain);
            }
            return domain;
	}


	/**
	 * Persists an entity unless it already exists
	 */
	public DParticipant persist(java.lang.String actionId, 
                java.lang.String extUserId, 
                java.lang.Long raceId, 
                java.lang.Integer status, 
                java.lang.Long userId) {
            DParticipant domain = findByActionId(actionId);
            if (null == domain) {
                domain = new DParticipant();
                domain.setActionId(actionId);
                domain.setExtUserId(extUserId);
                domain.setRaceId(raceId);
                domain.setStatus(status);
                domain.setUserId(userId);
                persist(domain);
            }
            return domain;
	}


}
