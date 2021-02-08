package com.ucl.imaginethisserver.Controller;

import com.ucl.imaginethisserver.Model.Project;
import com.ucl.imaginethisserver.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    @ResponseBody
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }
}
