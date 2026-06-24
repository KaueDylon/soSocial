package com.kaue.sosocial.domain.like_post.service;

import com.kaue.sosocial.domain.like_post.dto.LikePostRequest;
import com.kaue.sosocial.domain.like_post.dto.LikePostResponse;
import com.kaue.sosocial.domain.like_post.entity.LikePost;
import com.kaue.sosocial.domain.like_post.repository.LikePostRepository;
import com.kaue.sosocial.domain.post.repository.PostRepository;
import com.kaue.sosocial.domain.users.profile.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LikePostService {

    private final LikePostRepository likePostRepository;
    private final PostRepository postRepository;
    private final UserProfileRepository userProfileRepository;


    public LikePostService(LikePostRepository likePostRepository, LikePostRepository postRepository, PostRepository postRepository1, UserProfileRepository userProfileRepository) {
        this.likePostRepository = likePostRepository;
        this.postRepository = postRepository1;
        this.userProfileRepository = userProfileRepository;
    }

    @Transactional
    public LikePostResponse saveLike(LikePostRequest req, UUID userId){

        var post = postRepository.findById(req.post())
                .orElseThrow(() -> new IllegalArgumentException("Não existe post"));

        var user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Não existe usuario"));

        var like = new LikePost();
        like.setPost(post);
        like.setUser(user);
        like.setCreatedAt(LocalDateTime.now());

        return new LikePostResponse(post.getId(), user.getId(), like.getCreatedAt());
    }

}
