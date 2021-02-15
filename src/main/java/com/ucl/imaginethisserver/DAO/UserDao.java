package com.ucl.imaginethisserver.DAO;

import com.ucl.imaginethisserver.Model.User;

public interface UserDao {
    boolean createUser(User user);

    boolean updateUser(User user);
}
