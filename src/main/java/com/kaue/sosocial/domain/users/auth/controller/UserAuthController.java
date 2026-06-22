package com.kaue.sosocial.domain.users.auth.controller;

import com.kaue.sosocial.domain.users.auth.dto.UserLoginRequest;
import com.kaue.sosocial.domain.users.auth.dto.UserRegisterRequest;
import com.kaue.sosocial.domain.users.auth.dto.UserResponse;
import com.kaue.sosocial.domain.users.auth.dto.UserTokenResponse;
import com.kaue.sosocial.domain.users.auth.service.UserAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private final UserAuthService userAuthService;

    public UserAuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserTokenResponse> register(@RequestBody @Valid UserRegisterRequest req){
        return ResponseEntity.ok(userAuthService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenResponse> login(@RequestBody @Valid UserLoginRequest req){
        return ResponseEntity.ok(userAuthService.login(req));
    }

    @DeleteMapping("/user/delete/{userId}")
    public ResponseEntity<UserResponse> delete(@PathVariable UUID userId){
        userAuthService.delete(userId);
        return ResponseEntity.noContent().build();
    }

}
