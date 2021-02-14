package com.ucl.imaginethisserver.DAO.DAOImpl;

import com.ucl.imaginethisserver.DAO.FeedbackDao;
import com.ucl.imaginethisserver.DAO.FeedbackDto;
import com.ucl.imaginethisserver.Mapper.FeedbackDynamicSqlSupport;
import com.ucl.imaginethisserver.Mapper.FeedbackMapper;
import com.ucl.imaginethisserver.Mapper.VoteDynamicSqlSupport;
import com.ucl.imaginethisserver.Mapper.VoteMapper;
import com.ucl.imaginethisserver.Model.Feedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

@Repository
public class FeedbackDaoImpl implements FeedbackDao {
    private final FeedbackMapper feedbackMapper;

    private final VoteMapper voteMapper;

    private final Logger logger = LoggerFactory.getLogger(FeedbackDaoImpl.class);

    @Autowired
    public FeedbackDaoImpl(FeedbackMapper feedbackMapper, VoteMapper voteMapper) {
        this.feedbackMapper = feedbackMapper;
        this.voteMapper = voteMapper;
    }

    @Override
    public List<FeedbackDto> getAllFeedbacksWithVotes(String projectID) {
        return feedbackMapper.getAllFeedbacksWithVotes(projectID);
    }

    @Override
    public List<Feedback> getAllFeedbacks(String projectID) {
        return feedbackMapper.select(c -> c
                .where(FeedbackDynamicSqlSupport.projectId, isEqualToWhenPresent(projectID)));
    }

    @Override
    public Feedback getFeedbackByID(String projectID, UUID feedbackID) {
        Optional<Feedback> optionalFeedback = feedbackMapper.selectOne(c -> c
                .where(FeedbackDynamicSqlSupport.projectId, isEqualToWhenPresent(projectID))
                .and(FeedbackDynamicSqlSupport.feedbackId, isEqualToWhenPresent(feedbackID))
        );
        return optionalFeedback.orElse(null);
    }

    @Override
    public int addNewFeedback(String projectID, Feedback feedback) {
        feedback.setFeedbackId(UUID.fromString(feedback.getFeedbackId().toString()));
        feedback.setUserId(UUID.fromString(feedback.getUserId().toString()));
        return feedbackMapper.insert(feedback);
    }

    @Override
    public int updateFeedback(String projectID, UUID feedbackID, Feedback feedback) {
        return feedbackMapper.update(c -> c
                .set(FeedbackDynamicSqlSupport.text).equalTo(feedback.getText())
                .set(FeedbackDynamicSqlSupport.timestamp).equalTo(System.currentTimeMillis())
                .where(FeedbackDynamicSqlSupport.feedbackId, isEqualTo(feedbackID))
                .and(FeedbackDynamicSqlSupport.projectId, isEqualTo(projectID))
        );
    }

    @Override
    public int deleteFeedback(String projectID, UUID feedbackID) {
        // first delete associated votes
        int voteDeletion = voteMapper.delete(c -> c
                .where(VoteDynamicSqlSupport.feedbackId, isEqualTo(feedbackID))
        );
        // then delete feedback
        int feedbackDeletion = feedbackMapper.deleteByPrimaryKey(feedbackID);
        if (feedbackDeletion != 1) {
            logger.warn("Delete feedback failed. vote deletion: " + voteDeletion +
                    ", feedback deletion: " + feedbackDeletion);
        }
        return feedbackDeletion;
    }
}
