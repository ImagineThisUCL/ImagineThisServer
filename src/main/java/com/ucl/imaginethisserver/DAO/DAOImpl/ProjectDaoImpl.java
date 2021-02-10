package com.ucl.imaginethisserver.DAO.DAOImpl;

import com.ucl.imaginethisserver.DAO.ProjectDao;
import com.ucl.imaginethisserver.Mapper.ProjectDynamicSqlSupport;
import com.ucl.imaginethisserver.Mapper.ProjectMapper;
import com.ucl.imaginethisserver.Model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualToWhenPresent;

@Repository
public class ProjectDaoImpl implements ProjectDao {
    private final ProjectMapper projectMapper;

    private final Logger logger = LoggerFactory.getLogger(ProjectDaoImpl.class);

    @Autowired
    public ProjectDaoImpl(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectMapper.select(c -> c);
    }

    @Override
    public Project getProjectByID(String id) {
        Optional<Project> optional = projectMapper.selectOne(c -> c
                .where(ProjectDynamicSqlSupport.projectId, isEqualToWhenPresent(id))
        );
        return optional.orElse(null);
    }

    @Override
    public boolean addProject(Project project) {
        return projectMapper.insert(project) != 0;
    }

    @Override
    public boolean updateProject(String projectID, Project project) {
        return projectMapper.update(c -> c
                .set(ProjectDynamicSqlSupport.projectName).equalTo(project.getProjectName())
                .where(ProjectDynamicSqlSupport.projectId, isEqualTo(projectID))
        ) != 0;
    }
}
