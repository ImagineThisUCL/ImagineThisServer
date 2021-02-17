package com.ucl.imaginethisserver.DAO;

import com.ucl.imaginethisserver.Model.User;
import com.ucl.imaginethisserver.Model.Vote;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    /**
     * This method will get all votes for given user
     * @param userID ID of the user
     * @return a list of votes associated with that user
     */
    List<Vote> getAllVotesForUser(UUID userID);

    boolean createUser(User user);

    boolean updateUser(User user);
}
