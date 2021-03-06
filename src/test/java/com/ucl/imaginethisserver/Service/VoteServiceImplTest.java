package com.ucl.imaginethisserver.Service;

import com.ucl.imaginethisserver.DAO.FeedbackDao;
import com.ucl.imaginethisserver.DAO.ProjectDao;
import com.ucl.imaginethisserver.DAO.VoteDao;
import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Model.Project;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.ServiceImpl.FeedbackServiceImpl;
import com.ucl.imaginethisserver.Service.ServiceImpl.VoteServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VoteServiceImplTest {
    static FeedbackDao mockFeedbackDao;
    static ProjectDao mockProjectDao;
    static VoteDao mockVoteDao;
    static Feedback testFeedback;
    static Project testProject;
    static Vote testVote;
    static List<Vote> testVotes;
    static FeedbackServiceImpl feedbackService;
    static VoteServiceImpl voteService;
    static final String VOTE_PROJECT_ID = "MgWqYTZMdjG26oA1CxbWaE";
    static final UUID VOTE_FEEDBACK_ID = UUID.fromString("4744b5d3-27cd-491d-a8f4-32d263327796");
    static final UUID VOTE_ID = UUID.fromString("4744b5d3-27cd-491d-a8f4-32d263327797");
    static final UUID VOTE_USER_ID = UUID.fromString("2026b917-dddf-47a3-99ab-d413543504f3");
    static final long VOTE_TIMESTAMP = Long.parseLong("1612173537");
    static final Integer VOTE_VALUE = 1;
    @BeforeAll
    static void mockDAO() {
        mockFeedbackDao = mock(FeedbackDao.class);
        mockProjectDao = mock(ProjectDao.class);
        mockVoteDao = mock(VoteDao.class);

        // Prepare random vote
        testVote = new Vote();
        testVote.setVoteId(VOTE_ID);
        testVote.setVoteValue(VOTE_VALUE);
        testVote.setFeedbackId(VOTE_FEEDBACK_ID);
        testVote.setTimestamp(VOTE_TIMESTAMP);
        testVote.setUserId(VOTE_USER_ID);

        testVotes = new ArrayList<>();
        testVotes.add(testVote);

        // Prepare random feedbacks
        testFeedback = new Feedback();


        // Stub method calls
        when(mockVoteDao.getVotesForFeedback(VOTE_PROJECT_ID,VOTE_FEEDBACK_ID)).thenReturn(testVotes);
        when(mockProjectDao.getProjectByID(VOTE_PROJECT_ID)).thenReturn(testProject);

        // Create FeedbackService with mocked DAO
        feedbackService = new FeedbackServiceImpl(mockFeedbackDao, mockProjectDao);
        voteService = new VoteServiceImpl(mockVoteDao);
    }

    @Test
    void getVotesForFeedbackTest(){
        //the vote value might changed as updateVoteForFeedbackTest might execute first.
        testVote.setVoteValue(VOTE_VALUE);

        List<Vote> resultList = voteService.getVotesForFeedback(VOTE_PROJECT_ID,VOTE_FEEDBACK_ID);
        // Check size
        assertEquals(1, resultList.size());
        // Check content
        Vote resultVote = resultList.get(0);
        assertEquals(resultVote.getVoteId(), VOTE_ID);
        assertEquals(resultVote.getVoteValue(), VOTE_VALUE);
        assertEquals(resultVote.getFeedbackId(), VOTE_FEEDBACK_ID);
        assertEquals(resultVote.getUserId(), VOTE_USER_ID);
        assertEquals(resultVote.getTimestamp(), VOTE_TIMESTAMP);

    }

    @Test
    void voteFeedbackTest(){
        UUID VOTE_ID = UUID.fromString("4744b5d3-27cd-491d-a8f4-32d263327798");
        // Prepare random new vote
        Vote newVote = new Vote();
        newVote.setVoteId(VOTE_ID);
        newVote.setVoteValue(VOTE_VALUE);
        newVote.setFeedbackId(VOTE_FEEDBACK_ID);
        newVote.setTimestamp(VOTE_TIMESTAMP);
        newVote.setUserId(VOTE_USER_ID);

        when(mockVoteDao.voteFeedback(VOTE_PROJECT_ID, VOTE_FEEDBACK_ID, newVote)).thenReturn(true);

        boolean result = voteService.voteFeedback(VOTE_PROJECT_ID, VOTE_FEEDBACK_ID, newVote);
        assertEquals(result, true);

    }

    @Test
    void updateVoteForFeedbackTest(){
        testVote.setVoteValue(-1);
        when(mockFeedbackDao.getFeedbackByID(VOTE_PROJECT_ID, VOTE_FEEDBACK_ID)).thenReturn(testFeedback);
        when(mockVoteDao.updateVoteForFeedback(VOTE_PROJECT_ID, VOTE_FEEDBACK_ID, VOTE_ID, testVote)).thenReturn(true);

        boolean result = voteService.updateVoteForFeedback(VOTE_PROJECT_ID, VOTE_FEEDBACK_ID, VOTE_ID, testVote);
        assertEquals(result, true);
    }

    @Test
    void deleteVoteForFeedbackTest(){
        when(mockFeedbackDao.getFeedbackByID(VOTE_PROJECT_ID, VOTE_FEEDBACK_ID)).thenReturn(testFeedback);
        when(mockVoteDao.deleteVoteForFeedback(VOTE_PROJECT_ID, VOTE_FEEDBACK_ID, VOTE_ID, testVote)).thenReturn(true);

        boolean result = voteService.deleteVoteForFeedback(VOTE_PROJECT_ID, VOTE_FEEDBACK_ID, VOTE_ID, testVote);
        assertEquals(result, true);

    }

}
