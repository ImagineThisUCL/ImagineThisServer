package com.ucl.imaginethisserver.DAO;

import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Model.Vote;

import java.util.List;
import java.util.UUID;

public interface FeedbackDAO {
    /**
     * This method gets all feedbacks for a given project from DB
     * @param projectID ID of the project
     * @return a list of feedbacks
     */
    List<Feedback> getAllFeedbacks(String projectID);

    /**
     * This method get a specific feedback for a given project from DB
     * @param projectID ID of the project
     * @param feedbackID ID of the feedback
     * @return the specified feedback
     */
    Feedback getFeedbackByID(String projectID, UUID feedbackID);

    /**
     * This method adds a new feedback for the given project from DB
     * @param projectID ID of the project
     * @param feedback feedback object
     * @return bool value which indicates the operation status
     */
    boolean addNewFeedback(String projectID, Feedback feedback);

    /**
     * This method will vote on an existing feedback for the given project and update to DB
     * @param projectID ID of the project
     * @param feedbackID ID of the feedback
     * @param vote a vote object, can be either up vote or down vote
     * @return bool value which indicates the operation status
     */
    boolean voteFeedback(String projectID, UUID feedbackID, Vote vote);
}
