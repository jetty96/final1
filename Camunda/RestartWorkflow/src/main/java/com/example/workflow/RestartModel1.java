package com.example.workflow;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestartModel1 implements JavaDelegate {


    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;

    @Autowired
    public RestartModel1(ProcessEngine processEngine) {
        this.runtimeService = processEngine.getRuntimeService();
        this.repositoryService = processEngine.getRepositoryService();
    }
// Get Id for current Process Instance and for the Process Definition
// To restart the workflow, create a new instance by starting the workflow again from begin
// Delete the old workflow, quoting the reason for Logging purpose

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        //String processInstanceId = execution.getProcessInstanceId();


//        String processDefinitionKey = ((ProcessDefinitionEntity) ((ExecutionEntity) execution).getProcessDefinition()).getKey();
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
//                .processDefinitionKey(processDefinitionKey)
//                .latestVersion()
//                .singleResult();
//        String processDefinitionId = processDefinition.getId();

        String processInstanceId = execution.getProcessInstanceId();
        String processDefinitionId = execution.getProcessDefinitionId();
        runtimeService.deleteProcessInstance(processInstanceId, "Deleted for restart");
        runtimeService.startProcessInstanceById(processDefinitionId);
    }
}
