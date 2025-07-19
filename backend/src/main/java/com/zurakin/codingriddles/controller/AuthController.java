package com.zurakin.codingriddles.controller;

import com.zurakin.codingriddles.models.domain.User;
import com.zurakin.codingriddles.models.dto.AuthRequest;
import com.zurakin.codingriddles.models.dto.AuthResponseDto;
import com.zurakin.codingriddles.models.mapper.UserMapper;
import com.zurakin.codingriddles.models.entity.UserEntity;
import com.zurakin.codingriddles.repository.UserRepository;
import com.zurakin.codingriddles.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(userMapper.toEntity(user));
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        UserEntity userEntity = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        User user = UserMapper.INSTANCE.toDomain(userEntity);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String jwt = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponseDto(jwt));
    }
}