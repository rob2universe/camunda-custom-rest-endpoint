package org.camunda.example.api;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;

import java.util.Collections;
import java.util.Date;

@Slf4j
@EnableProcessApplication
@SpringBootApplication
public class Application {

  @Autowired
  private RuntimeService runtimeService;

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);
  }

  @EventListener
  public void processPostDeploy(PostDeployEvent event) {

    try {

      String BK = "BK" + System.currentTimeMillis();
      runtimeService.startProcessInstanceByKey("TestAProcess", BK);
      runtimeService.startProcessInstanceByKey("TestBProcess", BK);
      log.info("Started TestAProcess and TestBProcess with business key:" + BK);

      String BK2 = "BK" + System.currentTimeMillis();
      runtimeService.startProcessInstanceByKey("TestAProcess", BK2);
      runtimeService.startProcessInstanceByKey("TestBProcess", BK2);
      log.info("Started TestAProcess and TestBProcess with business key:" + BK2);
    }
    catch (Exception e)
    {
      log.info(e.getMessage());
    }

  }

}