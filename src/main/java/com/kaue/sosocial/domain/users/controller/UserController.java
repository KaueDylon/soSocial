package com.kaue.sosocial.domain.users.controller;

import com.kaue.sosocial.domain.users.dto.UserLoginRequest;
import com.kaue.sosocial.domain.users.dto.UserRegisterRequest;
import com.kaue.sosocial.domain.users.dto.UserResponse;
import com.kaue.sosocial.domain.users.dto.UserTokenResponse;
import com.kaue.sosocial.domain.users.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserTokenResponse> register(@RequestBody @Valid UserRegisterRequest req){
        return ResponseEntity.ok(userService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenResponse> login(@RequestBody @Valid UserLoginRequest req){
        return ResponseEntity.ok(userService.login(req));
    }

    @DeleteMapping("/user/delete/{userId}")
    public ResponseEntity<UserResponse> delete(@PathVariable UUID userId){
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

}
