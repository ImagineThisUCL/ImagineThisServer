package com.ucl.imaginethisserver.Mapper;

import static com.ucl.imaginethisserver.Mapper.ConversionDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.ucl.imaginethisserver.DAO.ConversionDto;
import com.ucl.imaginethisserver.Model.Conversion;
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

@Mapper
public interface ConversionMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    BasicColumn[] selectList = BasicColumn.columnList(conversionId, projectId, userId, timestamp, conversionStatus, publishStatus);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<Conversion> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<Conversion> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("ConversionResult")
    Optional<Conversion> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="ConversionResult", value = {
        @Result(column="conversion_id", property="conversionId", typeHandler=UUIDTypeHandler.class, jdbcType=JdbcType.OTHER, id=true),
        @Result(column="project_id", property="projectId", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="userId", typeHandler=UUIDTypeHandler.class, jdbcType=JdbcType.OTHER),
        @Result(column="c_timestamp", property="timestamp", jdbcType=JdbcType.BIGINT),
        @Result(column="conversion_status", property="conversionStatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="publish_status", property="publishStatus", jdbcType=JdbcType.VARCHAR)
    })
    List<Conversion> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, conversion, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, conversion, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    default int deleteByPrimaryKey(Object conversionId_) {
        return delete(c -> 
            c.where(conversionId, isEqualTo(conversionId_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    default int insert(Conversion record) {
        return MyBatis3Utils.insert(this::insert, record, conversion, c ->
            c.map(conversionId).toProperty("conversionId")
            .map(projectId).toProperty("projectId")
            .map(userId).toProperty("userId")
            .map(timestamp).toProperty("timestamp")
            .map(conversionStatus).toProperty("conversionStatus")
            .map(publishStatus).toProperty("publishStatus")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    default int insertMultiple(Collection<Conversion> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, conversion, c ->
            c.map(conversionId).toProperty("conversionId")
            .map(projectId).toProperty("projectId")
            .map(userId).toProperty("userId")
            .map(timestamp).toProperty("timestamp")
            .map(conversionStatus).toProperty("conversionStatus")
            .map(publishStatus).toProperty("publishStatus")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    default int insertSelective(Conversion record) {
        return MyBatis3Utils.insert(this::insert, record, conversion, c ->
            c.map(conversionId).toPropertyWhenPresent("conversionId", record::getConversionId)
            .map(projectId).toPropertyWhenPresent("projectId", record::getProjectId)
            .map(userId).toPropertyWhenPresent("userId", record::getUserId)
            .map(timestamp).toPropertyWhenPresent("timestamp", record::getTimestamp)
            .map(conversionStatus).toPropertyWhenPresent("conversionStatus", record::getConversionStatus)
            .map(publishStatus).toPropertyWhenPresent("publishStatus", record::getPublishStatus)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    default Optional<Conversion> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, conversion, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    default List<Conversion> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, conversion, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    default List<Conversion> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, conversion, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    default Optional<Conversion> selectByPrimaryKey(Object conversionId_) {
        return selectOne(c ->
            c.where(conversionId, isEqualTo(conversionId_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, conversion, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    static UpdateDSL<UpdateModel> updateAllColumns(Conversion record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(conversionId).equalTo(record::getConversionId)
                .set(projectId).equalTo(record::getProjectId)
                .set(userId).equalTo(record::getUserId)
                .set(timestamp).equalTo(record::getTimestamp)
                .set(conversionStatus).equalTo(record::getConversionStatus)
                .set(publishStatus).equalTo(record::getPublishStatus);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Conversion record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(conversionId).equalToWhenPresent(record::getConversionId)
                .set(projectId).equalToWhenPresent(record::getProjectId)
                .set(userId).equalToWhenPresent(record::getUserId)
                .set(timestamp).equalToWhenPresent(record::getTimestamp)
                .set(conversionStatus).equalToWhenPresent(record::getConversionStatus)
                .set(publishStatus).equalToWhenPresent(record::getPublishStatus);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    default int updateByPrimaryKey(Conversion record) {
        return update(c ->
            c.set(projectId).equalTo(record::getProjectId)
            .set(userId).equalTo(record::getUserId)
            .set(timestamp).equalTo(record::getTimestamp)
            .set(conversionStatus).equalTo(record::getConversionStatus)
            .set(publishStatus).equalTo(record::getPublishStatus)
            .where(conversionId, isEqualTo(record::getConversionId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    default int updateByPrimaryKeySelective(Conversion record) {
        return update(c ->
            c.set(projectId).equalToWhenPresent(record::getProjectId)
            .set(userId).equalToWhenPresent(record::getUserId)
            .set(timestamp).equalToWhenPresent(record::getTimestamp)
            .set(conversionStatus).equalToWhenPresent(record::getConversionStatus)
            .set(publishStatus).equalToWhenPresent(record::getPublishStatus)
            .where(conversionId, isEqualTo(record::getConversionId))
        );
    }


    @Select("SELECT conversion_id, project_id, u.user_id, u.user_name, c_timestamp, \n" +
            "conversion_status, publish_status \n" +
            "FROM conversions c \n" +
            "LEFT JOIN users u ON c.user_id = u.user_id \n" +
            "WHERE c.project_id = #{projectID}")
    @Results(id = "ConversionDtoResult", value = {
            @Result(column="conversion_id", property="conversionId", typeHandler= UUIDTypeHandler.class, jdbcType= JdbcType.OTHER, id=true),
            @Result(column="project_id", property="projectId", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_id", property="userId", typeHandler=UUIDTypeHandler.class, jdbcType=JdbcType.OTHER),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="conversion_status", property="conversionStatus", jdbcType=JdbcType.VARCHAR),
            @Result(column="publish_status", property="publishStatus", jdbcType=JdbcType.VARCHAR),
            @Result(column="c_timestamp", property="timestamp", jdbcType=JdbcType.BIGINT)
    })
    List<ConversionDto> getConversions(@Param("projectID") String projectID);
}