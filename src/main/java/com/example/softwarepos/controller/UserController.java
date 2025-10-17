package com.example.softwarepos.controller;

import com.example.softwarepos.entity.UserEntity;
import com.example.softwarepos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public String signup(@RequestBody UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "회원가입 완료";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserEntity loginRequest) {
        try {
            // AuthenticationManager로 인증 시도
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            // 인증 성공
            return "로그인 성공: " + authentication.getName();
        } catch (AuthenticationException e) {
            // 인증 실패
            return "로그인 실패: " + e.getMessage();
        }
    }
}
