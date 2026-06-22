package com.kaue.sosocial.domain.post.service;

import com.kaue.sosocial.commons.enums.StatusPost;
import com.kaue.sosocial.domain.post.dto.PostCreateRequest;
import com.kaue.sosocial.domain.post.dto.PostResponse;
import com.kaue.sosocial.domain.post.dto.PostViewResponse;
import com.kaue.sosocial.domain.post.entity.Post;
import com.kaue.sosocial.domain.post.repository.PostRepository;
import com.kaue.sosocial.domain.users.auth.dto.UserResponse;
import com.kaue.sosocial.domain.users.auth.repository.UserAuthRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserAuthRepository userAuthRepository;

    public PostService(PostRepository postRepository, UserAuthRepository userAuthRepository) {
        this.postRepository = postRepository;
        this.userAuthRepository = userAuthRepository;
    }

    public Page<PostResponse> getAllPostFromUser(Pageable pageable, UUID userId){
        if(userId == null){
            throw new IllegalArgumentException("Usuário não existe");
        }else{
            return postRepository.getAllPostsStatusPublic(userId, StatusPost.ACTIVE ,pageable);
        }
    }

    @Transactional
    public PostResponse createPost(PostCreateRequest req, UUID userId){

        if(req.text().isEmpty()){
            throw new IllegalArgumentException("Texto da postagem não deve ser vazio");
        }

        var user = userAuthRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não existe"));

        var post = new Post();

        post.setUser(user);
        post.setStatus(StatusPost.ACTIVE);
        post.setText(req.text());
        post.setCreatedAt(LocalDateTime.now());

        postRepository.save(post);

        return new PostResponse(post.getId() ,post.getText(), post.getCreatedAt());

    }

    @Transactional
    public PostViewResponse alterStatusPost(String statusPost, UUID postId, UUID userId){

        var user = userAuthRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não existe"));

        var post = postRepository.getPostByIdAndUserId(userId, postId)
                .orElseThrow(() -> new IllegalArgumentException("Post não existe"));

        StatusPost status;
        try{
            status = StatusPost.valueOf(statusPost.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e);
        }

        post.setStatus(status);
        postRepository.save(post);

        return new PostViewResponse(post);

    }

    @Transactional
    public PostViewResponse getViewPostByUser(UUID postId){

        var post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post não existe"));

        return new PostViewResponse(post);
    }

}
