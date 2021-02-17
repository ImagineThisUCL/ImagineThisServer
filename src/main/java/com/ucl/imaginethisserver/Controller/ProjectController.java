package com.ucl.imaginethisserver.Controller;

import com.ucl.imaginethisserver.Model.Project;
import com.ucl.imaginethisserver.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping("/projects")
    public ResponseEntity<Map<String, Boolean>> addProject(@RequestBody Project project) {
        boolean result = projectService.addProject(project);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/projects/{project-id}")
    public ResponseEntity<Map<String, String>> getProjectNameByID(@PathVariable("project-id") String projectID) {
        Map<String, String> m = new HashMap<>();
        m.put("projectName", projectService.getProjectNameByID(projectID));
        return ResponseEntity.ok(m);
    }

    @PatchMapping("/projects/{project-id}")
    public ResponseEntity<Map<String, Boolean>> updateProject(@PathVariable("project-id") String projectID,
                                                              @RequestBody Project project) {
        boolean result = projectService.updateProject(projectID, project);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
