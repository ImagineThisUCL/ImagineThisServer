package com.ucl.imaginethisserver.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucl.imaginethisserver.CustomExceptions.NotFoundException;
import com.ucl.imaginethisserver.CustomExceptions.ProjectNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucl.imaginethisserver.DAO.FeedbackDto;
import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeedbackController.class)
class FeedbackControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private FeedbackService service;

    private List<FeedbackDto> mockFeedbackList;

    private String mockProjectID = "MgWqYTZMdjG26oA1CxbWaE";

    private UUID mockFeedbackID = UUID.fromString("250a8d14-55d9-4cb1-93e2-29cd4ebda98b");

    private UUID mockUserID = UUID.fromString("f2366a7b-c2ca-40d5-939a-2f649411d257");

    private UUID mockVoteID = UUID.fromString("f2366a7b-c2ca-40d5-939a-2f649411d258");

    private String mockUserName = "John Dow";

    private FeedbackDto mockFeedback;


    @BeforeEach
    void setUp() {
        /*
        * by default the gson was configured to exclude fields that are not annotated as Expose
        * global config can be found in application.properties
        * Thus create a new mockMvc that uses a custom gson
        * */
        mockMvc = MockMvcBuilders.standaloneSetup(new FeedbackController(service))
                .setMessageConverters(new GsonHttpMessageConverter())
                .build();
        mockFeedback = new FeedbackDto();
        mockFeedback.setFeedbackId(mockFeedbackID);
        mockFeedback.setProjectId(mockProjectID);
        mockFeedback.setUserId(mockUserID);
        mockFeedback.setUserName(mockUserName);
        mockFeedback.setTimestamp(System.currentTimeMillis());
        mockFeedback.setText("Great prototype!");
        mockFeedbackList = new ArrayList<>();
        mockFeedbackList.add(mockFeedback);
    }

    /*
    * The following tests will test the getAllFeedbacks method
    * */
    @Test
    void givenFeedbacks_whenGetAllFeedbacks_thenReturnJsonArray() throws Exception{

        given(service.getFeedbacksWithVotes(mockProjectID)).willReturn(mockFeedbackList);

        mockMvc.perform(get("/api/v1/projects/" + mockProjectID + "/feedback")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userName", is("John Dow")));
    }

    @Test
    void givenInvalidProjectID_whenGetAllFeedbacks_thenReturnErrorNotFound() throws Exception {
        String projectID = "invalidProjectID";
        given(service.getFeedbacksWithVotes(projectID)).willThrow(new NotFoundException());

        mockMvc.perform(get("/api/v1/projects/" + projectID + "/feedback")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenInvalidProjectIDOrFeedbackID_whenGetFeedbackByID_thenReturnErrorNotFound() throws Exception {
        String projectID = "invalidProjectID";
        UUID feedbackID = UUID.randomUUID();

        given(service.getFeedbackByID(projectID, feedbackID)).willThrow(new NotFoundException());
        given(service.getFeedbackByID(mockProjectID, feedbackID)).willThrow(new NotFoundException());
        // both project id and feedback id are invalid
        mockMvc.perform(get("/api/v1/projects/" + projectID + "/feedback/" + feedbackID)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
        // only feedback id is invalid
        mockMvc.perform(get("/api/v1/projects/" + mockProjectID + "/feedback/" + feedbackID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidProjectIDAndFeedbackID_whenGetFeedbackByID_thenReturnSpecificFeedback() throws Exception {
        given(service.getFeedbackByID(mockProjectID, mockFeedbackID)).willReturn(mockFeedback);

        mockMvc.perform(get("/api/v1/projects/" + mockProjectID + "/feedback/" + mockFeedbackID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feedbackId", is(mockFeedbackID.toString())))
                .andExpect(jsonPath("$.projectId", is(mockProjectID)))
                .andExpect(jsonPath("$.userId", is(mockUserID.toString())));
    }
}