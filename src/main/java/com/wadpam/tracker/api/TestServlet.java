/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wadpam.tracker.api;

import com.wadpam.tracker.dao.DRaceDao;
import com.wadpam.tracker.dao.DRaceDaoBean;
import com.wadpam.tracker.domain.DRace;

/**
 *
 * @author osandstrom
 */
public class TestServlet extends MardaoCrudServlet<DRace, Long, DRaceDao> {

    public TestServlet() {
        super(DRace.class, Long.class);
        setDao(new DRaceDaoBean());
    }
    
    
}
