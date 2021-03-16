package com.ucl.imaginethisserver.Service;

import com.ucl.imaginethisserver.DAO.FeedbackDao;
import com.ucl.imaginethisserver.DAO.ProjectDao;
import com.ucl.imaginethisserver.DAO.VoteDao;
import com.ucl.imaginethisserver.Model.Project;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.ServiceImpl.FeedbackServiceImpl;
import com.ucl.imaginethisserver.Service.ServiceImpl.ProjectServiceImpl;
import com.ucl.imaginethisserver.Service.ServiceImpl.VoteServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ProjectServiceImplTest {
    static ProjectDao mockProjectDao;
    static Project testProject;
    static ProjectServiceImpl projectService;
    static final String PROJECT_ID = "MgWqYTZMdjG26oA1CxbWaE";
    static final String PROJECT_NAME = "ImagineThis Project";

    static List<Project> testProjects;


    @BeforeAll
    static void mockDAO() {
        mockProjectDao = mock(ProjectDao.class);

        // Prepare random project
        testProject = new Project();
        testProject.setProjectId(PROJECT_ID);
        testProject.setProjectName(PROJECT_NAME);

        testProjects = new ArrayList<>();
        testProjects.add(testProject);

        // Create ProjectService with mocked DAO
        projectService = new ProjectServiceImpl(mockProjectDao);

    }

    @Test
    void getAllProjectsTest(){
        when(mockProjectDao.getAllProjects()).thenReturn(testProjects);

        List<Project> resultList = projectService.getAllProjects();
        // Check size
        assertEquals(1, resultList.size());
        // Check content
        Project resultProject = resultList.get(0);
        assertEquals(resultProject.getProjectId(), PROJECT_ID);
        assertEquals(resultProject.getProjectName(), PROJECT_NAME);
    }

    @Test
    void getProjectNameByIDTest(){
        when(mockProjectDao.getProjectByID(PROJECT_ID)).thenReturn(testProject);

        String resultString = projectService.getProjectNameByID(PROJECT_ID);
        // Check size
        assertEquals(PROJECT_NAME, resultString);
    }

    @Test
    void addProjectTest(){
        when(mockProjectDao.addProject(testProject)).thenReturn(true);

        Boolean result = projectService.addProject(testProject);
        // Check size
        assertEquals(true, result);
    }

    @Test
    void updateProjectTest(){
        when(mockProjectDao.getProjectByID(PROJECT_ID)).thenReturn(testProject);
        when(mockProjectDao.updateProject(PROJECT_ID, testProject)).thenReturn(true);

        Boolean result = projectService.updateProject(PROJECT_ID,testProject);
        // Check size
        assertEquals(true, result);
    }

}
