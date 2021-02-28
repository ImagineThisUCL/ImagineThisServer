package com.ucl.imaginethisserver.Mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class ConversionDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    public static final Conversion conversion = new Conversion();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.conversion_id")
    public static final SqlColumn<Object> conversionId = conversion.conversionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.project_id")
    public static final SqlColumn<String> projectId = conversion.projectId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.user_id")
    public static final SqlColumn<Object> userId = conversion.userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.c_timestamp")
    public static final SqlColumn<Long> timestamp = conversion.timestamp;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    public static final class Conversion extends SqlTable {
        public final SqlColumn<Object> conversionId = column("conversion_id", JDBCType.OTHER, "com.ucl.imaginethisserver.TypeHandler.UUIDTypeHandler");

        public final SqlColumn<String> projectId = column("project_id", JDBCType.VARCHAR);

        public final SqlColumn<Object> userId = column("user_id", JDBCType.OTHER, "com.ucl.imaginethisserver.TypeHandler.UUIDTypeHandler");

        public final SqlColumn<Long> timestamp = column("c_timestamp", JDBCType.BIGINT);

        public Conversion() {
            super("conversions");
        }
    }
}