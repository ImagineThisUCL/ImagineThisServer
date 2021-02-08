package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.Mapper.ProjectDynamicSqlSupport;
import com.ucl.imaginethisserver.Mapper.ProjectMapper;
import com.ucl.imaginethisserver.Model.Project;
import com.ucl.imaginethisserver.Service.ProjectService;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectServiceImpl(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }


    @Override
    public List<Project> getAllProjects() {
        // select all projects and return as project list
//        SelectStatementProvider provider = SqlBuilder.select(ProjectMapper.selectList)
//                .from(ProjectDynamicSqlSupport.project)
        return projectMapper.select(c -> c);
    }
}
