package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.DAO.FeedbackDao;
import com.ucl.imaginethisserver.DAO.FeedbackDto;
import com.ucl.imaginethisserver.DAO.ProjectDao;
import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Service.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    private final FeedbackDao feedbackDao;
    private final ProjectDao projectDao;

    @Autowired
    public FeedbackServiceImpl(FeedbackDao feedbackDao, ProjectDao projectDao) {
        this.feedbackDao = feedbackDao;
        this.projectDao = projectDao;
    }

    @Override
    public List<Feedback> getAllFeedbacks(String projectID) {
        return feedbackDao.getAllFeedbacks(projectID);
    }

    @Override
    public List<FeedbackDto> getFeedbacksWithVotes(String projectID) {
        return feedbackDao.getAllFeedbacksWithVotes(projectID).orElseThrow(NotFoundException::new);
    }

    @Override
    public Feedback getFeedbackByID(String projectID, UUID feedbackID){
        Feedback result = feedbackDao.getFeedbackByID(projectID, feedbackID);
        if (result != null) {
            return result;
        } else {
            // throw not found exception
            throw new NotFoundException("Feedback Not Found!");
        }
    }

    @Override
    public boolean addNewFeedback(String projectID, Feedback feedback) {
        // create new feedback object
        Feedback newFeedback = new Feedback();
        if (projectID != null) {
            newFeedback.setProjectId(projectID);
        }
        if (feedback.getFeedbackId() == null) {
            UUID uuid = UUID.randomUUID();
            logger.info("Generating new UUID for feedback: " + uuid);
            newFeedback.setFeedbackId(uuid);
        }
        if (feedback.getTimestamp() == null) {
            logger.info("Generating new timestamp");
            newFeedback.setTimestamp(System.currentTimeMillis());
        }
        // get payload from request body
        newFeedback.setUserId(UUID.fromString(feedback.getUserId().toString()));
        newFeedback.setUserName(feedback.getUserName());
        newFeedback.setText(feedback.getText());
        int result = feedbackDao.addNewFeedback(projectID, newFeedback);
        if (result != 0) {
            return true;
        } else {
            logger.error("Error adding new feedback!");
            throw new InternalServerErrorException();
        }
    }

    @Override
    public boolean updateFeedback(String projectID, UUID feedbackID, Feedback feedback) {
        if (feedback.getText().equals("")) {
            logger.error("Error Updating feedback: " + feedbackID + ", feedback text not provided");
            throw new InternalServerErrorException();
        }
        int result = feedbackDao.updateFeedback(projectID, feedbackID, feedback);
        if (result == 1) {
            return true;
        } else {
            logger.warn("Update feedback failed.");
            return false;
        }
    }

    @Override
    public boolean deleteFeedback(String projectID, UUID feedbackID) {
        // check if feedback ID provided
        if (feedbackID == null) {
            logger.error("Error Deleting feedback: feedback ID not provided");
            throw new InternalServerErrorException();
        }
        return feedbackDao.deleteFeedback(projectID, feedbackID) == 1;
    }
}
