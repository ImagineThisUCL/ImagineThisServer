package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.FeedbackService;

import java.util.List;
import java.util.UUID;

public class FeedbackServiceImpl implements FeedbackService {
    @Override
    public List<Feedback> getAllFeedbacks(String projectID) {
        return null;
    }

    @Override
    public Feedback getFeedbackByID(String projectID, UUID feedbackID) {
        return null;
    }

    @Override
    public boolean addNewFeedback(String projectID, Feedback feedback) {
        return false;
    }

    @Override
    public boolean voteFeedback(String projectID, UUID feedbackID, Vote vote) {
        return false;
    }
}
