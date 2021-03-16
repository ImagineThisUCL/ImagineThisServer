package com.ucl.imaginethisserver.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucl.imaginethisserver.CustomExceptions.NotFoundException;
import com.ucl.imaginethisserver.DAO.FeedbackDto;
import com.ucl.imaginethisserver.Service.FeedbackService;
import com.ucl.imaginethisserver.Service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import com.ucl.imaginethisserver.Model.Project;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private ProjectService service;

    private List<Project> mockProjectList;

    private String mockProjectID = "MgWqYTZMdjG26oA1CxbWaE";

    private String mockProjectName = "ImagineThis Project";

    private Project mockProject;

    @BeforeEach
    void setUp() {
        /*
         * by default the gson was configured to exclude fields that are not annotated as Expose
         * global config can be found in application.properties
         * Thus create a new mockMvc that uses a custom gson
         * */
        mockMvc = MockMvcBuilders.standaloneSetup(new ProjectController(service))
                .setMessageConverters(new GsonHttpMessageConverter())
                .build();
        mockProject = new Project();
        mockProject.setProjectName(mockProjectName);
        mockProject.setProjectId(mockProjectID);
        mockProjectList = new ArrayList<>();
        mockProjectList.add(mockProject);
    }

    /*
     * The following tests will test the getAllProjects method
     * */

    @Test
    void givenNull_whenGetAllProjects_thenReturnJsonArray() throws Exception{
        given(service.getAllProjects()).willReturn(mockProjectList);

        mockMvc.perform(get("/api/v1/projects/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].projectId", is(mockProjectID)));
    }

    /*
     * The following tests will test the addProject method
     * */

    @Test
    void givenProject_whenAddProject_thenReturnJsonArray() throws Exception{
        given(service.addProject(ArgumentMatchers.any(Project.class))).willReturn(true);

        ObjectMapper mapper = new ObjectMapper();

        String requestJson= mapper.writeValueAsString(mockProject);

        mockMvc.perform(post("/api/v1/projects/")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk());
    }

    /*
     * The following tests will test the getProjectNameByID method
     * */

    @Test
    void givenValidProjectID_whenGetProjectNameByID_thenReturnString() throws Exception{
        given(service.getProjectNameByID(mockProjectID)).willReturn(mockProjectName);

        mockMvc.perform(get("/api/v1/projects/"+mockProjectID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectName", is(mockProjectName)));
    }

    @Test
    void givenInvalidProjectID_whenGetProjectNameByID_thenReturnNotFound() throws Exception{
        String projectID = "invalidProjectID";
        given(service.getProjectNameByID(projectID)).willReturn(null);

        mockMvc.perform(get("/api/v1/projects/"+mockProjectID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /*
     * The following tests will test the updateProject method
     * */

    @Test
    void givenValidProjectID_whenUpdateProject_thenReturnSuccess() throws Exception{
        given(service.updateProject(ArgumentMatchers.eq(mockProjectID), ArgumentMatchers.any(Project.class))).willReturn(true);
        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(mockProject);

        mockMvc.perform(patch("/api/v1/projects/"+mockProjectID)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void givenInvalidProjectID_whenUpdateProject_thenReturnSuccess() throws Exception{
        String projectID = "invalidProjectID";
        given(service.updateProject(ArgumentMatchers.eq(projectID), ArgumentMatchers.any(Project.class))).willReturn(false);

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(mockProject);

        mockMvc.perform(patch("/api/v1/projects/"+mockProjectID)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isNotFound());
    }

}
