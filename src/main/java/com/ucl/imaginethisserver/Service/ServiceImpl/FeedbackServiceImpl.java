package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.CustomExceptions.NotFoundException;
import com.ucl.imaginethisserver.CustomExceptions.ProjectNotFoundException;
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

    private List<String> projectIDList;

    @Override
    public List<Feedback> getAllFeedbacks(String projectID) {
        // TODO: add cache server
        if (projectExist(projectID)) {
            return feedbackDAO.getAllFeedbacks(projectID);
        } else {
            throw new ProjectNotFoundException("Project Not Found");
        }
    }

    @Override
    public Feedback getFeedbackByID(String projectID, UUID feedbackID) {
        List<Feedback> feedbackList;
        if (projectExist(projectID)) {
            projectIDList = feedbackDAO.getAllProjectID();
            feedbackList = feedbackDAO.getAllFeedbacks(projectID);
            // check if feedback exist
            for (Feedback f :
                    feedbackList) {
                if (f.getFeedbackID().equals(feedbackID)) {
                    return f;
                } else {
                    throw new NotFoundException("Feedback Not Found");
                }
            }
        } else {
            throw new ProjectNotFoundException("Project Not Found");
        }
        return null;
    }

    @Override
    public boolean addNewFeedback(String projectID, Feedback feedback) {
        return feedbackDAO.addNewFeedback(projectID, feedback);
    }

    @Override
    public boolean voteFeedback(String projectID, UUID feedbackID, Vote vote) {
        return feedbackDAO.voteFeedback(projectID, feedbackID, vote);
    }

    private boolean projectExist(String projectID) {
        if (projectIDList == null) {
            projectIDList = feedbackDAO.getAllProjectID();
        }
        return projectIDList.contains(projectID);
    }
}
