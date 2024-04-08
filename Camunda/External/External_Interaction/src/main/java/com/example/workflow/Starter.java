package com.example.workflow;

import jakarta.annotation.PostConstruct;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// Creating Users
// Starting Deployment
// Initializing some process variables
@Component
public class Starter {
    //camunda:assignee="${assignee}


    private final RepositoryService repositoryService;

    private final RuntimeService runtimeService;

    private final TaskService taskService;

    private final IdentityService identityService;

    @Autowired
    public Starter(ProcessEngine processEngine){
        this.runtimeService = processEngine.getRuntimeService();
        this.taskService = processEngine.getTaskService();
        this.identityService = processEngine.getIdentityService();
        this.repositoryService = processEngine.getRepositoryService();
    }

    @PostConstruct
    public void startWorkFlowTask(){
        System.out.println("WorkFlowStarted");
        userCreate();
        int number = 1;
        for(int i = 1; i <= number; i++) {
            String ProcessId = startWorkFlowTaskInstance();
            System.out.println(ProcessId);
        }
    }
    private String startWorkFlowTaskInstance(){

        Map<String, Object> variables = new HashMap<>();
        variables.put("starter", "UserName3");
        variables.put("assignee", "");

        Deployment deployment = repositoryService.createDeployment().addClasspathResource("External.bpmn").deploy();
        String processDefinitionKey = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult().getKey();
        String processId = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables).getId();
        System.out.println(processDefinitionKey);
        return processId;
    }
    public void userCreate(){
        System.out.println("Users");
        for(int i = 1; i<=5 ; i++){
            String userId = "UserName" + i;
            User existing = identityService.createUserQuery().userId(userId).singleResult();
            if (existing == null){
                User user = identityService.newUser(userId);
                user.setFirstName("John");
                user.setLastName("Doe" + i);
                user.setEmail("John.Doe" + i + "@example.com");
                user.setPassword("password123");
                identityService.saveUser(user);
            }
        }
    }
}
