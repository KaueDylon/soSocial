package com.kaue.sosocial.domain.post.dto;

import com.kaue.sosocial.commons.enums.StatusPost;
import com.kaue.sosocial.domain.post.entity.Post;
import com.kaue.sosocial.domain.users.auth.dto.UserResponse;

import java.time.LocalDateTime;

public record PostViewResponse(
        String text,
        LocalDateTime createdAt,
        UserResponse user,
        StatusPost status
) {

    public PostViewResponse(Post data){
        this(data.getText(), data.getCreatedAt(), new UserResponse(data.getUser().getId(),
                data.getUser().getName()), data.getStatus());
    }

}
