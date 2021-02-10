package com.ucl.imaginethisserver.DAO;

import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.TypeHandler.UUIDTypeHandler;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

@Repository
public interface FeedbackDao {
    /**
     * This method will return all feedbacks with upvotes and downvotes for a given project
     * @param projectID ID of the project
     * @return a list of feedbacksDto
     */
    List<FeedbackDto> getAllFeedbacksWithVotes(String projectID);

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
     * @return int value which indicates the database operation status
     */
    int addNewFeedback(String projectID, Feedback feedback);

    /**
     * This method will update the feedback text with the given feedback ID
     * @param projectID ID of the project
     * @param feedbackID ID of the feedback
     * @param feedback feedback object which includes new value
     * @return int value which indicates the database operation status
     */
    int updateFeedback(String projectID, UUID feedbackID, Feedback feedback);

    /**
     * This method will delete the feedback record with the given feedback ID
     * @param projectID ID of the project
     * @param feedbackID ID of the feedback
     * @return int value which indicates the database operation status
     */
    int deleteFeedback(String projectID, UUID feedbackID);
}


