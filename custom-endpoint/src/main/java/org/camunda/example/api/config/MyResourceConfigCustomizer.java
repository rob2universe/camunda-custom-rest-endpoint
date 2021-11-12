package org.camunda.example.api.config;

import org.camunda.example.api.CustomActivityHistoryRestService;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.autoconfigure.jersey.ResourceConfigCustomizer;
import org.springframework.stereotype.Component;

@Component
public class MyResourceConfigCustomizer implements ResourceConfigCustomizer {

  @Override
  public void customize(ResourceConfig config) {
    config.register(CustomActivityHistoryRestService.class);
  }
}
