package com.adop.teamgantt.services;

import com.adop.teamgantt.controllers.TasksController;
import com.adop.teamgantt.models.CreateTasksPost;
import com.adop.teamgantt.models.TasksBody;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class TasksService {
    private final RestTemplate restTemplate;
    private final Gson gson;

    @Autowired
    TasksService(final RestTemplate restTemplate,
                 final Gson gson) {
        this.restTemplate = restTemplate;
        this.gson = gson;
    }

    public HttpHeaders CreateHeaders(TasksController.PostRequest request) {
        final HttpHeaders httpRequestHeaderList = new HttpHeaders();
        httpRequestHeaderList.setContentType(MediaType.APPLICATION_JSON);
        httpRequestHeaderList.set("accept", "");
        httpRequestHeaderList.set("TG-Authorization", request.getTGAuthorization());
        httpRequestHeaderList.set("TG-Api-Key", request.getTGApiKey());
        httpRequestHeaderList.set("TG-User-Token", request.getTGUserToken());
        return httpRequestHeaderList;
    }

    public void post(TasksController.PostRequest request) {
        final HttpHeaders httpRequestHeaderList = this.CreateHeaders(request);
        final HttpEntity<String> httpRequest = new HttpEntity<>(
                gson.toJson(new CreateTasksPost(
                        "user",
                        12573874
                )),
                httpRequestHeaderList);
        restTemplate.postForObject("https://api.teamgantt.com/v1/tasks/93164308/resources", httpRequest, CreateTasksPost.class);
    }

    public void getTasks(TasksController.PostRequest request) {
        final HttpHeaders httpRequestHeaderList = this.CreateHeaders(request);
        final HttpEntity<String> httpRequest = new HttpEntity<>(httpRequestHeaderList);
        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.teamgantt.com/v1/tasks", HttpMethod.GET, httpRequest, String.class);

        final Type collectionType = new TypeToken<List<TasksBody>>() {
        }.getType();
        final List<TasksBody> tasks = new Gson().fromJson(response.getBody(), collectionType);

        System.out.println("");
    }
}
