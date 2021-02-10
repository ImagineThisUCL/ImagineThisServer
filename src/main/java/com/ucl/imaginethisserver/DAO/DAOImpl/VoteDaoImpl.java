package com.ucl.imaginethisserver.DAO.DAOImpl;

import com.ucl.imaginethisserver.DAO.VoteDao;
import com.ucl.imaginethisserver.Mapper.VoteDynamicSqlSupport;
import com.ucl.imaginethisserver.Mapper.VoteMapper;
import com.ucl.imaginethisserver.Model.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Repository
public class VoteDaoImpl implements VoteDao {
    private final VoteMapper voteMapper;

    private final Logger logger = LoggerFactory.getLogger(VoteDaoImpl.class);

    @Autowired
    public VoteDaoImpl(VoteMapper voteMapper) {
        this.voteMapper = voteMapper;
    }

    @Override
    public List<Vote> getVotesForFeedback(String projectID, UUID feedbackID) {
        return voteMapper.select(c -> c
                .where(VoteDynamicSqlSupport.feedbackId, isEqualTo(feedbackID))
        );
    }

    @Override
    public boolean voteFeedback(String projectID, UUID feedbackID, Vote vote) {
        Vote newVote = new Vote();
        UUID voteID = UUID.randomUUID();
        logger.info("Generating new vote for feedback " + feedbackID);
        newVote.setVoteId(voteID);
        newVote.setFeedbackId(feedbackID);
        newVote.setUserId(vote.getUserId());
        newVote.setTimestamp(System.currentTimeMillis());
        return voteMapper.insert(newVote) != 0;
    }

    @Override
    public boolean updateVoteForFeedback(String projectID, UUID feedbackID, UUID voteID, Vote vote) {
        return voteMapper.update(c -> c
                .set(VoteDynamicSqlSupport.voteValue).equalTo(vote.getVoteValue())
                .set(VoteDynamicSqlSupport.timestamp).equalTo(System.currentTimeMillis())
                .where(VoteDynamicSqlSupport.feedbackId, isEqualTo(feedbackID))
                .and(VoteDynamicSqlSupport.voteId, isEqualTo(voteID))
        ) != 0;
    }

    @Override
    public boolean deleteVoteForFeedback(String projectID, UUID feedbackID, UUID voteID, Vote vote) {
        return voteMapper.deleteByPrimaryKey(voteID) != 0;
    }
}
