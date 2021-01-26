package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.FeedbackService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Override
    public List<Feedback> getAllFeedbacks(String projectID) {
        // mock data
        List<Feedback> feedbacks = new ArrayList<>();
        feedbacks.add(new Feedback(UUID.randomUUID(), "MgWqYTZMdjG26oA1CxbWaE", UUID.randomUUID(),
                "John", new Timestamp(System.currentTimeMillis()), "Good prototype!"));
        feedbacks.add(new Feedback(UUID.randomUUID(), "MgWqYTZMdjG26oA1CxbWaE", UUID.randomUUID(),
                "Elena", new Timestamp(System.currentTimeMillis()), "I don't like the background color of the theme. Too dark."));
        return feedbacks;
    }

    @Override
    public Feedback getFeedbackByID(String projectID, UUID feedbackID) {
        return new Feedback(UUID.randomUUID(), projectID, feedbackID,
                "John", new Timestamp(System.currentTimeMillis()), "Good prototype!");
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
