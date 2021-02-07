package com.ucl.imaginethisserver.Model;

import com.google.gson.annotations.Expose;
import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

public class Vote {

    private UUID voteID;

    @Expose
    private final UUID feedbackID;

    @Expose
    @ApiModelProperty(example = "e348649c-2e03-49f3-a09a-95302510b07a")
    private final UUID userID;
    /**
     * vote can be either up vote or down vote
     * 1 indicates up vote and -1 indicates down vote
     */
    @Expose
    private int vote;

    @Expose
    @ApiModelProperty(example = "1611660815823")
    private long timestamp;

    public Vote(UUID voteID, UUID userID, UUID feedbackID, int vote, long timestamp) {
        assert vote == -1 || vote == 1 : "value of parameter 'vote' must be -1 or 1";
        this.voteID = voteID;
        this.feedbackID = feedbackID;
        this.userID = userID;
        this.vote = vote;
        this.timestamp = timestamp;
    }

    public void setVoteID(UUID voteID) {
        this.voteID = voteID;
    }

    public UUID getVoteID() {
        return voteID;
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

    public long getTimestamp() { return timestamp; }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public UUID getFeedbackID() {
        return feedbackID;
    }
}
