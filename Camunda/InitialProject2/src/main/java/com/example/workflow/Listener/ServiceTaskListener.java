package com.example.workflow.Listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class ServiceTaskListener implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String taskId = (String) execution.getVariable("taskId");
        System.out.println("In service Task");
        System.out.println(taskId);
        execution.getProcessEngineServices().getTaskService().complete(taskId);
    }
}
