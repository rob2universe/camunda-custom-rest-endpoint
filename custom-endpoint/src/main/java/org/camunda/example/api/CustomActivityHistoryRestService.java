package org.camunda.example.api;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
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
  HistoryService historyService;

  @Autowired
  ManagementService mgmtService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getActivityInstanceHistoryByBusinessKey(@QueryParam("businessKey") String businessKey) {

    List<HistoricActivityInstance> aiList;
    aiList = historyService.createNativeHistoricActivityInstanceQuery()
        .sql("SELECT AI.* FROM "
            + mgmtService.getTableName(HistoricProcessInstance.class) + " PI,"
            + mgmtService.getTableName(HistoricActivityInstance.class) + " AI"
            + " WHERE AI.PROC_INST_ID_ = PI.ID_ AND PI.BUSINESS_KEY_ = #{businessKey} ORDER BY AI.END_TIME_ ")
        .parameter("businessKey", businessKey)
        .list();

    log.debug("\nGetting instances for businessKey {} returned {} historic activity instances:", businessKey, aiList.size());
    aiList.forEach(ai -> log.debug("ActvityId: {} PI: {}, AI: {}",
        ai.getActivityId(), ai.getProcessInstanceId(), ai.getParentActivityInstanceId()));
    return Response.ok().entity(aiList).build();
  }
}

