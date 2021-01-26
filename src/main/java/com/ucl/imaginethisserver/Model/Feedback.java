package com.ucl.imaginethisserver.Model;

import com.google.gson.annotations.Expose;

import java.sql.Timestamp;
import java.util.UUID;

public class Feedback {
    @Expose
    private final UUID feedbackID;
    @Expose
    private final UUID userID;
    @Expose
    private final String projectID;
    @Expose
    private int upvotes;
    @Expose
    private int downvotes;
    @Expose
    private String userName;
    @Expose
    private Timestamp timestamp;
    @Expose
    private String text;

    public Feedback(UUID feedbackID, String projectID, int upvotes, int downvotes, UUID userID, String userName, Timestamp timestamp, String text) {
        this.feedbackID = feedbackID;
        this.projectID = projectID;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.userID = userID;
        this.userName = userName;
        this.timestamp = timestamp;
        this.text = text;
    }

    public Feedback(UUID feedbackID, String projectID, UUID userID, String userName, Timestamp timestamp, String text) {
        this(feedbackID, projectID, 0, 0, userID, userName, timestamp, text);
    }

    public UUID getFeedbackID() {
        return feedbackID;
    }

    public UUID getUserID() {
        return userID;
    }

    public String getProjectID() {
        return projectID;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public String getUserName() {
        return userName;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setText(String text) {
        this.text = text;
    }
}
