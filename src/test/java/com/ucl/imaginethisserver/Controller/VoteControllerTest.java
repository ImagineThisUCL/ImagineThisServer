package com.ucl.imaginethisserver.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucl.imaginethisserver.DAO.FeedbackDto;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.FeedbackService;
import com.ucl.imaginethisserver.Service.VoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VoteController.class)
class VoteControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private VoteService service;

    @MockBean
    private FeedbackService feedbackService;

    private List<Vote> mockVoteList;

    private final String mockProjectID = "MgWqYTZMdjG26oA1CxbWaE";

    private final UUID mockFeedbackID = UUID.fromString("250a8d14-55d9-4cb1-93e2-29cd4ebda98b");

    private final UUID mockUserID = UUID.fromString("f2366a7b-c2ca-40d5-939a-2f649411d257");

    private final UUID mockVoteID = UUID.fromString("f2366a7b-c2ca-40d5-939a-2f649411d258");

    private final String mockUserName = "John Dow";

    private final Integer mockVoteValue = 1;

    private Vote mockVote;

    private FeedbackDto mockFeedback;

    private List<FeedbackDto> mockFeedbackList;

    @BeforeEach
    void setUp() {
        /*
         * by default the gson was configured to exclude fields that are not annotated as Expose
         * global config can be found in application.properties
         * Thus create a new mockMvc that uses a custom gson
         * */
        mockMvc = MockMvcBuilders.standaloneSetup(new VoteController(service))
                .setMessageConverters(new GsonHttpMessageConverter())
                .build();
        mockVote = new Vote();
        mockVote.setFeedbackId(mockFeedbackID);
        mockVote.setVoteId(mockVoteID);
        mockVote.setUserId(mockUserID);
        mockVote.setTimestamp(System.currentTimeMillis());
        mockVote.setVoteValue(mockVoteValue);
        mockVoteList = new ArrayList<>();
        mockVoteList.add(mockVote);

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
     * The following tests will test the getVotesForFeedback method
     * */
    @Test
    void givenValidProjectIDAndFeedbackID_whenGetVotesForFeedback_thenReturnJsonArray() throws Exception{


        given(service.getVotesForFeedback(mockProjectID, mockFeedbackID)).willReturn(mockVoteList);

        mockMvc.perform(get("/api/v1/projects/" + mockProjectID + "/feedback/" + mockFeedbackID + "/vote")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].voteId", is("f2366a7b-c2ca-40d5-939a-2f649411d258")))
                .andExpect(jsonPath("$[0].userId", is("f2366a7b-c2ca-40d5-939a-2f649411d257")));
    }

    @Test
    void givenInvalidProjectIDOrFeedbackID_whenGetVotesForFeedback_thenReturnErrorNotFound() throws Exception {
        UUID feedbackID = UUID.randomUUID();

        given(service.getVotesForFeedback(mockProjectID, feedbackID)).willThrow(new NotFoundException());

        mockMvc.perform(get("/api/v1/projects/" + mockProjectID + "/feedback/" + feedbackID + "/vote")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /*
     * The following tests will test the voteFeedback method
     * */

    @Test
    void givenValidProjectIDAndFeedbackID_whenVoteFeedback_thenReturnSuccess() throws Exception {

        given(service.voteFeedback(eq(mockProjectID), eq(mockFeedbackID), any(Vote.class))).willReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(mockVote);

        mockMvc.perform(post("/api/v1/projects/" + mockProjectID + "/feedback/" + mockFeedbackID + "/vote")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void givenInvalidProjectIDOrFeedbackID_whenVoteFeedback_thenReturnInternalServerError() throws Exception {
        String projectID = "invalidProjectID";
        UUID feedbackID = UUID.randomUUID();

        // service will throw InternalServerErrorException
        // since the payload mockVote has all fields correctly set
        given(service.voteFeedback(eq(mockProjectID), eq(feedbackID), any(Vote.class))).willThrow(new InternalServerErrorException());
        given(service.voteFeedback(eq(projectID), eq(mockFeedbackID), any(Vote.class))).willThrow(new InternalServerErrorException());

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(mockVote);

        // controller will return InternalServerError
        mockMvc.perform(post("/api/v1/projects/" + projectID + "/feedback/" + mockFeedbackID + "/vote")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(post("/api/v1/projects/" + mockProjectID + "/feedback/" + feedbackID + "/vote")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isInternalServerError());
    }

    /*
     * The following tests will test the updateVoteForFeedback method
     * */

    @Test
    void givenValidProjectIDAndFeedbackIDAndVoteID_whenUpdateVoteForFeedback_thenReturnSuccess() throws Exception {

        given(service.updateVoteForFeedback(eq(mockProjectID), eq(mockFeedbackID), eq(mockVoteID), any(Vote.class))).willReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(mockVote);

        mockMvc.perform(patch("/api/v1/projects/" + mockProjectID + "/feedback/" + mockFeedbackID + "/vote/" + mockVoteID)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void givenInvalidProjectIDOrFeedbackIDOrVoteID_whenUpdateVoteForFeedback_thenReturnInternalServerError() throws Exception {
        String projectID = "invalidProjectID";
        UUID feedbackID = UUID.randomUUID();
        UUID voteID = UUID.randomUUID();

        // service will throw IllegalArgumentException
        given(service.updateVoteForFeedback(eq(projectID), eq(mockFeedbackID), eq(mockVoteID), any(Vote.class))).willThrow(new IllegalArgumentException());
        given(service.updateVoteForFeedback(eq(mockProjectID), eq(feedbackID), eq(mockVoteID), any(Vote.class))).willThrow(new IllegalArgumentException());
        given(service.updateVoteForFeedback(eq(mockProjectID), eq(mockFeedbackID), eq(voteID), any(Vote.class))).willThrow(new InternalServerErrorException());

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(mockVote);

        // controller will return InternalServerError
        mockMvc.perform(patch("/api/v1/projects/" + projectID + "/feedback/" + mockFeedbackID + "/vote/" + mockVoteID)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(patch("/api/v1/projects/" + mockProjectID + "/feedback/" + feedbackID + "/vote/" + mockVoteID)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(patch("/api/v1/projects/" + mockProjectID + "/feedback/" + mockFeedbackID + "/vote/" + voteID)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isInternalServerError());
    }

    /*
     * The following tests will test the deleteVoteForFeedback method
     * */


    @Test
    void givenValidProjectIDAndFeedbackIDAndVoteID_whenDeleteVoteForFeedback_thenReturnSuccess() throws Exception {
        given(service.deleteVoteForFeedback(eq(mockProjectID), eq(mockFeedbackID), eq(mockVoteID), any(Vote.class))).willReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(mockVote);

        mockMvc.perform(delete("/api/v1/projects/" + mockProjectID + "/feedback/" + mockFeedbackID + "/vote/" + mockVoteID)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void givenInvalidProjectIDOrFeedbackIDOrVoteID_whenDeleteVoteForFeedback_thenReturnInternalServerError() throws Exception {
        String projectID = "invalidProjectID";
        UUID feedbackID = UUID.randomUUID();
        UUID voteID = UUID.randomUUID();

        // service will throw InternalServerErrorException
        given(service.deleteVoteForFeedback(eq(projectID), eq(mockFeedbackID), eq(mockVoteID), any(Vote.class))).willThrow(new InternalServerErrorException());
        given(service.deleteVoteForFeedback(eq(mockProjectID), eq(feedbackID), eq(mockVoteID), any(Vote.class))).willThrow(new InternalServerErrorException());
        given(service.deleteVoteForFeedback(eq(mockProjectID), eq(mockFeedbackID), eq(voteID), any(Vote.class))).willThrow(new InternalServerErrorException());

        ObjectMapper mapper = new ObjectMapper();
        byte[] requestJson = mapper.writeValueAsBytes(mockVote);

        // controller will return InternalServerError
        mockMvc.perform(delete("/api/v1/projects/" + projectID + "/feedback/" + mockFeedbackID + "/vote/"+ mockVoteID)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(delete("/api/v1/projects/" + mockProjectID + "/feedback/" + feedbackID + "/vote/"+ mockVoteID)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(delete("/api/v1/projects/" + mockProjectID + "/feedback/" + mockFeedbackID + "/vote/"+ voteID)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isInternalServerError());
    }


}