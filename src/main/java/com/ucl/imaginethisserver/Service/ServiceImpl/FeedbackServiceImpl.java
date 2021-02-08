package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.CustomExceptions.NotFoundException;
import com.ucl.imaginethisserver.Mapper.FeedbackDynamicSqlSupport;
import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Mapper.FeedbackMapper;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.FeedbackService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualToWhenPresent;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackMapper feedbackMapper;

    @Autowired
    public FeedbackServiceImpl(FeedbackMapper feedbackMapper) {
        this.feedbackMapper = feedbackMapper;
    }


    @Override
    public List<Feedback> getAllFeedbacks(String projectID) {
        return feedbackMapper.select(c -> c
                .where(FeedbackDynamicSqlSupport.projectId, isEqualToWhenPresent(projectID))
        );
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
        return false;
    }

    @Override
    public boolean voteFeedback(String projectID, UUID feedbackID, Vote vote) {
        return false;
    }
}
