package com.kaue.sosocial.domain.users.profile.controller;


import com.kaue.sosocial.domain.users.profile.dto.UserProfileResponse;
import com.kaue.sosocial.domain.users.profile.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getInfo(
            @AuthenticationPrincipal UUID userId
    ){
        return ResponseEntity.ok(userProfileService.getInfo(userId));
    }

}
