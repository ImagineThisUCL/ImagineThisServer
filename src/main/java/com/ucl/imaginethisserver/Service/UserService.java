package com.ucl.imaginethisserver.Service;

import com.ucl.imaginethisserver.Model.User;

import java.util.UUID;

public interface UserService {
    UUID createUser(User user);

    boolean updateUser(User user);
}
