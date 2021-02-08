package com.ucl.imaginethisserver.Mapper;

import static com.ucl.imaginethisserver.Mapper.ProjectDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.ucl.imaginethisserver.Model.Project;
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

@Mapper
public interface ProjectMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    BasicColumn[] selectList = BasicColumn.columnList(projectId, projectName);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<Project> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<Project> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("ProjectResult")
    Optional<Project> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="ProjectResult", value = {
        @Result(column="project_id", property="projectId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="project_name", property="projectName", jdbcType=JdbcType.VARCHAR)
    })
    List<Project> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, project, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, project, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    default int deleteByPrimaryKey(String projectId_) {
        return delete(c -> 
            c.where(projectId, isEqualTo(projectId_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    default int insert(Project record) {
        return MyBatis3Utils.insert(this::insert, record, project, c ->
            c.map(projectId).toProperty("projectId")
            .map(projectName).toProperty("projectName")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    default int insertMultiple(Collection<Project> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, project, c ->
            c.map(projectId).toProperty("projectId")
            .map(projectName).toProperty("projectName")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    default int insertSelective(Project record) {
        return MyBatis3Utils.insert(this::insert, record, project, c ->
            c.map(projectId).toPropertyWhenPresent("projectId", record::getProjectId)
            .map(projectName).toPropertyWhenPresent("projectName", record::getProjectName)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    default Optional<Project> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, project, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    default List<Project> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, project, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    default List<Project> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, project, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    default Optional<Project> selectByPrimaryKey(String projectId_) {
        return selectOne(c ->
            c.where(projectId, isEqualTo(projectId_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, project, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    static UpdateDSL<UpdateModel> updateAllColumns(Project record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(projectId).equalTo(record::getProjectId)
                .set(projectName).equalTo(record::getProjectName);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Project record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(projectId).equalToWhenPresent(record::getProjectId)
                .set(projectName).equalToWhenPresent(record::getProjectName);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    default int updateByPrimaryKey(Project record) {
        return update(c ->
            c.set(projectName).equalTo(record::getProjectName)
            .where(projectId, isEqualTo(record::getProjectId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    default int updateByPrimaryKeySelective(Project record) {
        return update(c ->
            c.set(projectName).equalToWhenPresent(record::getProjectName)
            .where(projectId, isEqualTo(record::getProjectId))
        );
    }
}