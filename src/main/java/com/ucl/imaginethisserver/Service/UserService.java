package com.ucl.imaginethisserver.Service;

import com.ucl.imaginethisserver.Model.User;
import com.ucl.imaginethisserver.Model.Vote;

import java.util.List;
import java.util.UUID;

public interface UserService {
    /**
     * This method will get all votes for given user
     * @param userID ID of the user
     * @return a list of votes associated with that user
     */
    List<Vote> getAllVotesForUser(UUID userID);

    UUID createUser(User user);

    boolean updateUser(User user);
}
