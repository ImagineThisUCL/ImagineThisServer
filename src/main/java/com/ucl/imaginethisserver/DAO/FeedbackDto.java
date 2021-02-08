package com.ucl.imaginethisserver.DAO;

import com.ucl.imaginethisserver.Model.Feedback;

public class FeedbackDto extends Feedback {
    private int upvotes;

    private int downvotes;

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }
}