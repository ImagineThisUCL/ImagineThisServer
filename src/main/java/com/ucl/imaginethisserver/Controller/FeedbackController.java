package com.ucl.imaginethisserver.Controller;

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
    @ResponseBody
    public List<Feedback> getAllFeedbacks(@PathVariable("project-id") String projectID) {
        return feedbackService.getAllFeedbacks(projectID);
    }

    @GetMapping("/projects/{project-id}/feedback/{feedback-id}")
    @ResponseBody
    public Feedback getFeedbackByID(@PathVariable("project-id") String projectID,
                                    @PathVariable("feedback-id") UUID feedbackID) {
        return feedbackService.getFeedbackByID(projectID, feedbackID);
    }

    @PostMapping("/projects/{project-id}/feedback")
    public ResponseEntity<Map<String, Boolean>> addNewFeedback(@PathVariable("project-id") String projectID,
                                                               @RequestBody Feedback feedback) {
        Boolean result = feedbackService.addNewFeedback(projectID, feedback);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/feedbacks/{feedback-id}/vote")
    public ResponseEntity<Map<String, Object>> voteFeedback(@PathVariable("feedback-id") UUID feedbackID,
                                                             @RequestBody Vote vote) {
        UUID result = feedbackService.voteFeedback(feedbackID, vote);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("vote-id", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/vote/{vote-id}")
    public ResponseEntity<Map<String, Boolean>> DeleteVote(@PathVariable("vote-id") UUID voteID) {
        Boolean result = feedbackService.deleteVote(voteID);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PutMapping("/vote/{vote-id}/value/{value}")
//    public ResponseEntity<Map<String, Boolean>> UpdateVote(@PathVariable("vote-id") UUID voteID,
//                                                           @PathVariable("value") int value) {
//        Boolean result = feedbackService.updateVote(voteID, value);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("success", result);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping("/feedback/{feedback-id}/votes")
    @ResponseBody
    public List<Vote> getVotesByID(@PathVariable("feedback-id") UUID feedbackID) {
        return feedbackService.getVotesByID(feedbackID);
    }
}
