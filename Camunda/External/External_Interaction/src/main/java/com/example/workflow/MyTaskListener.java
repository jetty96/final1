package com.example.workflow;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.Map;

// Things Tried
// User task listener in the create event in Task2 and set camunda:assignee = "${assignee} from there.
// Identifier not recognised. the process variable assignee should be initialised and set before the task is created.
//Even if process variable assignee is initialised before, it cannot be reset in create event in a task. Should be set before.


@Component
public class MyTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        String taskId = delegateTask.getId();
        System.out.println(taskId);

        // Using RestTemplate to make REST API call
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/assignUser?taskId=" + taskId;
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url, null, Map.class);

        // Response from the external service and setting up Assignee by populating expression at Runtime
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Map<String, String> responseBody = responseEntity.getBody();
            if (responseBody != null) {
                String userId = responseBody.get("userId");
                System.out.println(userId instanceof String);
                // Setting up Assignee
                delegateTask.getExecution().setVariable("assignee", userId);
                //delegateTask.setVariable("assignee", userId);
                System.out.println("User assigned: " + userId);
            } else {
                System.out.println("No response body received");
            }
        } else {
            System.out.println("Failed to get response from external service");
        }
    }

    //String response = restTemplate.postForObject(url, null, String.class);
    //System.out.println("Response from external service: " + response);
}


