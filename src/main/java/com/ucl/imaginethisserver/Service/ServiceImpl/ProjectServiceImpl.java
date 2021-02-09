package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.CustomExceptions.NotFoundException;
import com.ucl.imaginethisserver.Mapper.ProjectDynamicSqlSupport;
import com.ucl.imaginethisserver.Mapper.ProjectMapper;
import com.ucl.imaginethisserver.Model.Project;
import com.ucl.imaginethisserver.Service.ProjectService;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualToWhenPresent;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;

    private final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    public ProjectServiceImpl(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }


    @Override
    public List<Project> getAllProjects() {
        // select all projects and return as project list
        return projectMapper.select(c -> c);
    }

    @Override
    public String getProjectNameByID(String id) {
        Optional<Project> optional = projectMapper.selectOne(c -> c
        .where(ProjectDynamicSqlSupport.projectId, isEqualToWhenPresent(id)));
        if (optional.isPresent()) {
            return optional.get().getProjectName();
        } else {
            logger.error("Project with ID: " + id + " not exist.");
            throw new NotFoundException("Project Not Found");
        }
    }
}
