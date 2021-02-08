package com.ucl.imaginethisserver.DAO;

import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Model.TypeHandler.UUIDTypeHandler;
import com.ucl.imaginethisserver.Model.Vote;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface FeedbackDAO {
    /**
     * This method gets all feedbacks for a given project from DB
     * @param projectID ID of the project
     * @return a list of feedbacks
     */
    @Select("SELECT f.feedback_id, project_id, f.user_id,\n" +
            "SUM(case when v.vote >= 0 then v.vote end) upvotes,\n" +
            "SUM(case when v.vote < 0 then v.vote end) downvotes, user_name, f_timestamp, feedback_text\n" +
            "FROM feedbacks f\n" +
            "LEFT JOIN votes v on f.feedback_id = v.feedback_id\n" +
            "WHERE project_id = #{projectID}\n" +
            "GROUP BY f.feedback_id")
    @Results(id = "feedbackResultMap", value = {
            @Result(property = "feedbackID", column = "feedback_id", typeHandler = UUIDTypeHandler.class),
            @Result(property = "userID", column = "user_id", typeHandler = UUIDTypeHandler.class),
            @Result(property = "projectID", column = "project_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "timestamp", column = "f_timestamp"),
            @Result(property = "text", column = "feedback_text")
    })
    List<Feedback> getAllFeedbacks(@Param("projectID") String projectID);

    @Select("SELECT project_id FROM projects")
    @Result(property = "projectID", column = "project_id")
    List<String> getAllProjectID();

    /**
     * This method get a specific feedback for a given project from DB
     * @param projectID ID of the project
     * @param feedbackID ID of the feedback
     * @return the specified feedback
     */
    @Select("SELECT * FROM feedbacks WHERE project_id = #{projectID} AND feedback_id = #{feedbackID}")
    @ResultMap(value = "feedbackResultMap")
    Feedback getFeedbackByID(String projectID, UUID feedbackID);

    /**
     * This method adds a new feedback for the given project from DB
     * @param projectID ID of the project
     * @param feedback feedback object
     * @return bool value which indicates the operation status
     */
    @Insert("INSERT INTO feedbacks" +
            "(feedback_id, project_id, user_id, user_name, feedback_text, f_timestamp)\n" +
            "VALUES (#{feedback.feedbackID}, #{projectID}, " +
            "#{feedback.userID}, #{feedback.userName}, #{feedback.text}, #{feedback.timestamp})")
    boolean addNewFeedback(String projectID, Feedback feedback);

    /**
     * This method will vote on an existing feedback for the given project and update to DB
     * @param feedbackID ID of the feedback
     * @param vote a vote object, can be either up vote or down vote
     * @return bool value which indicates the operation status
     */
    @Insert("INSERT INTO votes(vote_id, feedback_id, user_id, vote, v_timestamp)\n" +
            "VALUES (#{vote.voteID}, #{feedbackID}, #{vote.userID}, #{vote.vote}, #{vote.timestamp})")
    boolean addVoteByID(UUID feedbackID, Vote vote);


    /**
     * This method get vote for given feedback and user
     * @param feedbackID ID of the feedback
     * @param userID ID of the user
     * @return the specified vote
     */
    @Select("SELECT * FROM votes WHERE feedback_id = #{feedbackID} AND user_id = #{userID}")
    @Results(id = "voteResultMap", value = {
            @Result(property = "voteID", column = "vote_id", typeHandler = UUIDTypeHandler.class),
            @Result(property = "feedbackID", column = "feedback_id", typeHandler = UUIDTypeHandler.class),
            @Result(property = "userID", column = "user_id", typeHandler = UUIDTypeHandler.class),
            @Result(property = "vote", column = "vote"),
            @Result(property = "timestamp", column = "v_timestamp")
    })
    Vote getVoteByFeedbackandUser(UUID feedbackID, UUID userID);

    @Select("SELECT * FROM votes WHERE user_id = #{userID}")
    @ResultMap(value = "voteResultMap")
    List<Vote> getVotesByUserID(@Param(value = "userID") UUID userID);

    /**
     * This method deletes vote for a given project, feedback and user
     * @param voteID of the vote
     * @return bool value which indicates the operation status
     */
    @Delete("DELETE FROM votes WHERE vote_id = #{voteID}")
    boolean deleteVoteByID(@Param(value = "voteID") UUID voteID);

    /**
     * This method updates vote for a feedback and value
     * @param voteID of the vote
     * @param value of the vote
     * @return bool value which indicates the operation status
     */
    @Update("UPDATE votes SET vote = #{value} WHERE vote_id = #{voteID}")
    boolean updateVoteByID(@Param(value = "voteID") UUID voteID, @Param(value = "value") int value);

}
