package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.Controller.FeedbackController;
import com.ucl.imaginethisserver.CustomExceptions.NotFoundException;
import com.ucl.imaginethisserver.CustomExceptions.ProjectNotFoundException;
import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Model.Vote;
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
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeedbackService.class)
class FeedbackServiceImplTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService service;

    private List<Feedback> mockFeedbackList;

    private String mockProjectID = "MgWqYTZMdjG26oA1CxbWaE";

    private UUID mockFeedbackID = UUID.fromString("250a8d14-55d9-4cb1-93e2-29cd4ebda98b");

    private UUID mockUserID = UUID.fromString("f2366a7b-c2ca-40d5-939a-2f649411d257");

    private UUID mockVoteID = UUID.fromString("f2366a7b-c2ca-40d5-939a-2f649411d258");

    private String mockUserName = "John Dow";

    private Feedback mockFeedback= new Feedback(mockFeedbackID,
            mockProjectID, mockUserID, mockUserName, 1610955210, "Great prototype!");

    private Vote mockVote = new Vote(mockVoteID,
            mockUserID, mockFeedbackID, 1, 1610955210);


    @BeforeEach
    void setUp() {
        mockFeedbackList = new ArrayList<>();
        mockFeedbackList.add(mockFeedback);
    }

    @Test
    void getAllFeedbacks() {
        List<Feedback> receivedFeedback = service.getAllFeedbacks(mockProjectID);

        assertEquals(0, receivedFeedback.size());

    }

    @Test
    void getFeedbackByID() {
        Feedback receivedFeedback = service.getFeedbackByID(mockProjectID,mockFeedbackID);

        assertEquals(null, receivedFeedback);
    }

    @Test
    void addNewFeedback() {
        boolean status = service.addNewFeedback(mockProjectID, mockFeedback);

        if(status) {
            Feedback receiveFeedback = service.getFeedbackByID(mockProjectID, mockFeedbackID);
            assertEquals(mockFeedback, receiveFeedback);
        }

    }

    @Test
    void voteFeedback() {
        UUID status = service.voteFeedback(mockFeedbackID, mockVote);

        List<Vote> receiveVote = service.getVotesByID(mockFeedbackID);

        if(status!=null) {
            assertEquals(receiveVote, hasItems(mockVote));
        }

        Vote mockVoteUpdate = new Vote(mockVoteID,
                mockUserID, mockFeedbackID, -1, 1610955210);

        UUID status2 = service.voteFeedback(mockFeedbackID, mockVoteUpdate);

        List<Vote> receiveVoteUpdate = service.getVotesByID(mockFeedbackID);

        if(status2!=null) {
            assertEquals(receiveVoteUpdate, hasItems(mockVoteUpdate));
        }


        boolean status3 = service.deleteVote(mockFeedbackID);

        List<Vote> receiveVoteDelete = service.getVotesByID(mockFeedbackID);

        if(status3) {
            assertEquals(receiveVoteDelete, not(hasItems(mockVoteUpdate)));
        }

    }
}