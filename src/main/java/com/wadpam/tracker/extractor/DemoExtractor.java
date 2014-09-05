/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wadpam.tracker.extractor;

import com.wadpam.tracker.domain.DParticipant;
import com.wadpam.tracker.domain.DRace;
import com.wadpam.tracker.domain.DSplit;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author osandstrom
 */
public class DemoExtractor extends AbstractSplitsExtractor {

    @Override
    public Map<DSplit, DSplit> getPassedSplits(DRace race, TreeMap<Long, DSplit> raceSplits, DParticipant participant) throws IOException {
        final long start = participant.getCreatedDate().getTime();
        final long millis = System.currentTimeMillis();
        
        // raceSplit -> participantSplit
        Map<DSplit, DSplit> passed = new HashMap<>();
        for (DSplit raceSplit : raceSplits.values()) {
            if (start + raceSplit.getTimestamp() <= millis) {
                final DSplit split = new DSplit();
                split.setDistance(raceSplit.getDistance());
                split.setElevation(raceSplit.getElevation());
                split.setName(raceSplit.getName());
                split.setTimestamp(start + raceSplit.getTimestamp());
                passed.put(raceSplit, split);
            }
        }
        
        return passed;
    }

    @Override
    public TreeMap<String, String> searchForParticipants(DRace race, String searchName, String firstName) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put(searchName + ", " + firstName, Long.toHexString(System.currentTimeMillis()));
        return map;
    }
    
}
