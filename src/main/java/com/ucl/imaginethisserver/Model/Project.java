package com.ucl.imaginethisserver.Model;

import javax.annotation.Generated;

public class Project {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: projects.project_id")
    private String projectId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: projects.project_name")
    private String projectName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: projects.project_id")
    public String getProjectId() {
        return projectId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: projects.project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: projects.project_name")
    public String getProjectName() {
        return projectName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: projects.project_name")
    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", projectId=").append(projectId);
        sb.append(", projectName=").append(projectName);
        sb.append("]");
        return sb.toString();
    }
}