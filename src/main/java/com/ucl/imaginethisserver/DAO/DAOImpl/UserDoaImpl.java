package com.ucl.imaginethisserver.DAO.DAOImpl;

import com.ucl.imaginethisserver.DAO.UserDao;
import com.ucl.imaginethisserver.Mapper.UserMapper;
import com.ucl.imaginethisserver.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserDoaImpl implements UserDao {
    private final UserMapper userMapper;

    @Autowired
    public UserDoaImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
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
