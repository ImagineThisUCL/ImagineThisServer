package com.ucl.imaginethisserver.Model;

import javax.annotation.Generated;

public class Conversion {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.conversion_id")
    private Object conversionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.project_id")
    private String projectId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.user_id")
    private Object userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.c_timestamp")
    private Long timestamp;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.conversion_status")
    private String conversionStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.publish_status")
    private String publishStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.conversion_id")
    public Object getConversionId() {
        return conversionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.conversion_id")
    public void setConversionId(Object conversionId) {
        this.conversionId = conversionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.project_id")
    public String getProjectId() {
        return projectId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.user_id")
    public Object getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.user_id")
    public void setUserId(Object userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.c_timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.c_timestamp")
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.conversion_status")
    public String getConversionStatus() {
        return conversionStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.conversion_status")
    public void setConversionStatus(String conversionStatus) {
        this.conversionStatus = conversionStatus == null ? null : conversionStatus.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.publish_status")
    public String getPublishStatus() {
        return publishStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: conversions.publish_status")
    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus == null ? null : publishStatus.trim();
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: conversions")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", conversionId=").append(conversionId);
        sb.append(", projectId=").append(projectId);
        sb.append(", userId=").append(userId);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", conversionStatus=").append(conversionStatus);
        sb.append(", publishStatus=").append(publishStatus);
        sb.append("]");
        return sb.toString();
    }
}