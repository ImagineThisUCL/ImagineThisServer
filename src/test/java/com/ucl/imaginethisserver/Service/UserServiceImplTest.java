package com.ucl.imaginethisserver.Service;

import com.ucl.imaginethisserver.DAO.FeedbackDao;
import com.ucl.imaginethisserver.DAO.ProjectDao;
import com.ucl.imaginethisserver.DAO.UserDao;
import com.ucl.imaginethisserver.DAO.VoteDao;
import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Model.User;
import com.ucl.imaginethisserver.Service.ServiceImpl.FeedbackServiceImpl;
import com.ucl.imaginethisserver.Service.ServiceImpl.ProjectServiceImpl;
import com.ucl.imaginethisserver.Service.ServiceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UserServiceImplTest {
    static UserDao mockUserDao;
    static User testUser;
    static UserServiceImpl userService;
    static final UUID USER_ID = UUID.fromString("2026b917-dddf-47a3-99ab-d413543504f3");;
    static final String USER_NAME = "John Dow";
    static Vote testVote;
    static final String PROJECT_ID = "MgWqYTZMdjG26oA1CxbWaE";
    static final UUID VOTE_ID = UUID.fromString("f2366a7b-c2ca-40d5-939a-2f649411d258");
    static final int VOTE_VALUE = 1;
    static final UUID FEEDBACK_ID = UUID.fromString("250a8d14-55d9-4cb1-93e2-29cd4ebda98b");


    static List<User> testUsers;
    static List<Vote> testVotes;

    @BeforeAll
    static void mockDAO() {
        mockUserDao = mock(UserDao.class);

        // Prepare random feedbacks
        testUser = new User();
        testUser.setUserId(USER_ID);
        testUser.setUserName(USER_NAME);
        testUsers = new ArrayList<>();
        testUsers.add(testUser);

        // Create UserService with mocked DAO
        userService = new UserServiceImpl(mockUserDao);

        testVote = new Vote();
        testVote.setFeedbackId(FEEDBACK_ID);
        testVote.setVoteId(VOTE_ID);
        testVote.setUserId(USER_ID);
        testVote.setTimestamp(System.currentTimeMillis());
        testVote.setVoteValue(VOTE_VALUE);
        testVotes = new ArrayList<>();
        testVotes.add(testVote);
    }

    @Test
    void getAllVotesForUserTest() {
        when(mockUserDao.getAllVotesForUser(USER_ID)).thenReturn(testVotes);
        List<Vote> resultList = userService.getAllVotesForUser(USER_ID);
        assertEquals(1, resultList.size());
        Vote resultVote = resultList.get(0);
        assertEquals(resultVote.getVoteId(), VOTE_ID);
        assertEquals(resultVote.getVoteValue(), VOTE_VALUE);
        assertEquals(resultVote.getFeedbackId(), FEEDBACK_ID);
        assertEquals(resultVote.getUserId(), USER_ID);
    }

    @Test
    void createUserTest(){
        when(mockUserDao.createUser(testUser)).thenReturn(true);

        UUID resultID = userService.createUser(testUser);
        assertEquals(USER_ID, resultID);
    }

    @Test
    void updateUserTest(){
        when(mockUserDao.updateUser(testUser)).thenReturn(true);
        Boolean result = userService.updateUser(testUser);
        assertEquals(true, result);
    }

}
