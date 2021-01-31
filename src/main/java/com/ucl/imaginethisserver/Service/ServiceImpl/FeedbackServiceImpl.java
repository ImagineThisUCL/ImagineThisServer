package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.Entity.Feedback;
import com.ucl.imaginethisserver.Mapper.FeedbackMapper;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackMapper feedbackMapper;

    @Autowired
    public FeedbackServiceImpl(FeedbackMapper feedbackMapper) {
        this.feedbackMapper = feedbackMapper;
    }


    @Override
    public List<Feedback> getAllFeedbacks(String projectID) {
        return feedbackMapper.selectAll();
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
