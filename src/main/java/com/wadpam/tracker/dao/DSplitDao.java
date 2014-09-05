package com.wadpam.tracker.dao;

import com.wadpam.tracker.domain.DSplit;
import java.util.TreeMap;

/**
 * Business Methods interface for entity DSplit.
 * This interface is generated by mardao, but edited by developers.
 * It is not overwritten by the generator once it exists.
 *
 * Generated on 2014-02-11T19:20:04.353+0100.
 * @author mardao DAO generator (net.sf.mardao.plugin.ProcessDomainMojo)
 */
public interface DSplitDao extends GeneratedDSplitDao {
    
    static String NAME_START = "Start";
    static String NAME_FINISH = "Finish";
	
    TreeMap<Long, DSplit> mapByParentKey(Object raceOrParticipantKey);
    
}