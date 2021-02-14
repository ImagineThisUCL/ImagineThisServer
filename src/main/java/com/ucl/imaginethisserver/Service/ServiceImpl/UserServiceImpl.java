package com.ucl.imaginethisserver.Service.ServiceImpl;

import com.ucl.imaginethisserver.CustomExceptions.InternalServerErrorException;
import com.ucl.imaginethisserver.DAO.UserDao;
import com.ucl.imaginethisserver.Model.User;
import com.ucl.imaginethisserver.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UUID createUser(User user) {
        // check user ID and user name
        if (user.getUserName() == null) {
            logger.error("Error creating new User: user name not provided");
            throw new InternalServerErrorException();
        }
        if (user.getUserId() == null) {
            UUID uuid = UUID.randomUUID();
            logger.info("Generating new User ID " + uuid);
            user.setUserId(uuid);
        }
        if (userDao.createUser(user)) {
            return (UUID)user.getUserId();
        } else {
            logger.error("Error creating new User. Please check the log");
            throw new InternalServerErrorException();
        }
    }

    @Override
    public boolean updateUser(User user) {
        // check user ID
        if (user.getUserId() == null) {
            logger.error("Error updating User: user ID not provided");
            throw new InternalServerErrorException();
        }
        return userDao.updateUser(user);
    }
}
