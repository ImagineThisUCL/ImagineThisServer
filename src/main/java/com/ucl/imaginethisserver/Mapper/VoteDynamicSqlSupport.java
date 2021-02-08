package com.ucl.imaginethisserver.Mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class VoteDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    public static final Vote vote = new Vote();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.vote_id")
    public static final SqlColumn<Object> voteId = vote.voteId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.feedback_id")
    public static final SqlColumn<Object> feedbackId = vote.feedbackId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.user_id")
    public static final SqlColumn<Object> userId = vote.userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.v_timestamp")
    public static final SqlColumn<Long> timestamp = vote.timestamp;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    public static final class Vote extends SqlTable {
        public final SqlColumn<Object> voteId = column("vote_id", JDBCType.OTHER, "com.ucl.imaginethisserver.TypeHandler.UUIDTypeHandler");

        public final SqlColumn<Object> feedbackId = column("feedback_id", JDBCType.OTHER, "com.ucl.imaginethisserver.TypeHandler.UUIDTypeHandler");

        public final SqlColumn<Object> userId = column("user_id", JDBCType.OTHER);

        public final SqlColumn<Integer> vote = column("vote", JDBCType.INTEGER);

        public final SqlColumn<Long> timestamp = column("v_timestamp", JDBCType.BIGINT);

        public Vote() {
            super("votes");
        }
    }
}