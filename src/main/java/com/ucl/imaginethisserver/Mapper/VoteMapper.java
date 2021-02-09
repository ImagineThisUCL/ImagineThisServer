package com.ucl.imaginethisserver.Mapper;

import static com.ucl.imaginethisserver.Mapper.VoteDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.TypeHandler.UUIDTypeHandler;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
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
public interface VoteMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    BasicColumn[] selectList = BasicColumn.columnList(voteId, feedbackId, userId, voteValue, timestamp);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<Vote> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<Vote> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("VoteResult")
    Optional<Vote> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="VoteResult", value = {
        @Result(column="vote_id", property="voteId", typeHandler=UUIDTypeHandler.class, jdbcType=JdbcType.OTHER, id=true),
        @Result(column="feedback_id", property="feedbackId", typeHandler=UUIDTypeHandler.class, jdbcType=JdbcType.OTHER),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.OTHER),
        @Result(column="vote", property="voteValue", jdbcType=JdbcType.INTEGER),
        @Result(column="v_timestamp", property="timestamp", jdbcType=JdbcType.BIGINT)
    })
    List<Vote> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, vote, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, vote, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    default int deleteByPrimaryKey(Object voteId_) {
        return delete(c -> 
            c.where(voteId, isEqualTo(voteId_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    default int insert(Vote record) {
        return MyBatis3Utils.insert(this::insert, record, vote, c ->
            c.map(voteId).toProperty("voteId")
            .map(feedbackId).toProperty("feedbackId")
            .map(userId).toProperty("userId")
            .map(voteValue).toProperty("voteValue")
            .map(timestamp).toProperty("timestamp")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    default int insertMultiple(Collection<Vote> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, vote, c ->
            c.map(voteId).toProperty("voteId")
            .map(feedbackId).toProperty("feedbackId")
            .map(userId).toProperty("userId")
            .map(voteValue).toProperty("voteValue")
            .map(timestamp).toProperty("timestamp")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    default int insertSelective(Vote record) {
        return MyBatis3Utils.insert(this::insert, record, vote, c ->
            c.map(voteId).toPropertyWhenPresent("voteId", record::getVoteId)
            .map(feedbackId).toPropertyWhenPresent("feedbackId", record::getFeedbackId)
            .map(userId).toPropertyWhenPresent("userId", record::getUserId)
            .map(voteValue).toPropertyWhenPresent("voteValue", record::getVoteValue)
            .map(timestamp).toPropertyWhenPresent("timestamp", record::getTimestamp)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    default Optional<Vote> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, vote, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    default List<Vote> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, vote, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    default List<Vote> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, vote, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    default Optional<Vote> selectByPrimaryKey(Object voteId_) {
        return selectOne(c ->
            c.where(voteId, isEqualTo(voteId_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, vote, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    static UpdateDSL<UpdateModel> updateAllColumns(Vote record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(voteId).equalTo(record::getVoteId)
                .set(feedbackId).equalTo(record::getFeedbackId)
                .set(userId).equalTo(record::getUserId)
                .set(voteValue).equalTo(record::getVoteValue)
                .set(timestamp).equalTo(record::getTimestamp);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Vote record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(voteId).equalToWhenPresent(record::getVoteId)
                .set(feedbackId).equalToWhenPresent(record::getFeedbackId)
                .set(userId).equalToWhenPresent(record::getUserId)
                .set(voteValue).equalToWhenPresent(record::getVoteValue)
                .set(timestamp).equalToWhenPresent(record::getTimestamp);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    default int updateByPrimaryKey(Vote record) {
        return update(c ->
            c.set(feedbackId).equalTo(record::getFeedbackId)
            .set(userId).equalTo(record::getUserId)
            .set(voteValue).equalTo(record::getVoteValue)
            .set(timestamp).equalTo(record::getTimestamp)
            .where(voteId, isEqualTo(record::getVoteId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    default int updateByPrimaryKeySelective(Vote record) {
        return update(c ->
            c.set(feedbackId).equalToWhenPresent(record::getFeedbackId)
            .set(userId).equalToWhenPresent(record::getUserId)
            .set(voteValue).equalToWhenPresent(record::getVoteValue)
            .set(timestamp).equalToWhenPresent(record::getTimestamp)
            .where(voteId, isEqualTo(record::getVoteId))
        );
    }
}