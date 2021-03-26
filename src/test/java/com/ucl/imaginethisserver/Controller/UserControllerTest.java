package com.ucl.imaginethisserver.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucl.imaginethisserver.Model.User;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    private List<User> mockUserList;

    private UUID mockUserID = UUID.fromString("f2366a7b-c2ca-40d5-939a-2f649411d257");

    private String mockUserName = "John Dow";

    private User mockUser;

    private List<Vote> mockVoteList;

    private Vote mockVote;

    private final UUID mockFeedbackID = UUID.fromString("250a8d14-55d9-4cb1-93e2-29cd4ebda98b");

    private final UUID mockVoteID = UUID.fromString("f2366a7b-c2ca-40d5-939a-2f649411d258");

    private final int mockVoteValue = 1;


    @BeforeEach
    void setUp() {
        /*
         * by default the gson was configured to exclude fields that are not annotated as Expose
         * global config can be found in application.properties
         * Thus create a new mockMvc that uses a custom gson
         * */
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(service))
                .setMessageConverters(new GsonHttpMessageConverter())
                .build();
        mockUser = new User();
        mockUser.setUserId(mockUserID);
        mockUser.setUserName(mockUserName);
        mockUserList = new ArrayList<>();
        mockUserList.add(mockUser);
        mockVoteList = new ArrayList<>();

        mockVote = new Vote();
        mockVote.setFeedbackId(mockFeedbackID);
        mockVote.setVoteId(mockVoteID);
        mockVote.setUserId(mockUserID);
        mockVote.setTimestamp(System.currentTimeMillis());
        mockVote.setVoteValue(mockVoteValue);
        mockVoteList = new ArrayList<>();
        mockVoteList.add(mockVote);


    }

    /*
     * The following tests will test the createNewUser method
     * */

    @Test
    void givenUser_whenCreateNewUser_thenReturnSuccess() throws Exception{
        User newUser = new User();
        newUser.setUserId(UUID.fromString("f2366a7b-c2ca-40d5-939a-2f649411d258"));
        newUser.setUserName("Max Dow");

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(newUser);

        given(service.createUser(ArgumentMatchers.any(User.class))).willReturn((UUID) ArgumentMatchers.any(Object.class));

        mockMvc.perform(post("/api/v1/users/")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk());
    }

    /*
     * The following tests will test the updateUser method
     * */

    @Test
    void givenAnyUserID_whenUpdateUser_thenReturnJsonArray() throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(mockUser);

        given(service.updateUser(ArgumentMatchers.any(User.class))).willReturn(true);

        mockMvc.perform(patch("/api/v1/users/" + mockUserID)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk());
    }

    /*
     * The following tests will test the getVotesByUserID method
     * */

    @Test
    void givenValidUserID_whenGetVotesByUserID_thenReturnJsonArray() throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(mockUser);

        given(service.getAllVotesForUser(mockUserID)).willReturn(mockVoteList);

        mockMvc.perform(get("/api/v1/users/" + mockUserID + "/votes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }


}
