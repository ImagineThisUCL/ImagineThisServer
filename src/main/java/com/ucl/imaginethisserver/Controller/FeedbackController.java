package com.ucl.imaginethisserver.Controller;

import com.ucl.imaginethisserver.CustomExceptions.ProjectNotFoundException;
import com.ucl.imaginethisserver.DAO.FeedbackDto;
import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.FeedbackService;
import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1")
public class FeedbackController {
    private final FeedbackService feedbackService;

    Logger logger = LoggerFactory.getLogger(FeedbackController.class);
    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/projects/{project-id}/feedback")
    @ResponseBody
    public List<FeedbackDto> getFeedbacksWithVotes(@PathVariable("project-id") String projectID) {
        List<FeedbackDto> feedbacks = feedbackService.getFeedbacksWithVotes(projectID);
        return feedbacks;
    }

    @GetMapping("/projects/{project-id}/feedback/{feedback-id}")
    @ResponseBody
    public Feedback getFeedbackByID(@PathVariable("project-id") String projectID,
                                                    @PathVariable("feedback-id") UUID feedbackID) {
        Feedback feedback = feedbackService.getFeedbackByID(projectID, feedbackID);
        return feedback;
    }

    @PostMapping("/projects/{project-id}/feedback")
    public ResponseEntity<Map<String, Boolean>> addNewFeedback(@PathVariable("project-id") String projectID,
                                                               @RequestBody Feedback feedback) {
        Boolean result = feedbackService.addNewFeedback(projectID, feedback);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
