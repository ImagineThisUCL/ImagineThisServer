package com.ucl.imaginethisserver.Controller;

import com.ucl.imaginethisserver.CustomExceptions.NotFoundException;
import com.ucl.imaginethisserver.CustomExceptions.ProjectNotFoundException;
import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Service.FeedbackService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeedbackController.class)
class FeedbackControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService service;

    private List<Feedback> mockFeedbackList;

    private String realProjectID = "MgWqYTZMdjG26oA1CxbWaE";


    @BeforeEach
    void setUp() {
        mockFeedbackList = new ArrayList<>();
        mockFeedbackList.add(new Feedback(UUID.fromString("250a8d14-55d9-4cb1-93e2-29cd4ebda98b")
                , "MgWqYTZMdjG26oA1CxbWaE", UUID.fromString("f2366a7b-c2ca-40d5-939a-2f649411d257")
                , "John Dow", 1610955210, "Great prototype!"));
    }

    /*
    * The following tests will test the getAllFeedbacks method
    * */
    @Test
    void givenFeedbacks_whenGetAllFeedbacks_thenReturnJsonArray() throws Exception{

        given(service.getAllFeedbacks(realProjectID)).willReturn(mockFeedbackList);

        mockMvc.perform(get("/api/v1/projects/" + realProjectID + "/feedback")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userName", is("John Dow")));
    }

    @Test
    void givenInvalidProjectID_whenGetAllFeedbacks_thenReturnErrorNotFound() throws Exception {
        String projectID = "invalidProjectID";
        given(service.getAllFeedbacks(projectID)).willThrow(new NotFoundException());

        mockMvc.perform(get("/api/v1/projects/" + projectID + "/feedback")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenInvalidProjectIDOrFeedbackID_whenGetFeedbackByID_thenReturnErrorNotFound() throws Exception {
        String projectID = "invalidProjectID";
        UUID feedbackID = UUID.randomUUID();

        given(service.getFeedbackByID(projectID, feedbackID)).willThrow(new NotFoundException());
        given(service.getFeedbackByID(realProjectID, feedbackID)).willThrow(new NotFoundException());
        // both project id and feedback id are invalid
        mockMvc.perform(get("/api/v1/projects/" + projectID + "/feedback/" + feedbackID)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
        // only feedback id is invalid
        mockMvc.perform(get("/api/v1/projects/" + realProjectID + "/feedback/" + feedbackID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addNewFeedback() {
    }

    @Test
    void voteFeedback() {
    }
}