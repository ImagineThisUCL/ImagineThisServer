package com.ucl.imaginethisserver.Mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class ProjectDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    public static final Project project = new Project();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: projects.project_id")
    public static final SqlColumn<String> projectId = project.projectId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: projects.project_name")
    public static final SqlColumn<String> projectName = project.projectName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: projects")
    public static final class Project extends SqlTable {
        public final SqlColumn<String> projectId = column("project_id", JDBCType.VARCHAR);

        public final SqlColumn<String> projectName = column("project_name", JDBCType.VARCHAR);

        public Project() {
            super("projects");
        }
    }
}