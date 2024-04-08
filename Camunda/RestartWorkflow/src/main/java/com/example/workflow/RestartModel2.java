package com.example.workflow;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/// Failed ///
// Tried to get activity ID of start event from history service providing current process instance Id.
// Doubt: we can get activity ID only for tasks
// Can save the Point of entry task ID and try but can cause problem in multidivergent workflow
@Component
public class RestartModel2 implements JavaDelegate {


    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;
    private final HistoryService historyService;

    @Autowired
    public RestartModel2(ProcessEngine processEngine) {
        this.runtimeService = processEngine.getRuntimeService();
        this.repositoryService = processEngine.getRepositoryService();
        this.historyService = processEngine.getHistoryService();
    }


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String processInstanceId = execution.getProcessInstanceId();

//        String id = "";
//        HistoricActivityInstance startEventInstance = historyService.createHistoricActivityInstanceQuery()
//                .processInstanceId(processInstanceId)
//                .activityType(id)
//                .singleResult();
//
//        String startingTaskId = null;
//        if (startEventInstance != null) {
//            startingTaskId = startEventInstance.getActivityId();
//        }
//
//        runtimeService.createProcessInstanceModification(processInstanceId)
//                .startBeforeActivity(startingTaskId)
//                .execute();

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().singleResult();
        runtimeService.deleteProcessInstance(processInstance.getId(), "any reason");
        runtimeService.restartProcessInstances(processInstance.getProcessDefinitionId())
                .startAfterActivity("Start")
                .processInstanceIds(processInstance.getId())
                .execute();





    }
}
