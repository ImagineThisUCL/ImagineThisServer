package com.ucl.imaginethisserver.Controller;

import com.ucl.imaginethisserver.Model.User;
import com.ucl.imaginethisserver.Model.Vote;
import com.ucl.imaginethisserver.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> createNewUser(@RequestBody User user) {
        UUID userId = userService.createUser(user);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("userId", userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/users/{user-id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable("user-id") UUID userId,
                                                          @RequestBody User user) {
        user.setUserId(userId);
        Boolean result = userService.updateUser(user);
        Map<String, Object> response = new HashMap<>();
        response.put("success", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/{user-id}/votes")
    public List<Vote> getVotesByUserID(@PathVariable("user-id") UUID userId) {
        return userService.getAllVotesForUser(userId);
    }
}
