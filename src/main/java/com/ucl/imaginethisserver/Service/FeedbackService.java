package com.ucl.imaginethisserver.Service;

import com.ucl.imaginethisserver.DAO.FeedbackDto;
import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Model.Vote;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface FeedbackService {

    /**
     * This method will return all feedbacks for a given project
     * @param projectID ID of the project
     * @return a list of feedbacks
     */
    List<Feedback> getAllFeedbacks(String projectID);

    List<FeedbackDto> getFeedbacksWithVotes(String projectID);

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
