package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.CustomExceptions.NotFoundException;
import com.ucl.imaginethisserver.DAO.FeedbackDao;
import com.ucl.imaginethisserver.DAO.FeedbackDto;
import com.ucl.imaginethisserver.Mapper.FeedbackDynamicSqlSupport;
import com.ucl.imaginethisserver.Mapper.VoteDynamicSqlSupport;
import com.ucl.imaginethisserver.Model.Feedback;
import com.ucl.imaginethisserver.Mapper.FeedbackMapper;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.FeedbackService;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackMapper feedbackMapper;

    private final FeedbackDao feedbackDao;

    @Autowired
    public FeedbackServiceImpl(FeedbackMapper feedbackMapper, FeedbackDao feedbackDao) {
        this.feedbackMapper = feedbackMapper;
        this.feedbackDao = feedbackDao;
    }

    public static class UpvoteCount implements BasicColumn {
        private String alias;
        public UpvoteCount() {
            super();
        }
        @Override
        public Optional<String> alias() {
            return Optional.ofNullable(alias);
        }

        @Override
        public BasicColumn as(String s) {
            UpvoteCount upvoteCount = new UpvoteCount();
            upvoteCount.alias = s;
            return upvoteCount;
        }

        @Override
        public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
            return "count(case when vote > 0 then vote end)";
        }
    }

    @Override
    public List<Feedback> getAllFeedbacks(String projectID) {


        return feedbackMapper.select(c -> c
                .where(FeedbackDynamicSqlSupport.projectId, isEqualToWhenPresent(projectID))
        );
    }

    @Override
    public List<FeedbackDto> getFeedbacksWithVotes(String projectID) {
//        SelectStatementProvider provider = (SelectStatementProvider) SqlBuilder
//                .select(
//                        FeedbackDynamicSqlSupport.feedbackId,
//                        FeedbackDynamicSqlSupport.projectId,
//                        FeedbackDynamicSqlSupport.userId,
//                        new UpvoteCount().as("upvotes"),
//                        FeedbackDynamicSqlSupport.userName,
//                        FeedbackDynamicSqlSupport.timestamp,
//                        FeedbackDynamicSqlSupport.text
//                )
//                .from(FeedbackDynamicSqlSupport.feedback)
//                .leftJoin(VoteDynamicSqlSupport.vote)
//                .on(FeedbackDynamicSqlSupport.feedbackId, equalTo(VoteDynamicSqlSupport.feedbackId))
//                .where(FeedbackDynamicSqlSupport.projectId, isEqualToWhenPresent(projectID))
//                .groupBy(FeedbackDynamicSqlSupport.feedbackId)
//                .build()
//                .render(RenderingStrategy.MYBATIS3);
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
        return false;
    }

    @Override
    public boolean voteFeedback(String projectID, UUID feedbackID, Vote vote) {
        return false;
    }
}
