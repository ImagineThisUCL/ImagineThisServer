package com.ucl.imaginethisserver.DAO;

import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.TypeHandler.UUIDTypeHandler;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FeedbackDao {
    @Select("SELECT f.feedback_id, project_id, f.user_id,\n" +
            "COUNT(case when v.vote > 0 then v.vote end) upvotes,\n" +
            "COUNT(case when v.vote < 0 then v.vote end) downvotes, user_name, f_timestamp, feedback_text\n" +
            "FROM feedbacks f\n" +
            "LEFT JOIN votes v on f.feedback_id = v.feedback_id\n" +
            "WHERE project_id = #{projectID}\n" +
            "GROUP BY f.feedback_id")
    @Results(id = "FeedbackDtoResult", value = {
            @Result(column="feedback_id", property="feedbackId", typeHandler= UUIDTypeHandler.class, jdbcType= JdbcType.OTHER, id=true),
            @Result(column="project_id", property="projectId", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_id", property="userId", typeHandler=UUIDTypeHandler.class, jdbcType=JdbcType.OTHER),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="feedback_text", property="text", jdbcType=JdbcType.VARCHAR),
            @Result(column="f_timestamp", property="timestamp", jdbcType=JdbcType.BIGINT)
    })
    List<FeedbackDto> getAllFeedbacksWithVotes(@Param("projectID") String projectID);
}


