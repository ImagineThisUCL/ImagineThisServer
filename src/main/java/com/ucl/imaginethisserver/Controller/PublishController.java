package com.ucl.imaginethisserver.Controller;

import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Service.PublishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class PublishController {

    private PublishService publishService;
    private Logger logger = LoggerFactory.getLogger(PublishController.class);

    @Autowired
    public PublishController(PublishService publishService) {
        this.publishService = publishService;
    }

    @PostMapping("/projects/{project-id}/publish")
    public ResponseEntity<Map<String, Boolean>> publishProject(@PathVariable("project-id") String projectID) {

        boolean result = publishService.publish(projectID);

        Map<String, Boolean> response = new HashMap<>();
        response.put("success", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
