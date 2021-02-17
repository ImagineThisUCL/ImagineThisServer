package com.ucl.imaginethisserver.DAO.DAOImpl;

import com.ucl.imaginethisserver.DAO.UserDao;
import com.ucl.imaginethisserver.Mapper.UserMapper;
import com.ucl.imaginethisserver.Mapper.VoteDynamicSqlSupport;
import com.ucl.imaginethisserver.Mapper.VoteMapper;
import com.ucl.imaginethisserver.Model.User;
import com.ucl.imaginethisserver.Model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Repository
public class UserDoaImpl implements UserDao {
    private final UserMapper userMapper;

    private final VoteMapper voteMapper;

    @Override
    public List<Vote> getAllVotesForUser(UUID userID) {
        return voteMapper.select(c -> c
                .where(VoteDynamicSqlSupport.userId, isEqualTo(userID))
        );
    }


    @Autowired
    public UserDoaImpl(UserMapper userMapper, VoteMapper voteMapper) {
        this.userMapper = userMapper;
        this.voteMapper = voteMapper;
    }

    @Override
    public boolean createUser(User user) {
        User newUser = new User();
        newUser.setUserId(UUID.fromString(user.getUserId().toString()));
        newUser.setUserName(user.getUserName());
        return userMapper.insert(newUser) != 0;
    }

    @Override
    public boolean updateUser(User user) {
        user.setUserId(UUID.fromString(user.getUserId().toString()));
        return userMapper.updateByPrimaryKey(user) != 0;
    }
}
