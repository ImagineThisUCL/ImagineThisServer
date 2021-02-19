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

@WebMvcTest(GenerationController.class)
class GenerationControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private FeedbackService service;

    @BeforeEach
    void setUp() {
        /*
         * by default the gson was configured to exclude fields that are not annotated as Expose
         * global config can be found in application.properties
         * Thus create a new mockMvc that uses a custom gson
         * */
        mockMvc = MockMvcBuilders.standaloneSetup(new GenerationController(null))
                .setMessageConverters(new GsonHttpMessageConverter())
                .build();
    }

    /*
     * The following tests will test the getAllFeedbacks method
     * */
    @Test
    void givenFeedbacks_whenGetAllFeedbacks_thenReturnJsonArray() throws Exception {

//        given(service.getFeedbacksWithVotes(mockProjectID)).willReturn(mockFeedbackList);
//
//        mockMvc.perform(get("/api/v1/projects/" + mockProjectID + "/feedback")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].userName", is("John Dow")));
    }

    @Test
    void givenInvalidProjectID_whenGetAllFeedbacks_thenReturnErrorNotFound() throws Exception {
        String projectID = "invalidProjectID";
        given(service.getFeedbacksWithVotes(projectID)).willThrow(new NotFoundException());

        mockMvc.perform(get("/api/v1/projects/" + projectID + "/feedback")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
