package com.ucl.imaginethisserver.Mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class UserDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    public static final User user = new User();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: users.user_id")
    public static final SqlColumn<Object> userId = user.userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: users.user_name")
    public static final SqlColumn<String> userName = user.userName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: users")
    public static final class User extends SqlTable {
        public final SqlColumn<Object> userId = column("user_id", JDBCType.OTHER, "com.ucl.imaginethisserver.TypeHandler.UUIDTypeHandler");

        public final SqlColumn<String> userName = column("user_name", JDBCType.VARCHAR);

        public User() {
            super("users");
        }
    }
}