package com.kaue.sosocial.domain.users.service;

import com.kaue.sosocial.commons.enums.RoleUser;
import com.kaue.sosocial.domain.users.dto.UserLoginRequest;
import com.kaue.sosocial.domain.users.dto.UserRegisterRequest;
import com.kaue.sosocial.domain.users.dto.UserResponse;
import com.kaue.sosocial.domain.users.dto.UserTokenResponse;
import com.kaue.sosocial.domain.users.entity.User;
import com.kaue.sosocial.domain.users.repository.UserRepository;
import com.kaue.sosocial.infra.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserTokenResponse register(UserRegisterRequest req){
        if(userRepository.existsByEmail(req.email())){
            throw new IllegalArgumentException("Esse e-mail já foi cadastrado.");
        }

        var user = new User();
        user.setName(req.name());
        user.setEmail(req.email());
        user.setRoleUser(RoleUser.MEMBER);
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(req.password()));
        userRepository.save(user);

        return new UserTokenResponse(jwtService.createToken(user.getId(), user.getEmail()));
    }

    @Transactional
    public UserTokenResponse login(UserLoginRequest req){

        var user = userRepository.findByEmail(req.email())
                .orElseThrow( () -> new IllegalArgumentException("Email incorreto."));

        if (!passwordEncoder.matches(req.password(), user.getPassword())){
            throw new IllegalArgumentException("Senha incorreta.");
        }

        return new UserTokenResponse(jwtService.createToken(user.getId(), user.getEmail()));
    }

    @Transactional
    public void delete(UUID userId){
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        userRepository.delete(user);
    }

}
