package com.wadpam.tracker.config;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import com.wadpam.mardao.guice.MardaoGuiceModule;
import com.wadpam.mardao.oauth.web.OAuth2Filter;
import com.wadpam.tracker.api.AdminResource;
import com.wadpam.tracker.api.ParticipantResource;
import com.wadpam.tracker.api.PublicResource;
import com.wadpam.tracker.api.RaceResource;
import com.wadpam.tracker.api.SplitResource;
import com.wadpam.tracker.api.TrackerResource;
import com.wadpam.tracker.dao.DParticipantDao;
import com.wadpam.tracker.dao.DParticipantDaoBean;
import com.wadpam.tracker.dao.DRaceDao;
import com.wadpam.tracker.dao.DRaceDaoBean;
import com.wadpam.tracker.dao.DSplitDao;
import com.wadpam.tracker.dao.DSplitDaoBean;

/**
 * Created with IntelliJ IDEA.
 *
 * @author osandstrom
 * Date: 1/18/14 Time: 5:11 PM
 */
public class TrackerGuiceServletContextListener extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {

    return Guice.createInjector(
      new MardaoGuiceModule(),
      new JerseyServletModule() {

        private final void bindDaos() {
          bind(DParticipantDao.class).to(DParticipantDaoBean.class);
          bind(DRaceDao.class).to(DRaceDaoBean.class);
          bind(DSplitDao.class).to(DSplitDaoBean.class);
        }
        
        private final void bindResources() {
            bind(AdminResource.class);
            bind(ParticipantResource.class);
            bind(PublicResource.class);
            bind(RaceResource.class);
            bind(SplitResource.class);
            bind(TrackerResource.class);
        }

        @Override
        protected void configureServlets() {
          bindDaos();
          
          bindResources();

          filter("/*").through(PersistFilter.class);
          filter("/api/*").through(OAuth2Filter.class);

          serve("/*").with(GuiceContainer.class);
        }
      }
    );
  }
}
