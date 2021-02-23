package com.ucl.imaginethisserver.Service;

import com.ucl.imaginethisserver.Model.Vote;

import java.util.List;
import java.util.UUID;

public interface VoteService {
    /**
     * This method will get all votes for the given feedback
     * @param projectID ID of the project
     * @param feedbackID ID of the feedback
     * @return a list of votes associated with that feedback
     */
    List<Vote> getVotesForFeedback(String projectID, UUID feedbackID);

    /**
     * This method will vote on an existing feedback for the given project
     * @param projectID ID of the project
     * @param feedbackID ID of the feedback
     * @param vote a vote object, can be either up vote or down vote
     * @return ID if the vote
     */
    UUID voteFeedback(String projectID, UUID feedbackID, Vote vote);

    /**
     * This method will update the vote detail
     * @param projectID ID of the project
     * @param feedbackID ID of the feedback
     * @param voteID ID of the vote
     * @param vote payload of vote which includes new vote value
     * @return bool value which indicates the operation status
     */
    boolean updateVoteForFeedback(String projectID, UUID feedbackID, UUID voteID, Vote vote);

    /**
     * This method will delete the vote with vote ID specified
     * @param projectID ID of the project
     * @param feedbackID ID of the feedback
     * @param voteID ID of the vote
     * @param vote payload of vote which includes new vote value
     * @return bool value which indicates the operation status
     */
    boolean deleteVoteForFeedback(String projectID, UUID feedbackID, UUID voteID, Vote vote);
}
