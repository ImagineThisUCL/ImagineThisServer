package com.ucl.imaginethisserver.Service;

import com.ucl.imaginethisserver.Model.Vote;

import java.util.List;
import java.util.UUID;

public interface VoteService {
    List<Vote> getVotesForFeedback(String projectID, UUID feedbackID);

    /**
     * This method will vote on an existing feedback for the given project
     * @param projectID ID of the project
     * @param feedbackID ID of the feedback
     * @param vote a vote object, can be either up vote or down vote
     * @return bool value which indicates the operation status
     */
    boolean voteFeedback(String projectID, UUID feedbackID, Vote vote);

    boolean updateVoteForFeedback(String projectID, UUID feedbackID, UUID voteID, Vote vote);

    boolean deleteVoteForFeedback(String projectID, UUID feedbackID, UUID voteID, Vote vote);
}
