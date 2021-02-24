package com.ucl.imaginethisserver.Service;

import com.ucl.imaginethisserver.DAO.FeedbackDao;
import com.ucl.imaginethisserver.DAO.ProjectDao;
import com.ucl.imaginethisserver.Mapper.FeedbackMapper;
import com.ucl.imaginethisserver.Mapper.ProjectMapper;
import com.ucl.imaginethisserver.Mapper.VoteMapper;
import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Model.Vote;
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

    static FeedbackDao mockFeedbackDao;
    static ProjectDao mockProjectDao;
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
        mockFeedbackDao = mock(FeedbackDao.class);
        mockProjectDao = mock(ProjectDao.class);

        // Prepare random feedbacks
        testFeedback = new Feedback();
        testFeedback.setFeedbackId(FEEDBACK_ID);
        testFeedback.setProjectId(FEEDBACK_PROJECT_ID);
        testFeedback.setUserId(FEEDBACK_USER_ID);
        testFeedback.setUserName(FEEDBACK_USERNAME);
        testFeedback.setText(FEEDBACK_TEXT);
        testFeedback.setTimestamp(FEEDBACK_TIMESTAMP);
        testFeedbacks = new ArrayList<>();
        testFeedbacks.add(testFeedback);

        // Stub method calls
        when(mockFeedbackDao.getAllFeedbacks("test")).thenReturn(testFeedbacks);
        when(mockFeedbackDao.getFeedbackByID("test", FEEDBACK_ID)).thenReturn(testFeedback);

        // Create FeedbackService with mocked DAO
        feedbackService = new FeedbackServiceImpl(mockFeedbackDao, mockProjectDao);
    }

    @Test
    void getAllFeedbacksTest() {
        List<Feedback> resultList = feedbackService.getAllFeedbacks("test");
        // Check size
        assertEquals(1, resultList.size());
        // Check content
        Feedback resultFeedback = resultList.get(0);
        assertEquals(resultFeedback.getFeedbackId(), FEEDBACK_ID);
        assertEquals(resultFeedback.getText(), FEEDBACK_TEXT);
        assertEquals(resultFeedback.getUserName(), FEEDBACK_USERNAME);
        assertEquals(resultFeedback.getProjectId(), FEEDBACK_PROJECT_ID);
        assertEquals(resultFeedback.getUserId(), FEEDBACK_USER_ID);
        assertEquals(resultFeedback.getTimestamp(), FEEDBACK_TIMESTAMP);
    }

    @Test
    void getFeedbackByIDTest() {
        Feedback resultFeedback = feedbackService.getFeedbackByID("test", FEEDBACK_ID);
        // Check content
        assertEquals(resultFeedback.getFeedbackId(), FEEDBACK_ID);
        assertEquals(resultFeedback.getText(), FEEDBACK_TEXT);
        assertEquals(resultFeedback.getUserName(), FEEDBACK_USERNAME);
        assertEquals(resultFeedback.getProjectId(), FEEDBACK_PROJECT_ID);
        assertEquals(resultFeedback.getUserId(), FEEDBACK_USER_ID);
        assertEquals(resultFeedback.getTimestamp(), FEEDBACK_TIMESTAMP);
    }


    @Test
    void addNewFeedbackTest(){
        UUID FEEDBACK_ID = UUID.fromString("4744b5d3-27cd-491d-a8f4-32d263327797");
        // Prepare random new feedbacks
        Feedback newFeedback = new Feedback();
        newFeedback.setFeedbackId(FEEDBACK_ID);
        newFeedback.setProjectId(FEEDBACK_PROJECT_ID);
        newFeedback.setUserId(FEEDBACK_USER_ID);
        newFeedback.setUserName(FEEDBACK_USERNAME);
        newFeedback.setText(FEEDBACK_TEXT);
        newFeedback.setTimestamp(FEEDBACK_TIMESTAMP);
        testFeedbacks.add(newFeedback);

        when(mockFeedbackDao.addNewFeedback(eq(FEEDBACK_PROJECT_ID), any(Feedback.class))).thenReturn(1);

        boolean result = feedbackService.addNewFeedback(FEEDBACK_PROJECT_ID, newFeedback);
        assertEquals(result, true);
    }

    @Test
    void updateFeedbackTest(){
        testFeedback.setText("Needs improvement!");
        when(mockFeedbackDao.getFeedbackByID(FEEDBACK_PROJECT_ID, FEEDBACK_ID)).thenReturn(testFeedback);
        when(mockFeedbackDao.updateFeedback(FEEDBACK_PROJECT_ID, FEEDBACK_ID, testFeedback)).thenReturn(1);

        boolean result = feedbackService.updateFeedback(FEEDBACK_PROJECT_ID, FEEDBACK_ID, testFeedback);
        assertEquals(result, true);
    }

    @Test
    void deleteFeedbackText(){
        when(mockFeedbackDao.deleteFeedback(FEEDBACK_PROJECT_ID, FEEDBACK_ID)).thenReturn(1);

        boolean result = feedbackService.deleteFeedback(FEEDBACK_PROJECT_ID, FEEDBACK_ID);
        assertEquals(result, true);
    }


}
