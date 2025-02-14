package com.dano.dano_book_social.service;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.dano.dano_book_social.DTO.UserDTO.RequsetLoginDTO;
import com.dano.dano_book_social.DTO.UserDTO.ResponseLoginDTO;
import com.dano.dano_book_social.entity.UserEntity;
import com.dano.dano_book_social.repos.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepo userRepo;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    @Value("${application.security.jwt.exp}")
    private long jwtExp;

    public ResponseLoginDTO login(RequsetLoginDTO requestBody) {

        Authentication authentication = 
                authManager.authenticate(new UsernamePasswordAuthenticationToken(requestBody.email(), requestBody.password()));
        
        if(authentication.isAuthenticated()) {
            UserEntity user = userRepo.findByEmail(requestBody.email()).orElseThrow();
            String token = jwtService.generateToken(user);
            return new ResponseLoginDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                token,
                "Bearer",
                jwtExp);
        } else {
            throw new UsernameNotFoundException("Email or password is incorrect");
        }
        
    } 
}
