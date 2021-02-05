package com.ucl.imaginethisserver.Controller;

import com.ucl.imaginethisserver.CustomExceptions.ProjectNotFoundException;
import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.FeedbackService;
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

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/projects/{project-id}/feedback")
    public ResponseEntity<List<Feedback>> getAllFeedbacks(@PathVariable("project-id") String projectID) {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks(projectID);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/projects/{project-id}/feedback/{feedback-id}")
    public ResponseEntity<Feedback> getFeedbackByID(@PathVariable("project-id") String projectID,
                                                    @PathVariable("feedback-id") UUID feedbackID) {
        Feedback feedback = feedbackService.getFeedbackByID(projectID, feedbackID);
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }

    @PostMapping("/projects/{project-id}/feedback")
    public ResponseEntity<Map<String, Boolean>> addNewFeedback(@PathVariable("project-id") String projectID,
                                                               @RequestBody Feedback feedback) {
        Boolean result = feedbackService.addNewFeedback(projectID, feedback);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/feedback/{feedback-id}/vote")
    public ResponseEntity<Map<String, Boolean>> voteFeedback(@PathVariable("feedback-id") UUID feedbackID,
                                                             @RequestBody Vote vote) {
        Boolean result = feedbackService.voteFeedback(feedbackID, vote);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/{user-id}/votes")
    @ResponseBody
    public List<Vote> getVotesByUserID(@PathVariable("user-id") UUID userID) {
        return feedbackService.getVotesByUserID(userID);
    }

//    @PostMapping("feedback/{feedback-id}/{vote-id}")
//    public ResponseEntity<Map<String, Boolean>> deleteFeedback(@PathVariable("feedback-id") UUID feedbackID,
//                                                               @PathVariable("vote-id") UUID voteID) {
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}
