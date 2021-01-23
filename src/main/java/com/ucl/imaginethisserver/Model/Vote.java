package com.ucl.imaginethisserver.Model;

import java.util.UUID;

public class Vote {

    private final UUID userID;
    /**
     * vote can be either up vote or down vote
     * 1 indicates up vote and -1 indicates down vote
     */
    private int vote;

    public Vote(UUID userID, int vote) {
        this.userID = userID;
        this.vote = vote;
    }

    public UUID getUserID() {
        return userID;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
