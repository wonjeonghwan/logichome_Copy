package com.example.nono_logic.domain.user;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/me")
    public String me(Authentication authentication) {
        return authentication.getName(); // ← JWT에서 넣은 email
    }

    @GetMapping
    public String test() {
        return "users api alive";
    }
}
