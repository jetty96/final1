package com.example.workflow.Listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.task.Task;

public class NextTaskAssigneeListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        String processInstanceId = delegateTask.getProcessInstanceId();

        String assignee = calculateAssignee(delegateTask);

        Task nextTask = processEngine.getTaskService().createTaskQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        System.out.println(nextTask.getName());

        if (nextTask != null && assignee != null) {
            nextTask.setAssignee(assignee);
            processEngine.getTaskService().saveTask(nextTask);
            System.out.println("Assigned user '" + assignee + "' to the next user task (ID: " + nextTask.getId() + ")");
        } else {
            System.out.println("No next user task found or no assignee defined for the next user task.");
        }
    }

    private String calculateAssignee(DelegateTask delegateTask) {

        return "UserName3";
    }
}
