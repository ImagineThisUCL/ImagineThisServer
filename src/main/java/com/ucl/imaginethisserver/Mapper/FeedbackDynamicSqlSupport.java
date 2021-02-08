package com.ucl.imaginethisserver.Mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class FeedbackDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    public static final Feedback feedback = new Feedback();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.feedback_id")
    public static final SqlColumn<Object> feedbackId = feedback.feedbackId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.project_id")
    public static final SqlColumn<String> projectId = feedback.projectId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.user_id")
    public static final SqlColumn<Object> userId = feedback.userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.user_name")
    public static final SqlColumn<String> userName = feedback.userName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.feedback_text")
    public static final SqlColumn<String> text = feedback.text;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.f_timestamp")
    public static final SqlColumn<Long> timestamp = feedback.timestamp;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    public static final class Feedback extends SqlTable {
        public final SqlColumn<Object> feedbackId = column("feedback_id", JDBCType.OTHER, "com.ucl.imaginethisserver.TypeHandler.UUIDTypeHandler");

        public final SqlColumn<String> projectId = column("project_id", JDBCType.VARCHAR);

        public final SqlColumn<Object> userId = column("user_id", JDBCType.OTHER, "com.ucl.imaginethisserver.TypeHandler.UUIDTypeHandler");

        public final SqlColumn<String> userName = column("user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> text = column("feedback_text", JDBCType.VARCHAR);

        public final SqlColumn<Long> timestamp = column("f_timestamp", JDBCType.BIGINT);

        public Feedback() {
            super("feedbacks");
        }
    }
}