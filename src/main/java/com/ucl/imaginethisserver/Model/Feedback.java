package com.ucl.imaginethisserver.Model;

import com.google.gson.annotations.Expose;
import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

public class Feedback {
    @Expose
    @ApiModelProperty(example = "cb791e97-a402-4174-95ea-dab2c3f06b25")
    private UUID feedbackID;
    @Expose
    @ApiModelProperty(example = "bd96ccc0-eeff-48e8-8b4e-652675dbc9a2")
    private UUID userID;
    @Expose
    @ApiModelProperty(example = "MgWqYTZMdjG26oA1CxbWaE")
    private String projectID;
    @Expose
    private int upvotes;
    @Expose
    private int downvotes;
    @Expose
    private String userName;
    @Expose
    @ApiModelProperty(example = "1611660815823")
    private long timestamp;
    @Expose
    private String text;

    public Feedback() {

    }

    public Feedback(UUID feedbackID, String projectID, int upvotes, int downvotes, UUID userID, String userName, long timestamp, String text) {
        this.feedbackID = feedbackID;
        this.projectID = projectID;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.userID = userID;
        this.userName = userName;
        this.timestamp = timestamp;
        this.text = text;
    }

    public Feedback(UUID feedbackID, String projectID, UUID userID, String userName, long timestamp, String text) {
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

    public long getTimestamp() {
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

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setText(String text) {
        this.text = text;
    }
}
