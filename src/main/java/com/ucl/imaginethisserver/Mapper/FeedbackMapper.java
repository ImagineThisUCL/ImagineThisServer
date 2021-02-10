package com.ucl.imaginethisserver.Mapper;

import static com.ucl.imaginethisserver.Mapper.FeedbackDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.ucl.imaginethisserver.DAO.FeedbackDto;
import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.TypeHandler.UUIDTypeHandler;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FeedbackMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    BasicColumn[] selectList = BasicColumn.columnList(feedbackId, projectId, userId, userName, text, timestamp);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<Feedback> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<Feedback> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("FeedbackResult")
    Optional<Feedback> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="FeedbackResult", value = {
        @Result(column="feedback_id", property="feedbackId", typeHandler=UUIDTypeHandler.class, jdbcType=JdbcType.OTHER, id=true),
        @Result(column="project_id", property="projectId", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="userId", typeHandler=UUIDTypeHandler.class, jdbcType=JdbcType.OTHER),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="feedback_text", property="text", jdbcType=JdbcType.VARCHAR),
        @Result(column="f_timestamp", property="timestamp", jdbcType=JdbcType.BIGINT)
    })
    List<Feedback> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, feedback, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, feedback, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    default int deleteByPrimaryKey(Object feedbackId_) {
        return delete(c -> 
            c.where(feedbackId, isEqualTo(feedbackId_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    default int insert(Feedback record) {
        return MyBatis3Utils.insert(this::insert, record, feedback, c ->
            c.map(feedbackId).toProperty("feedbackId")
            .map(projectId).toProperty("projectId")
            .map(userId).toProperty("userId")
            .map(userName).toProperty("userName")
            .map(text).toProperty("text")
            .map(timestamp).toProperty("timestamp")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    default int insertMultiple(Collection<Feedback> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, feedback, c ->
            c.map(feedbackId).toProperty("feedbackId")
            .map(projectId).toProperty("projectId")
            .map(userId).toProperty("userId")
            .map(userName).toProperty("userName")
            .map(text).toProperty("text")
            .map(timestamp).toProperty("timestamp")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    default int insertSelective(Feedback record) {
        return MyBatis3Utils.insert(this::insert, record, feedback, c ->
            c.map(feedbackId).toPropertyWhenPresent("feedbackId", record::getFeedbackId)
            .map(projectId).toPropertyWhenPresent("projectId", record::getProjectId)
            .map(userId).toPropertyWhenPresent("userId", record::getUserId)
            .map(userName).toPropertyWhenPresent("userName", record::getUserName)
            .map(text).toPropertyWhenPresent("text", record::getText)
            .map(timestamp).toPropertyWhenPresent("timestamp", record::getTimestamp)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    default Optional<Feedback> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, feedback, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    default List<Feedback> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, feedback, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    default List<Feedback> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, feedback, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    default Optional<Feedback> selectByPrimaryKey(Object feedbackId_) {
        return selectOne(c ->
            c.where(feedbackId, isEqualTo(feedbackId_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, feedback, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    static UpdateDSL<UpdateModel> updateAllColumns(Feedback record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(feedbackId).equalTo(record::getFeedbackId)
                .set(projectId).equalTo(record::getProjectId)
                .set(userId).equalTo(record::getUserId)
                .set(userName).equalTo(record::getUserName)
                .set(text).equalTo(record::getText)
                .set(timestamp).equalTo(record::getTimestamp);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Feedback record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(feedbackId).equalToWhenPresent(record::getFeedbackId)
                .set(projectId).equalToWhenPresent(record::getProjectId)
                .set(userId).equalToWhenPresent(record::getUserId)
                .set(userName).equalToWhenPresent(record::getUserName)
                .set(text).equalToWhenPresent(record::getText)
                .set(timestamp).equalToWhenPresent(record::getTimestamp);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    default int updateByPrimaryKey(Feedback record) {
        return update(c ->
            c.set(projectId).equalTo(record::getProjectId)
            .set(userId).equalTo(record::getUserId)
            .set(userName).equalTo(record::getUserName)
            .set(text).equalTo(record::getText)
            .set(timestamp).equalTo(record::getTimestamp)
            .where(feedbackId, isEqualTo(record::getFeedbackId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    default int updateByPrimaryKeySelective(Feedback record) {
        return update(c ->
            c.set(projectId).equalToWhenPresent(record::getProjectId)
            .set(userId).equalToWhenPresent(record::getUserId)
            .set(userName).equalToWhenPresent(record::getUserName)
            .set(text).equalToWhenPresent(record::getText)
            .set(timestamp).equalToWhenPresent(record::getTimestamp)
            .where(feedbackId, isEqualTo(record::getFeedbackId))
        );
    }

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