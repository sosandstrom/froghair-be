package com.wadpam.froghair.config;


import com.google.common.collect.ImmutableMap;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import com.wadpam.froghair.api.NearbyResource;
import com.wadpam.mardao.guice.MardaoGuiceModule;
import com.wadpam.mardao.oauth.web.OAuth2Filter;

import net.sf.mardao.dao.DatastoreSupplier;
import net.sf.mardao.dao.Supplier;

/**
 * Guice me up.
 *
 * @author osandstrom
 * Date: 1/18/14 Time: 5:11 PM
 */
public class FroghairGuiceServletContextListener extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {

    return Guice.createInjector(
      new MardaoGuiceModule(),
      new JerseyServletModule() {

        private final void bindDaos() {
          bind(Supplier.class).to(DatastoreSupplier.class);
        }
        
        private final void bindResources() {
          bind(NearbyResource.class);
        }

        @Override
        protected void configureServlets() {
          bindDaos();
          
          bindResources();

          filter("/*").through(PersistFilter.class);
          filter("/api/*").through(OAuth2Filter.class);

          serve("/*").with(GuiceContainer.class, ImmutableMap.of(
            "jersey.config.server.tracing.type", "ALL"
          ));
        }
      }
    );
  }
}
