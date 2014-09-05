package com.wadpam.tracker.extractor;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.common.base.Strings;
import com.wadpam.tracker.api.AdminResource;
import com.wadpam.tracker.api.CreateSplitRequest;
import com.wadpam.tracker.dao.DParticipantDao;
import com.wadpam.tracker.dao.DRaceDao;
import com.wadpam.tracker.dao.DSplitDao;
import com.wadpam.tracker.domain.DParticipant;
import com.wadpam.tracker.domain.DRace;
import com.wadpam.tracker.domain.DSplit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @author osandstrom
 * Date: 1/24/14 Time: 7:02 PM
 */
public abstract class AbstractSplitsExtractor {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractSplitsExtractor.class);
    
    protected AdminResource adminResource;
    
    protected DParticipantDao participantDao;
    protected DRaceDao raceDao;
    protected DSplitDao splitDao;
    
    protected DRace race;
    
    private final MemcacheService memcacheService;

    public AbstractSplitsExtractor() {
        memcacheService = MemcacheServiceFactory.getMemcacheService();
    }
    
    public void process(DRace race) {
        // query and add participants:
        Iterable<DParticipant> r = participantDao.queryPending(race.getId());
        final ArrayList<DParticipant> registered = new ArrayList<DParticipant>();
        for (DParticipant participant : r) {
            if (!Strings.isNullOrEmpty(participant.getExtUserId())) {
                registered.add(participant);
            }
        }
        
        if (registered.isEmpty()) {
            return;
        }
        
        // query and map race splits
        final Object raceKey = raceDao.getPrimaryKey(race);
        final String splitsKey = "splits." + raceDao.getKeyString(raceKey);
        LOGGER.debug("Memcache splitsKey={}", splitsKey);
        
        TreeMap<Long, DSplit> raceSplits = (TreeMap<Long, DSplit>) memcacheService.get(splitsKey);
        if (null == raceSplits) {
            raceSplits = splitDao.mapByParentKey(raceKey);
            memcacheService.put(splitsKey, raceSplits, Expiration.byDeltaSeconds(3600));
        }
        
        Queue queue = QueueFactory.getDefaultQueue();
        // now, process all properly registered participants
        int i = 0;
        for (DParticipant participant : registered) {
            queue.add(TaskOptions.Builder
                    .withUrl("/_adm/participant/" + participant.getId())
                    .param("raceId", Long.toString(race.getId()))
                    .countdownMillis(3000*i));
            i++;
        }
    }
    
    public void processParticipant(Long participantId) {
        DParticipant participant = participantDao.findByPrimaryKey(participantId);
        LOGGER.debug("participant id={}, userId={}", participantId, participant.getUserId());

        final Object raceKey = raceDao.getPrimaryKey(null, participant.getRaceId());
        final String splitsKey = "splits." + raceDao.getKeyString(raceKey);
        TreeMap<Long, DSplit> raceSplits = (TreeMap<Long, DSplit>) memcacheService.get(splitsKey);
        try {
            final Map<DSplit,DSplit> splits = getPassedSplits(race, raceSplits, 
                    participant);
            if (null != splits && !splits.isEmpty()) {
                updateSplits(race, participantId, raceSplits, splits);
            }
        } catch (Exception ex) {
            LOGGER.warn("Getting splits for participant {}", participantId);
        }
    }

    public abstract Map<DSplit,DSplit> getPassedSplits(DRace race, 
            TreeMap<Long, DSplit> raceSplits, DParticipant participant) throws IOException;

    public abstract TreeMap<String,String> searchForParticipants(DRace race, String searchName, String firstName);
    
    private void updateSplits(DRace race, Long participantId, 
            TreeMap<Long, DSplit> raceSplits, Map<DSplit,DSplit> passedSplitsMap) {
        
        if (!passedSplitsMap.isEmpty()) {
            
            final Object participantKey = participantDao.getPrimaryKey(null, participantId);
            final TreeMap<Long, DSplit> persistedSplits = splitDao.mapByParentKey(participantKey);
            
            // any new passed splits?
            for (DSplit raceSplit : raceSplits.values()) {
                DSplit passedSplit = passedSplitsMap.get(raceSplit);
                if (!persistedSplits.containsKey(passedSplit.getTimestamp())) {

                    CreateSplitRequest body = new CreateSplitRequest();
                    body.setName(passedSplit.getName());
                    body.setElapsedSeconds(Long.toString(passedSplit.getTimestamp()));
                    body.setRaceSplitId(raceSplit.getId());
                    LOGGER.info("Creating participant split {}", passedSplit);
                    adminResource.createParticipantSplit(participantId, 
                            body);
                    return;
                }
                else {
                    LOGGER.debug("Split {} already persisted.", passedSplit);
                }
            }
        }
    }
    
    public void setParticipantDao(DParticipantDao participantDao) {
        this.participantDao = participantDao;
    }

    public void setRaceDao(DRaceDao raceDao) {
        this.raceDao = raceDao;
    }

    public void setSplitDao(DSplitDao splitDao) {
        this.splitDao = splitDao;
    }

    public void setAdminResource(AdminResource adminResource) {
        this.adminResource = adminResource;
    }

    public void setRace(DRace race) {
        this.race = race;
    }

}
