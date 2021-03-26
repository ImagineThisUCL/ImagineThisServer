package com.ucl.imaginethisserver.Controller;

import com.ucl.imaginethisserver.Model.User;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> createNewUser(@RequestBody User user) {
        try {
            UUID userId = userService.createUser(user);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("userId", userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InternalServerErrorException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error creating new user", e
            );
        }
    }

    @PatchMapping("/users/{user-id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable("user-id") UUID userId,
                                                          @RequestBody User user) {
        try {
            user.setUserId(userId);
            Boolean result = userService.updateUser(user);
            Map<String, Object> response = new HashMap<>();
            response.put("success", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InternalServerErrorException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error creating new user", e
            );
        }
    }

    @GetMapping("/users/{user-id}/votes")
    public List<Vote> getVotesByUserID(@PathVariable("user-id") UUID userId) {
        try {
            return userService.getAllVotesForUser(userId);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No votes found for user", e
            );
        }
    }
}
