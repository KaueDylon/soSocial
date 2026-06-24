package com.kaue.sosocial.domain.users.profile.service;

import com.kaue.sosocial.domain.users.profile.dto.UserProfileResponse;
import com.kaue.sosocial.domain.users.profile.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Transactional
    public UserProfileResponse getInfo(UUID userId){

        var user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return new UserProfileResponse(user.getId(),user.getName(),user.getEmail(),user.getCreatedAt());

    }


}
