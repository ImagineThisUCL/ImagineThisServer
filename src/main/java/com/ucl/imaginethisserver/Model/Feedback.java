package com.ucl.imaginethisserver.Model;

import javax.annotation.Generated;

public class Feedback {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.feedback_id")
    private Object feedbackId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.project_id")
    private String projectId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.user_id")
    private Object userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.user_name")
    private String userName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.feedback_text")
    private String feedbackText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.f_timestamp")
    private Long timestamp;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.feedback_id")
    public Object getFeedbackId() {
        return feedbackId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.feedback_id")
    public void setFeedbackId(Object feedbackId) {
        this.feedbackId = feedbackId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.project_id")
    public String getProjectId() {
        return projectId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.user_id")
    public Object getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.user_id")
    public void setUserId(Object userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.user_name")
    public String getUserName() {
        return userName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.user_name")
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.feedback_text")
    public String getFeedbackText() {
        return feedbackText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.feedback_text")
    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText == null ? null : feedbackText.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.f_timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: feedbacks.f_timestamp")
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: feedbacks")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", feedbackId=").append(feedbackId);
        sb.append(", projectId=").append(projectId);
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", feedbackText=").append(feedbackText);
        sb.append(", timestamp=").append(timestamp);
        sb.append("]");
        return sb.toString();
    }
}