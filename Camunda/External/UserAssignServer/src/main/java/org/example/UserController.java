package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@RestController
public class UserController {

    public static void main(String[] args) {
        SpringApplication.run(UserController.class, args);
    }

    @PostMapping("/assignUser")
    public @ResponseBody Map<String, String> assignUser(@RequestParam String taskId) {
        //Logic to handle assigning user to the task
        String userId = getUserIdFromExternalService();
        System.out.println("User " + userId + " assigned to task " + taskId);
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("userId", userId);
        return jsonResponse;

        //return "{\"userId\":\"" + userId + "\"}";
        //return new UserAssignmentResponse(userId, taskId);
    }

    private String getUserIdFromExternalService() {
        return "UserName3";
    }
//
//    // simple POJO representing the response
//    static class UserAssignmentResponse {
//        private String userId;
//        private String taskId;
//
//        public UserAssignmentResponse(String userId, String taskId) {
//            this.userId = userId;
//            this.taskId = taskId;
//        }
//
//        public String getUserId() {
//            return userId;
//        }
//
//        public String getTaskId() {
//            return taskId;
//        }
//    }
}
