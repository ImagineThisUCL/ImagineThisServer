package com.ucl.imaginethisserver.Service;

import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Model.Vote;

import java.util.List;
import java.util.UUID;

public interface FeedbackService {

    /**
     * This method will return all feedbacks for a given project
     * @param projectID ID of the project
     * @return a list of feedbacks
     */
    List<Feedback> getAllFeedbacks(String projectID);

    /**
     * This method get a specific feedback for a given project
     * @param projectID ID of the project
     * @param feedbackID ID of the feedback
     * @return the specified feedback
     */
    Feedback getFeedbackByID(String projectID, UUID feedbackID);

    /**
     * This method adds a new feedback for the given project
     * @param projectID ID of the project
     * @param feedback feedback object
     * @return bool value which indicates the operation status
     */
    boolean addNewFeedback(String projectID, Feedback feedback);


    /**
     * This method will add/update/remove vote on an existing feedback for the given project
     * @param feedbackID ID of the feedback
     * @param vote a vote object, can be either up vote or down vote
     * @return UUID of the newly generated vote
     */
    UUID voteFeedback(UUID feedbackID, Vote vote);

    /**
     * This method will query all votes that the user votes on
     * @param userID ID of the user
     * @return A list contains all votes that the given user made
     */
    List<Vote> getVotesByUserID(UUID userID);

    boolean deleteVote(UUID voteID);
}
