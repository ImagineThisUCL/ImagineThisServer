package com.ucl.imaginethisserver.Controller;

import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1")
public class VoteController {

    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/projects/{project-id}/feedback/{feedback-id}/vote")
    public List<Vote> getVotesForFeedback(@PathVariable("project-id") String projectID,
                                          @PathVariable("feedback-id") UUID feedbackID) {
        return voteService.getVotesForFeedback(projectID, feedbackID);
    }

    @PostMapping("/projects/{project-id}/feedback/{feedback-id}/vote")
    public ResponseEntity<Map<String, Boolean>> voteFeedback(@PathVariable("project-id") String projectID,
                                                             @PathVariable("feedback-id") UUID feedbackID,
                                                             @RequestBody Vote vote) {
        Boolean result = voteService.voteFeedback(projectID, feedbackID, vote) != null;
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/projects/{project-id}/feedback/{feedback-id}/vote/{vote-id}")
    public ResponseEntity<Map<String, Boolean>> updateVoteForFeedback(@PathVariable("project-id") String projectID,
                                                                      @PathVariable("feedback-id") UUID feedbackID,
                                                                      @PathVariable("vote-id") UUID voteID,
                                                                      @RequestBody Vote vote) {
        boolean result = voteService.updateVoteForFeedback(projectID, feedbackID, voteID, vote);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/projects/{project-id}/feedback/{feedback-id}/vote/{vote-id}")
    public ResponseEntity<Map<String, Boolean>> deleteVoteForFeedback(@PathVariable("project-id") String projectID,
                                                                      @PathVariable("feedback-id") UUID feedbackID,
                                                                      @PathVariable("vote-id") UUID voteID,
                                                                      @RequestBody Vote vote) {
        boolean result = voteService.deleteVoteForFeedback(projectID, feedbackID, voteID, vote);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
