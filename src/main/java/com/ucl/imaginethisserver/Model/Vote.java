package com.ucl.imaginethisserver.Model;

import javax.annotation.Generated;

public class Vote {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.vote_id")
    private Object voteId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.feedback_id")
    private Object feedbackId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.user_id")
    private Object userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.vote")
    private Integer vote;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.v_timestamp")
    private Long timestamp;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.vote_id")
    public Object getVoteId() {
        return voteId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.vote_id")
    public void setVoteId(Object voteId) {
        this.voteId = voteId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.feedback_id")
    public Object getFeedbackId() {
        return feedbackId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.feedback_id")
    public void setFeedbackId(Object feedbackId) {
        this.feedbackId = feedbackId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.user_id")
    public Object getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.user_id")
    public void setUserId(Object userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.vote")
    public Integer getVote() {
        return vote;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.vote")
    public void setVote(Integer vote) {
        this.vote = vote;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.v_timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: votes.v_timestamp")
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: votes")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", voteId=").append(voteId);
        sb.append(", feedbackId=").append(feedbackId);
        sb.append(", userId=").append(userId);
        sb.append(", vote=").append(vote);
        sb.append(", timestamp=").append(timestamp);
        sb.append("]");
        return sb.toString();
    }
}