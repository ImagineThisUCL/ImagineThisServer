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

    /**
     * This method will return all feedbacks with upvotes and downvotes as additional fields for a given project
     * @param projectID ID of the project
     * @return a list of feedbackDto
     */
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

    /**
     * This method will update the feedback text with the given feedback ID
     * @param projectID ID of the project
     * @param feedbackID ID of the feedback
     * @param feedback feedback payload with new feedback text
     * @return bool value which indicates the operation status
     */
    boolean updateFeedback(String projectID, UUID feedbackID, Feedback feedback);

    /**
     * This method will delete the feedback record with the given feedback ID
     * @param projectID ID of the project
     * @param feedbackID ID of the feedback
     * @return bool value which indicates the operation status
     */
    boolean deleteFeedback(String projectID, UUID feedbackID);
}
