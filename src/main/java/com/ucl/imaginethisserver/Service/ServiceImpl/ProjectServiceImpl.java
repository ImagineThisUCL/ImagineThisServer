package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.DAO.ProjectDao;
import com.ucl.imaginethisserver.Model.Project;
import com.ucl.imaginethisserver.Service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
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
            throw new NotFoundException();
        }
    }

    @Override
    public boolean addProject(Project project) {
        if (projectDao.addProject(project)) {
            return true;
        } else {
            // fail to add project, throw internal server error
            logger.error("Error adding new project with ID: " + project.getProjectId());
            throw new InternalServerErrorException();
        }
    }

    @Override
    public boolean updateProject(String projectID, Project project) {
        Project get_project = projectDao.getProjectByID(projectID);

        if (get_project == null) {
            logger.error("Error updating project. Project ID not provided");
            throw new InternalServerErrorException();
        }
        return projectDao.updateProject(projectID, project);
    }
}
