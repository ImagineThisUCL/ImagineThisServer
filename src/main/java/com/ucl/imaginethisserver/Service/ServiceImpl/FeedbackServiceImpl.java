package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.CustomExceptions.InternalError;
import com.ucl.imaginethisserver.CustomExceptions.NotFoundException;
import com.ucl.imaginethisserver.CustomExceptions.ProjectNotFoundException;
import com.ucl.imaginethisserver.CustomExceptions.FeedbackNotFoundException;
import com.ucl.imaginethisserver.CustomExceptions.UpdateException;
import com.ucl.imaginethisserver.DAO.FeedbackDAO;
import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.FeedbackService;
import org.apache.ibatis.jdbc.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

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
                if (f.getFeedbackID().toString().equals(feedbackID.toString())) {
                    logger.info("Feedback " + feedbackID + " found.");
                    return feedbackDAO.getFeedbackByID(projectID, feedbackID);
                }
            }
            logger.error("Feedback " + feedbackID + " not found.", new NotFoundException("Feedback Not Found"));
        } else {
            logger.error("Project " + projectID + " not found.", new NotFoundException("Project Not Found"));
        }
        return null;
    }

    @Override
    public boolean addNewFeedback(String projectID, Feedback feedback) {
        boolean success = feedbackDAO.addNewFeedback(projectID, feedback);
        if(success){
            return success;
        }else{
            throw new UpdateException("Feedback Not Added");
        }
    }


    @Override
    public UUID voteFeedback(UUID feedbackID, Vote vote) {
        UUID uuid = UUID.randomUUID();
        if (vote.getVoteID() == null) {
            logger.info("Generating random voteID: " + uuid);
            vote.setVoteID(uuid);
        } else {
            uuid = vote.getVoteID();
        }
        try {
            feedbackDAO.addVoteByID(feedbackID, vote);
            return uuid;
        } catch (Exception e) {
            // log out error message
            logger.error(e.getMessage());
            // throw new runtime exception to make Spring return 500 error
            throw new InternalError();
        }
    }

    @Override
    public List<Vote> getVotesByUserID(UUID userID) {
        try {
            return feedbackDAO.getVotesByUserID(userID);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new InternalError();
        }
    }

    @Override
    public boolean deleteVote(UUID voteID) {
        try {
            return feedbackDAO.deleteVoteByID(voteID);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new InternalError();
        }
    }

    private boolean projectExist(String projectID) {
        if (projectIDList == null) {
            projectIDList = feedbackDAO.getAllProjectID();
        }
        return projectIDList.contains(projectID);
    }

}
