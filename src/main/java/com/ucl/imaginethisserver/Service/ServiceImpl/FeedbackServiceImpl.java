package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.CustomExceptions.*;
import com.ucl.imaginethisserver.CustomExceptions.InternalError;
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
            logger.error("Feedback " + feedbackID + " not found.");
            throw new NotFoundException("Feedback Not Found");
        } else {
            logger.error("Project " + projectID + " not found.");
            throw new NotFoundException("Project Not Found");
        }
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
        // check vote value
        if (vote.getVote() != 1 && vote.getVote() != -1) {
            logger.error("Invalid request: the value of vote can only be 1 or -1");
            throw new BadRequestException();
        }
        // auto generating timestamp and vite ID if not provided
        if (vote.getVoteID() == null) {
            logger.info("Generating random voteID: " + uuid);
            vote.setVoteID(uuid);
        } else {
            uuid = vote.getVoteID();
        }
        if (vote.getTimestamp() == 0) {
            logger.info("Generating timestamp for vote " + uuid);
            vote.setTimestamp(System.currentTimeMillis());
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

    @Override
    public boolean updateVote(UUID voteID, int value) {
        try {
            return feedbackDAO.updateVoteByID(voteID, value);
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
