package org.camunda.example.api;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Path("/testprocess")
public class CustomRestService {

  public static final String PD_KEY = "testprocess";
  @Autowired
  ProcessEngine engine;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getList() {

    var list = engine.getHistoryService().createHistoricProcessInstanceQuery().processDefinitionKey(PD_KEY).list();
    log.info("Found {} process instances.", list.size());
    List<String> pidList = new ArrayList<>();
    list.forEach(e -> pidList.add(e.getRootProcessInstanceId()));
    return Response.ok().entity(pidList).build();
  }

  @POST
  @Path("/start/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response startProcess() {
    var pi = engine.getRuntimeService().startProcessInstanceByKey(PD_KEY);
    return Response.status(201)
        .entity(ProcessInstanceDto.fromProcessInstance(pi)).build();
  }
}

