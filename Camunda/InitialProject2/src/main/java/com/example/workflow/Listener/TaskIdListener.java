package com.example.workflow.Listener;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskIdListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        String processInstanceId = execution.getProcessInstanceId();
        Task task = processEngine.getTaskService().createTaskQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        String taskId = task.getId();
        System.out.println("taskId");
        System.out.println(taskId);
        execution.setVariable("taskId", taskId);
    }
}
