package com.kaue.sosocial.domain.users.auth.service;

import com.kaue.sosocial.commons.enums.RoleUser;
import com.kaue.sosocial.domain.users.auth.dto.UserLoginRequest;
import com.kaue.sosocial.domain.users.auth.dto.UserRegisterRequest;
import com.kaue.sosocial.domain.users.auth.dto.UserTokenResponse;
import com.kaue.sosocial.domain.users.entity.User;
import com.kaue.sosocial.domain.users.auth.repository.UserAuthRepository;
import com.kaue.sosocial.infra.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserAuthService {

    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserAuthService(UserAuthRepository userAuthRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userAuthRepository = userAuthRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserTokenResponse register(UserRegisterRequest req){
        if(userAuthRepository.existsByEmail(req.email())){
            throw new IllegalArgumentException("Esse e-mail já foi cadastrado.");
        }

        var user = new User();
        user.setName(req.name());
        user.setEmail(req.email());
        user.setRoleUser(RoleUser.MEMBER);
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(req.password()));
        userAuthRepository.save(user);

        return new UserTokenResponse(jwtService.createToken(user.getId(), user.getEmail()));
    }

    @Transactional
    public UserTokenResponse login(UserLoginRequest req){

        var user = userAuthRepository.findByEmail(req.email())
                .orElseThrow( () -> new IllegalArgumentException("Email incorreto."));

        if (!passwordEncoder.matches(req.password(), user.getPassword())){
            throw new IllegalArgumentException("Senha incorreta.");
        }

        return new UserTokenResponse(jwtService.createToken(user.getId(), user.getEmail()));
    }

    @Transactional
    public void delete(UUID userId){
        var user = userAuthRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        userAuthRepository.delete(user);
    }

}
