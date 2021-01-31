package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.DAO.FeedbackDAO;
import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.FeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackDAO feedbackDAO;

    @Override
    public List<Feedback> getAllFeedbacks(String projectID) {
        return feedbackDAO.getAllFeedbacks(projectID);
    }

    @Override
    public Feedback getFeedbackByID(String projectID, UUID feedbackID) {
        return feedbackDAO.getFeedbackByID(projectID, feedbackID);
    }

    @Override
    public boolean addNewFeedback(String projectID, Feedback feedback) {
        return feedbackDAO.addNewFeedback(projectID, feedback);
    }

    @Override
    public boolean voteFeedback(String projectID, UUID feedbackID, Vote vote) {
        return feedbackDAO.voteFeedback(projectID, feedbackID, vote);
    }
}
