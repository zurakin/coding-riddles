package com.zurakin.codingriddles.controller;


import com.zurakin.codingriddles.models.dto.LeaderboardEntryDto;
import com.zurakin.codingriddles.models.dto.UserProfileDto;
import com.zurakin.codingriddles.models.mapper.UserProfileMapper;
import com.zurakin.codingriddles.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final UserProfileMapper userProfileMapper = UserProfileMapper.INSTANCE;

    @GetMapping
    public UserProfileDto getUserProfile(Authentication auth) {
        var user = userRepository.findByUsername(auth.getName());

        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        return userProfileMapper.toDto(user.get());
    }

    @GetMapping("/leaderboard")
    public List<LeaderboardEntryDto> getLeaderboard() {
        return userRepository.findTop10LeaderboardEntries();
    }
}
