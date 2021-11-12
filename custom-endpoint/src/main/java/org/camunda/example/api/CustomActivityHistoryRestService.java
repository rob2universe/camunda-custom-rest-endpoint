package org.camunda.example.api;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Path("custom-activity-history")
public class CustomActivityHistoryRestService {

  @Autowired
  ProcessEngine engine;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getHistoryByBusinessKey(@QueryParam("businessKey") String businessKey) {

    log.info("Getting instances for businessKey: {}", businessKey);
    HistoryService historyService = engine.getHistoryService();
    var piList = historyService.createHistoricProcessInstanceQuery()
        .processInstanceBusinessKey(businessKey)
        .orderByProcessInstanceEndTime().asc()
        .list();
    log.info("Found {} process instances.", piList.size());
    List<HistoricActivityInstance> aiList = new ArrayList<>();
    piList.forEach(e -> {
      log.debug("Process instance {} of definition id {}", e.getId(), e.getProcessDefinitionId());
          aiList.addAll(
              historyService.createHistoricActivityInstanceQuery()
              .processInstanceId(e.getId())
              .list()
          );
        }
    );

    return Response.ok().entity(aiList).build();
  }
}

