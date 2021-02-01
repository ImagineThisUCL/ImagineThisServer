package com.ucl.imaginethisserver.Service;

import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.DAO.DAOImpl.MockFeedbackRepo;
import com.ucl.imaginethisserver.Service.ServiceImpl.FeedbackServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.lang.AssertionError;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FeedbackServiceImplTest {

    static MockFeedbackRepo mockFeedback;
    static Feedback testFeedback;
    static List<Feedback> testFeedbacks;
    static FeedbackServiceImpl feedbackService;
    static final String FEEDBACK_TEXT = "Good prototype!";
    static final String FEEDBACK_USERNAME = "John";
    static final String FEEDBACK_PROJECT_ID = "MgWqYTZMdjG26oA1CxbWaE";
    static final UUID FEEDBACK_ID = UUID.fromString("4744b5d3-27cd-491d-a8f4-32d263327796");
    static final UUID FEEDBACK_USER_ID = UUID.fromString("2026b917-dddf-47a3-99ab-d413543504f3");
    static final long FEEDBACK_TIMESTAMP = Long.parseLong("1612173537");

    @BeforeAll
    static void mockDAO() {
        mockFeedback = mock(MockFeedbackRepo.class);

        // Prepare random feedbacks
        testFeedback = new Feedback(FEEDBACK_ID, FEEDBACK_PROJECT_ID, FEEDBACK_USER_ID, FEEDBACK_USERNAME, FEEDBACK_TIMESTAMP, FEEDBACK_TEXT);
        testFeedbacks = new ArrayList<>();
        testFeedbacks.add(testFeedback);

        // Stub method calls
        when(mockFeedback.getAllFeedbacks("test")).thenReturn(testFeedbacks);
        when(mockFeedback.getFeedbackByID("test", FEEDBACK_ID)).thenReturn(testFeedback);

        // Create FeedbackService with mocked DAO
        feedbackService = new FeedbackServiceImpl(mockFeedback);
    }

    @Test
    void getAllFeedbacksTest() {
        List<Feedback> resultList = feedbackService.getAllFeedbacks("test");
        // Check size
        assertEquals(1, resultList.size());
        // Check content
        Feedback resultFeedback = resultList.get(0);
        assertEquals(resultFeedback.getText(), FEEDBACK_TEXT);
        assertEquals(resultFeedback.getUserName(), FEEDBACK_USERNAME);
        assertEquals(resultFeedback.getProjectID(), FEEDBACK_PROJECT_ID);
        assertEquals(resultFeedback.getUserID(), FEEDBACK_USER_ID);
        assertEquals(resultFeedback.getTimestamp(), FEEDBACK_TIMESTAMP);
    }

    @Test
    void getFeedbackByIDTest() {
        Feedback resultFeedback = feedbackService.getFeedbackByID("test", FEEDBACK_ID);
        // Check content
        assertEquals(resultFeedback.getFeedbackID(), FEEDBACK_ID);
        assertEquals(resultFeedback.getText(), FEEDBACK_TEXT);
        assertEquals(resultFeedback.getUserName(), FEEDBACK_USERNAME);
        assertEquals(resultFeedback.getProjectID(), FEEDBACK_PROJECT_ID);
        assertEquals(resultFeedback.getUserID(), FEEDBACK_USER_ID);
        assertEquals(resultFeedback.getTimestamp(), FEEDBACK_TIMESTAMP);
    }

    @Test
    void addNewFeedbackTest() {
        // Check method addNewFeedback is called on DAO class
        feedbackService.addNewFeedback(FEEDBACK_PROJECT_ID, testFeedback);
        verify(mockFeedback).addNewFeedback(FEEDBACK_PROJECT_ID, testFeedback);
    }

    @Test
    void voteFeedbackTest() {
        // Check method voteFeedback is called on DAO class
        Vote testVote = new Vote(UUID.randomUUID(), 1);
        feedbackService.voteFeedback(FEEDBACK_PROJECT_ID, FEEDBACK_ID, testVote);
        verify(mockFeedback).voteFeedback(FEEDBACK_PROJECT_ID, FEEDBACK_ID, testVote);
    }

    @Test
    void voteIncorrectValueTest() {
        // Check incorrect vote value is captured
        assertThrows(AssertionError.class, () -> new Vote(UUID.randomUUID(), 0));
        assertThrows(AssertionError.class, () -> new Vote(UUID.randomUUID(), -2));
        assertThrows(AssertionError.class, () -> new Vote(UUID.randomUUID(), 2));
    }

}
