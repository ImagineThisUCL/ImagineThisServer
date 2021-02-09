package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.CustomExceptions.InternalServerErrorException;
import com.ucl.imaginethisserver.CustomExceptions.NotFoundException;
import com.ucl.imaginethisserver.DAO.FeedbackDao;
import com.ucl.imaginethisserver.DAO.FeedbackDto;
import com.ucl.imaginethisserver.Mapper.FeedbackDynamicSqlSupport;
import com.ucl.imaginethisserver.Mapper.VoteDynamicSqlSupport;
import com.ucl.imaginethisserver.Mapper.VoteMapper;
import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Mapper.FeedbackMapper;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.FeedbackService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    private final FeedbackMapper feedbackMapper;

    private final VoteMapper voteMapper;

    private final FeedbackDao feedbackDao;

    @Autowired
    public FeedbackServiceImpl(FeedbackMapper feedbackMapper, VoteMapper voteMapper, FeedbackDao feedbackDao) {
        this.feedbackMapper = feedbackMapper;
        this.voteMapper = voteMapper;
        this.feedbackDao = feedbackDao;
    }

    @Override
    public List<Feedback> getAllFeedbacks(String projectID) {
        return feedbackMapper.select(c -> c
                .where(FeedbackDynamicSqlSupport.projectId, isEqualToWhenPresent(projectID))
        );
    }

    @Override
    public List<FeedbackDto> getFeedbacksWithVotes(String projectID) {
        return feedbackDao.getAllFeedbacksWithVotes(projectID);
    }

    @Override
    public Feedback getFeedbackByID(String projectID, UUID feedbackID){
        List<Feedback> resultList = feedbackMapper.select(c -> c
                .where(FeedbackDynamicSqlSupport.projectId, isEqualToWhenPresent(projectID))
                .and(FeedbackDynamicSqlSupport.feedbackId, isEqualToWhenPresent(feedbackID))
        );
        if (resultList.size() == 1) {
            return resultList.get(0);
        } else {
            // throw not found exception
            throw new NotFoundException("Feedback Not Found!");
        }
    }

    @Override
    public boolean addNewFeedback(String projectID, Feedback feedback) {
        if (feedback.getProjectId() == null) {
            feedback.setProjectId(projectID);
        }
        if (feedback.getFeedbackId() == null) {
            UUID uuid = UUID.randomUUID();
            logger.info("Generating new UUID for feedback: " + uuid);
            feedback.setFeedbackId(uuid);
        }
        int result = feedbackMapper.insert(feedback);
        if (result != 0) {
            return true;
        } else {
            logger.error("Error adding new feedback!");
            throw new InternalServerErrorException();
        }
    }
}
