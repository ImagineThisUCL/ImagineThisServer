package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.CustomExceptions.InternalServerErrorException;
import com.ucl.imaginethisserver.CustomExceptions.NotFoundException;
import com.ucl.imaginethisserver.DAO.ProjectDao;
import com.ucl.imaginethisserver.Model.Project;
import com.ucl.imaginethisserver.Service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectDao projectDao;

    private final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    public ProjectServiceImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }


    @Override
    public List<Project> getAllProjects() {
        // select all projects and return as project list
        return projectDao.getAllProjects();
    }

    @Override
    public String getProjectNameByID(String id) {
        Project project = projectDao.getProjectByID(id);
        if (project != null) {
            return project.getProjectName();
        } else {
            logger.error("Project with ID: " + id + " not exist.");
            throw new NotFoundException("Project Not Found");
        }
    }

    @Override
    public boolean addProject(Project project) {
        return projectDao.addProject(project);
    }

    @Override
    public boolean updateProject(String projectID, Project project) {
        if (projectID == null) {
            logger.error("Error updating project. Project ID not provided");
            throw new InternalServerErrorException();
        }
        return projectDao.updateProject(projectID, project);
    }
}
