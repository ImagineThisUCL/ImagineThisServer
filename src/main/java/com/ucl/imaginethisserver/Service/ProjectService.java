package com.ucl.imaginethisserver.Service;

import com.ucl.imaginethisserver.Model.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    List<Project> getAllProjects();

    String getProjectNameByID(String id);
}
