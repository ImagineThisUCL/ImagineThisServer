package com.ucl.imaginethisserver.Model;

import java.sql.Timestamp;
import java.util.UUID;

public class Feedback {
    private final UUID feedbackID;
    private final UUID userID;
    private final String projectID;
    private int upvotes;
    private int downvotes;
    private String userName;
    private Timestamp timestamp;
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
