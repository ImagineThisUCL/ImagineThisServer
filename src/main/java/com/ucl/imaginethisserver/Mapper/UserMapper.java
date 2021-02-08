package com.ucl.imaginethisserver.Mapper;

import static com.ucl.imaginethisserver.Mapper.UserDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.ucl.imaginethisserver.Model.User;
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

@Mapper
public interface UserMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    BasicColumn[] selectList = BasicColumn.columnList(userId, userName);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<User> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<User> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UserResult")
    Optional<User> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UserResult", value = {
        @Result(column="user_id", property="userId", typeHandler=UUIDTypeHandler.class, jdbcType=JdbcType.OTHER, id=true),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR)
    })
    List<User> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, user, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, user, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    default int deleteByPrimaryKey(Object userId_) {
        return delete(c -> 
            c.where(userId, isEqualTo(userId_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    default int insert(User record) {
        return MyBatis3Utils.insert(this::insert, record, user, c ->
            c.map(userId).toProperty("userId")
            .map(userName).toProperty("userName")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    default int insertMultiple(Collection<User> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, user, c ->
            c.map(userId).toProperty("userId")
            .map(userName).toProperty("userName")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    default int insertSelective(User record) {
        return MyBatis3Utils.insert(this::insert, record, user, c ->
            c.map(userId).toPropertyWhenPresent("userId", record::getUserId)
            .map(userName).toPropertyWhenPresent("userName", record::getUserName)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    default Optional<User> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, user, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    default List<User> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, user, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    default List<User> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, user, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    default Optional<User> selectByPrimaryKey(Object userId_) {
        return selectOne(c ->
            c.where(userId, isEqualTo(userId_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, user, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    static UpdateDSL<UpdateModel> updateAllColumns(User record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(userId).equalTo(record::getUserId)
                .set(userName).equalTo(record::getUserName);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(User record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(userId).equalToWhenPresent(record::getUserId)
                .set(userName).equalToWhenPresent(record::getUserName);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    default int updateByPrimaryKey(User record) {
        return update(c ->
            c.set(userName).equalTo(record::getUserName)
            .where(userId, isEqualTo(record::getUserId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    default int updateByPrimaryKeySelective(User record) {
        return update(c ->
            c.set(userName).equalToWhenPresent(record::getUserName)
            .where(userId, isEqualTo(record::getUserId))
        );
    }
}