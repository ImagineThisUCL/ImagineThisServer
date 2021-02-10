package com.ucl.imaginethisserver.Service;

import com.ucl.imaginethisserver.Model.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    /**
     * This method will get all projects
     * @return list of projects
     */
    List<Project> getAllProjects();

    /**
     * This method gets the project name with given project ID
     * @param id ID of the project
     * @return name of that project
     */
    String getProjectNameByID(String id);

    /**
     * This method will add a new project to the database
     * @param project project object which includes project ID and project name
     * @return bool value which indicates the operation status
     */
    boolean addProject(Project project);

    /**
     * This method will update the fields of project with given project ID
     * @param project project object
     * @return bool value which indicates the operation status
     */
    boolean updateProject(String projectID, Project project);
}
