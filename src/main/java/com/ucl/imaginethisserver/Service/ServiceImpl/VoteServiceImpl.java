package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.DAO.VoteDao;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.InternalServerErrorException;
import java.util.List;
import java.util.UUID;

@Service
public class VoteServiceImpl implements VoteService {

    private final Logger logger = LoggerFactory.getLogger(VoteServiceImpl.class);

    private final VoteDao voteDao;

    @Autowired
    public VoteServiceImpl(VoteDao voteDao) {
        this.voteDao = voteDao;
    }

    @Override
    public List<Vote> getVotesForFeedback(String projectID, UUID feedbackID) {
        // check feedback id
        if (feedbackID == null) {
            logger.error("Error getting vote for feedback: feedback ID not provided");
            throw new IllegalArgumentException();
        }
        List<Vote> votes = voteDao.getVotesForFeedback(projectID, feedbackID);
        if (votes.size() > 0) {
            return votes;
        } else {
            logger.error("Cannot get Votes for Feedback " + feedbackID);
            throw new InternalServerErrorException();
        }
    }

    @Override
    public boolean voteFeedback(String projectID, UUID feedbackID, Vote vote) {
        if (vote.getVoteValue() != 1 && vote.getVoteValue() != -1) {
            logger.error("Vote value can only be 1 or -1");
            throw new IllegalArgumentException();
        }
        if (vote.getUserId() == null) {
            logger.error("Error creating new vote: UserID not provided.");
            throw new IllegalArgumentException();
        }
        // since the vote ID should be returned to the requester, generates the vote ID here if not present
        if (vote.getVoteId() == null) {
            UUID uuid = UUID.randomUUID();
            logger.info("Generating Vote ID: " + uuid);
            // set the vote ID
            vote.setVoteId(uuid);
        }
        logger.info("Getting feedbackID: " + feedbackID);
        vote.setFeedbackId(feedbackID);
        if (voteDao.voteFeedback(projectID, feedbackID, vote)) {
            return true;
        } else {
            logger.error("Error voting feedback " + feedbackID);
            throw new InternalServerErrorException();
        }
    }

    @Override
    public boolean updateVoteForFeedback(String projectID, UUID feedbackID, UUID voteID, Vote vote) {
        if (feedbackID == null || voteID == null) {
            logger.error("Error updating vote. Feedback ID or vote ID not provided.");
            throw new IllegalArgumentException();
        }
        if (vote.getVoteValue() != 1 && vote.getVoteValue() != -1) {
            logger.error("Vote value can only be 1 or -1");
            throw new IllegalArgumentException();
        }
        if (voteDao.updateVoteForFeedback(projectID, feedbackID, voteID, vote)) {
            return true;
        } else {
            logger.error("Error Updating vote " + voteID + "for feedback " + feedbackID);
            throw new InternalServerErrorException();
        }
    }

    @Override
    public boolean deleteVoteForFeedback(String projectID, UUID feedbackID, UUID voteID, Vote vote) {
        if (voteDao.deleteVoteForFeedback(projectID, feedbackID, voteID, vote)) {
            return true;
        } else {
            logger.error("Error Deleting vote " + voteID + "for feedback " + feedbackID);
            throw new InternalServerErrorException();
        }
    }
}
