package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.CustomExceptions.ProjectNotFoundException;
import com.ucl.imaginethisserver.CustomExceptions.FeedbackNotFoundException;
import com.ucl.imaginethisserver.CustomExceptions.UpdateException;
import com.ucl.imaginethisserver.DAO.FeedbackDAO;
import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.FeedbackService;
import org.apache.ibatis.jdbc.Null;
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
        if (projectIDList == null) {
            projectIDList = feedbackDAO.getAllProjectID();
            System.out.println(projectIDList.size());
        }
        if (projectIDList.contains(projectID)) {
            return feedbackDAO.getAllFeedbacks(projectID);
        } else {
            throw new ProjectNotFoundException("Project Not Found");
        }
    }

    @Override
    public Feedback getFeedbackByID(String projectID, UUID feedbackID) {
        Feedback requested_feedback = feedbackDAO.getFeedbackByID(projectID, feedbackID);
        if (requested_feedback==null) {
            throw new FeedbackNotFoundException("Feedback Not Found");
        }else{
            return requested_feedback;
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
    public boolean voteFeedback(String projectID, UUID feedbackID, Vote vote) {
        Vote previousVoteByUser = feedbackDAO.getVoteByID(projectID, feedbackID, vote.getUserID());

        if(previousVoteByUser==null){
            //if user has not voted before, set this vote
            return feedbackDAO.voteFeedback(projectID, feedbackID, vote);
        }else if (previousVoteByUser.getVote()!=vote.getVote()){
           //if user has voted but different vote, replace the existing vote with new vote
            return feedbackDAO.changeVoteFeedback(projectID, feedbackID, vote);
        }else{
            //if user has voted same before.
            throw new UpdateException("Duplicate Vote");
        }
    }
}
