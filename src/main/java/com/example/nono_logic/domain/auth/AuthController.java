package com.example.nono_logic.domain.auth;

import com.example.nono_logic.domain.user.UserService;
import com.example.nono_logic.domain.user.CreateUserRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        String token = userService.login(request.getEmail(), request.getPassword());
        return Map.of("accessToken", token);
    }

    @PostMapping("/register")
    public String register(@RequestBody CreateUserRequest request) {
        System.out.println("🔥 register API called: " + request.getEmail());
        userService.createUser(request.getEmail(), request.getPassword());
        return "registered";
    }


}