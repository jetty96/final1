package com.example.workflow.Listener;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskListen implements TaskListener {
    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;
    private final HistoryService historyService;
    private final TaskService taskService;

    @Autowired
    public TaskListen(ProcessEngine processEngine) {
        this.runtimeService = processEngine.getRuntimeService();
        this.repositoryService = processEngine.getRepositoryService();
        this.historyService= processEngine.getHistoryService();
        this.taskService = processEngine.getTaskService();
    }
    @Override
    public void notify(DelegateTask delegateTask) {

        // Process Instance ID of the running instance
        String processInstanceId = delegateTask.getProcessInstanceId();
        //CompleteTasks_NoOrder(processInstanceId);
        CompleteTasks_Order(processInstanceId);
        ActiveTasks(processInstanceId);

    }
    public void CompleteTasks_NoOrder(String ProcessInstanceId){
        // Retrieve completed tasks
        List<HistoricTaskInstance> completedTasks = historyService
                .createHistoricTaskInstanceQuery()
                .processInstanceId(ProcessInstanceId)
                .finished()
                .list();

        System.out.println("Completed Tasks:");
        for (HistoricTaskInstance task : completedTasks) {
            System.out.println(task.getName() + " - " + task.getEndTime());
        }

    }
    public void CompleteTasks_Order(String ProcessInstanceId){
        List<HistoricTaskInstance> completedTasks = historyService
                .createHistoricTaskInstanceQuery()
                .processInstanceId(ProcessInstanceId)
                .finished()
                .orderByHistoricTaskInstanceEndTime()
                .asc()
                .list();

        System.out.println("Completed Tasks:");
        for (HistoricTaskInstance task : completedTasks) {
            String assigneeName = task.getAssignee();
            System.out.println(task.getName() + " - Completed by: " + assigneeName + " at " + task.getEndTime());
        }
    }
    public void ActiveTasks(String ProcessInstanceId){
        // Retrieve active tasks
        List<Task> activeTasks = taskService
                .createTaskQuery()
                .processInstanceId(ProcessInstanceId)
                .active()
                .list();

        System.out.println("\nActive Tasks:");
        for (Task task : activeTasks) {
            System.out.println(task.getName() + " - " + task.getCreateTime());
        }
    }

}
