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
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    
    @GetMapping("/signup")
    public String signupPage() {
    return "회원가입 페이지";
    }
    @PostMapping("/signup")
    public String signup(@RequestBody UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "회원가입 완료";
    }

    @PostMapping("/login")
public Map<String, Object> login(@RequestBody UserEntity loginRequest) {
    Map<String, Object> result = new HashMap<>();
    try {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );
        // 인증 성공
        result.put("success", true);
        result.put("username", authentication.getName());
    } catch (AuthenticationException e) {
        // 인증 실패
        result.put("success", false);
        result.put("message", e.getMessage());
    }
    return result; 
}

}
