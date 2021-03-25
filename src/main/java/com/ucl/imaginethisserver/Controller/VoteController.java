package com.ucl.imaginethisserver.Controller;

import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
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
        try {
            return voteService.getVotesForFeedback(projectID, feedbackID);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Not votes found for feedback", e
            );
        }
    }

    @PostMapping("/projects/{project-id}/feedback/{feedback-id}/vote")
    public ResponseEntity<Map<String, Object>> voteFeedback(@PathVariable("project-id") String projectID,
                                                             @PathVariable("feedback-id") UUID feedbackID,
                                                             @RequestBody Vote vote) {
        try {
            boolean result = voteService.voteFeedback(projectID, feedbackID, vote);
            Map<String, Object> response = new HashMap<>();
            response.put("success", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException | InternalServerErrorException e) {
            String errorMessage = (e instanceof IllegalArgumentException) ?
                    "Illegal argument" :
                    "Error voting Feedback";
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, e
            );
        }

    }

    @PatchMapping("/projects/{project-id}/feedback/{feedback-id}/vote/{vote-id}")
    public ResponseEntity<Map<String, Boolean>> updateVoteForFeedback(@PathVariable("project-id") String projectID,
                                                                      @PathVariable("feedback-id") UUID feedbackID,
                                                                      @PathVariable("vote-id") UUID voteID,
                                                                      @RequestBody Vote vote) {
        try {
            boolean result = voteService.updateVoteForFeedback(projectID, feedbackID, voteID, vote);
            Map<String, Boolean> response = new HashMap<>();
            response.put("success", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException | InternalServerErrorException e) {
            String errorMessage = (e instanceof IllegalArgumentException) ?
                    "Illegal argument" :
                    "Error updating vote";
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, e
            );
        }
    }

    @DeleteMapping("/projects/{project-id}/feedback/{feedback-id}/vote/{vote-id}")
    public ResponseEntity<Map<String, Boolean>> deleteVoteForFeedback(@PathVariable("project-id") String projectID,
                                                                      @PathVariable("feedback-id") UUID feedbackID,
                                                                      @PathVariable("vote-id") UUID voteID,
                                                                      @RequestBody Vote vote) {
        try {
            boolean result = voteService.deleteVoteForFeedback(projectID, feedbackID, voteID, vote);
            Map<String, Boolean> response = new HashMap<>();
            response.put("success", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InternalServerErrorException e) {
            String errorMessage = "Error deleting vote";
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, e
            );
        }
    }
}
