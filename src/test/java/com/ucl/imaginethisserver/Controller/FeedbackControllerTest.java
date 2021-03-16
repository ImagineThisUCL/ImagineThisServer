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
import org.mockito.ArgumentMatchers;
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
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.ArgumentMatchers;
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
    void givenValidProjectID_whenGetAllFeedbacks_thenReturnJsonArray() throws Exception{

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

    /*
     * The following tests will test the getFeedbackByID method
     * */

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

    /*
     * The following tests will test the addNewFeedback method
     * */

    @Test
    void givenValidProjectID_whenAddNewFeedback_thenReturnSuccess() throws Exception {
        given(service.addNewFeedback(ArgumentMatchers.eq(mockProjectID), any(Feedback.class))).willReturn(true);
        ObjectMapper mapper = new ObjectMapper();

        String requestJson= mapper.writeValueAsString(mockFeedback);

        mockMvc.perform(post("/api/v1/projects/" + mockProjectID + "/feedback")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void givenInvalidProjectID_whenAddNewFeedback_thenReturnErrorNotFound() throws Exception {
        String projectID = "invalidProjectID";
        ObjectMapper mapper = new ObjectMapper();

        FeedbackDto invalidFeedback = new FeedbackDto();
        invalidFeedback.setFeedbackId(mockFeedbackID);
        invalidFeedback.setProjectId(projectID);
        invalidFeedback.setUserId(mockUserID);
        invalidFeedback.setUserName(mockUserName);
        invalidFeedback.setTimestamp(System.currentTimeMillis());

        given(service.getFeedbacksWithVotes(projectID)).willThrow(new NotFoundException());
        String requestJson= mapper.writeValueAsString(mockFeedback);

        mockMvc.perform(post("/api/v1/projects/" + projectID + "/feedback")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isNotFound());
    }

    /*
     * The following tests will test the updateFeedback method
     * */

    @Test
    void givenValidProjectIDAndFeedbackID_whenUpdateFeedback_thenReturnSuccess() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        given(service.updateFeedback(ArgumentMatchers.eq(mockProjectID), ArgumentMatchers.eq(mockFeedbackID), any(Feedback.class))).willReturn(true);
        String requestJson = mapper.writeValueAsString(mockFeedback);

        mockMvc.perform(patch("/api/v1/projects/" + mockProjectID + "/feedback/" + mockFeedbackID)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void givenInvalidProjectIDOrFeedbackID_whenUpdateFeedback_thenReturnErrorNotFound() throws Exception {
        String projectID = "invalidProjectID";
        UUID feedbackID = UUID.randomUUID();
        ObjectMapper mapper = new ObjectMapper();

        given(service.updateFeedback(projectID, mockFeedbackID, mockFeedback)).willThrow(new NotFoundException());
        given(service.updateFeedback(mockProjectID, feedbackID, mockFeedback)).willThrow(new NotFoundException());
        String requestJson = mapper.writeValueAsString(mockFeedback);

        mockMvc.perform(patch("/api/v1/projects/" + projectID + "/feedback/" + mockFeedbackID)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isNotFound());

        mockMvc.perform(patch("/api/v1/projects/" + mockProjectID + "/feedback/" + feedbackID)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isNotFound());
    }

    /*
     * The following tests will test the deleteFeedback method
     * */

    @Test
    void givenValidProjectIDAndFeedbackID_whenDeleteFeedback_thenReturnSuccess() throws Exception {
        given(service.deleteFeedback(mockProjectID, mockFeedbackID)).willReturn(true);
        ObjectMapper mapper = new ObjectMapper();

        String requestJson= mapper.writeValueAsString(mockFeedback);

        mockMvc.perform(delete("/api/v1/projects/" + mockProjectID + "/feedback/" + mockFeedbackID)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk());
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