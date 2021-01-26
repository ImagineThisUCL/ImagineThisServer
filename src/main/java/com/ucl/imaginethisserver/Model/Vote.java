package com.ucl.imaginethisserver.Model;

import com.google.gson.annotations.Expose;
import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

public class Vote {

    @Expose
    @ApiModelProperty(example = "e348649c-2e03-49f3-a09a-95302510b07a")
    private final UUID userID;
    /**
     * vote can be either up vote or down vote
     * 1 indicates up vote and -1 indicates down vote
     */
    @Expose
    private int vote;

    public Vote(UUID userID, int vote) {
        assert vote == -1 || vote == 1 : "value of parameter 'vote' must be -1 or 1";
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
