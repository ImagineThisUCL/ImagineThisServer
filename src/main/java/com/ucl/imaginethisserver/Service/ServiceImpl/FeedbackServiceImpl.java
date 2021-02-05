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
        boolean success = feedbackDAO.addNewFeedback(projectID, feedback);
        if(success){
            return success;
        }else{
            throw new UpdateException("Feedback Not Added");
        }
    }


    @Override
    public boolean voteFeedback(UUID feedbackID, Vote vote) {
        if (vote.getVoteID() == null) {
            vote.setVoteID(UUID.randomUUID());
        }
        try {
            feedbackDAO.addVoteByID(feedbackID, vote);
        } catch (Exception e) {
            // log out error message
            logger.error(e.getMessage());
            // throw new runtime exception to make Spring return 500 error
            throw new InternalError();
        }
        return feedbackDAO.addVoteByID(feedbackID, vote);
    }

    public boolean deleteVote(Vote vote) {
        return feedbackDAO.deleteVoteByID(vote.getVoteID());
    }

    public boolean addVote(UUID feedbackID, Vote vote) {
        return feedbackDAO.addVoteByID(feedbackID, vote);
    }

    public boolean updateVote(UUID feedbackID, Vote prev_vote, Vote new_vote) {
        boolean delete_ops = deleteVote(prev_vote);
        if(delete_ops){
            return feedbackDAO.addVoteByID(feedbackID, new_vote);
        }else{
            throw new UpdateException("Update Failed");
        }
    }


    private boolean projectExist(String projectID) {
        if (projectIDList == null) {
            projectIDList = feedbackDAO.getAllProjectID();
        }
        return projectIDList.contains(projectID);
    }

}
