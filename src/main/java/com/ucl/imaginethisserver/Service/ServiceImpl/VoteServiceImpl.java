package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.CustomExceptions.InternalServerErrorException;
import com.ucl.imaginethisserver.Mapper.VoteDynamicSqlSupport;
import com.ucl.imaginethisserver.Mapper.VoteMapper;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Service
public class VoteServiceImpl implements VoteService {

    private final Logger logger = LoggerFactory.getLogger(VoteServiceImpl.class);

    private final VoteMapper voteMapper;

    @Autowired
    public VoteServiceImpl(VoteMapper voteMapper) {
        this.voteMapper = voteMapper;
    }

    @Override
    public List<Vote> getVotesForFeedback(String projectID, UUID feedbackID) {
        List<Vote> votes = voteMapper.select(c -> c
                .where(VoteDynamicSqlSupport.feedbackId, isEqualTo(feedbackID)));
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
            throw new InternalServerErrorException();
        }
        if (vote.getUserId() == null) {
            logger.error("Error creating new vote: UserID not provided.");
            throw new InternalServerErrorException();
        }
        Vote newVote = new Vote();
        UUID voteID = UUID.randomUUID();
        logger.info("Generating new vote for feedback " + feedbackID);
        newVote.setVoteId(voteID);
        newVote.setFeedbackId(feedbackID);
        newVote.setUserId(vote.getUserId());
        newVote.setTimestamp(System.currentTimeMillis());
        int result = voteMapper.insert(newVote);
        return result != 0;
    }

    @Override
    public boolean updateVoteForFeedback(String projectID, UUID feedbackID, UUID voteID, Vote vote) {
        if (vote.getVoteValue() != 1 && vote.getVoteValue() != -1) {
            logger.error("Vote value can only be 1 or -1");
            throw new InternalServerErrorException();
        }
        int result = voteMapper.update(c -> c
                .set(VoteDynamicSqlSupport.voteValue).equalTo(vote.getVoteValue())
                .where(VoteDynamicSqlSupport.feedbackId, isEqualTo(feedbackID))
                .and(VoteDynamicSqlSupport.voteId, isEqualTo(voteID)));
        if (result == 1) {
            return true;
        } else {
            logger.warn("Update vote failed.");
            return false;
        }
    }

    @Override
    public boolean deleteVoteForFeedback(String projectID, UUID feedbackID, UUID voteID, Vote vote) {
        int result = voteMapper.deleteByPrimaryKey(voteID);
        if (result == 1) {
            return true;
        } else {
            logger.warn("Delete vote failed.");
            return false;
        }
    }
}
