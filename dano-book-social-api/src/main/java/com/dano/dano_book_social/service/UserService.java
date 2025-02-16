package com.dano.dano_book_social.service;


import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dano.dano_book_social.DTO.UserDTO.RequestRegisterDTO;
import com.dano.dano_book_social.DTO.UserDTO.RequestTokenDTO;
import com.dano.dano_book_social.DTO.UserDTO.RequestLoginDTO;
import com.dano.dano_book_social.DTO.UserDTO.ResponseLoginDTO;
import com.dano.dano_book_social.constents.EmailTemplateName;
import com.dano.dano_book_social.entity.RoleEntity;
import com.dano.dano_book_social.entity.TokenEntity;
import com.dano.dano_book_social.entity.UserEntity;
import com.dano.dano_book_social.repos.RoleRepo;
import com.dano.dano_book_social.repos.TokenRepo;
import com.dano.dano_book_social.repos.UserRepo;

import ch.qos.logback.core.subst.Token;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final TokenRepo tokenRepo;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final EmailService emailService;
    @Value("${application.security.jwt.exp}")
    private long jwtExp;
    @Value("${application.mailing.frontend.activation-url}")
    private String activateAccountURL;

    public ResponseLoginDTO login(RequestLoginDTO requestBody) {

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

    @Transactional
    public void register(RequestRegisterDTO requestBody) throws MessagingException {
        // Check if there is a user with this email or not
        boolean isUserExist = userRepo.findByEmail(requestBody.email()).isPresent();
        if(isUserExist) {
            throw new IllegalArgumentException("Email already taken");
        }

        // Create new user
        var newUser = UserEntity.builder()
                                .firstName(requestBody.firstName().trim())
                                .lastName(requestBody.lastName().trim())
                                .birthDate(requestBody.birthDate())
                                .email(requestBody.email().trim())
                                .password(new BCryptPasswordEncoder(12).encode(requestBody.password()))
                                .enabled(false)
                                .accountLocked(false)
                                .build();

        // Add Roles
        Set<RoleEntity> roles = new HashSet<>();
        
        var roleAdmin = roleRepo.findByName("ADMIN").orElseThrow(
            () -> new IllegalArgumentException("This role does not exist")
            );
        var roleUser = roleRepo.findByName("USER").orElseThrow(
            () -> new IllegalArgumentException("This role does not exist")
            );

        roles.addAll(List.of(roleAdmin, roleUser));
        newUser.addRoles(roles);

        userRepo.save(newUser);

        sendValidationEmail(newUser);
        
    }

    private void sendValidationEmail(UserEntity user) throws MessagingException {
        var activationToken = generateAndSaveToken(user);
            emailService.sendEmail(
                user.getEmail(),
                user.getName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activateAccountURL,
                activationToken,
                "Activate your account");
        
    }
    
     @Transactional
    private String generateAndSaveToken(UserEntity user) {
        String generatedActivationToken = generateActivationToken(6);

        TokenEntity token = TokenEntity.builder()
                            .token(generatedActivationToken)
                            .createdAt(LocalDateTime.now())
                            .expiresAt(LocalDateTime.now().plusMinutes(10))
                            .user(user)
                            .build();

        tokenRepo.save(token);

        return generatedActivationToken;
                
    }
        
    private String generateActivationToken(int length) {
        String CHARS = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for(int i = 0; i < length; i++) {
            int randomNumber = secureRandom.nextInt(CHARS.length());
            codeBuilder.append(CHARS.charAt(randomNumber));
        }

        return codeBuilder.toString();
    }

    @Transactional
    public void activateUserAccount(RequestTokenDTO requestBody) throws MessagingException {
        TokenEntity tokenDB = tokenRepo.findByToken(requestBody.token()).orElseThrow(() -> new IllegalArgumentException("Token invalid"));
        if(!tokenDB.getUser().isEnabled() && tokenDB.getExpiresAt().isAfter(LocalDateTime.now())) {
            tokenDB.getUser().setEnabled(true);
            tokenDB.setValidatedAt(LocalDateTime.now());
            tokenRepo.save(tokenDB);
        } else if (tokenDB.getUser().isEnabled()) {
            System.out.println("User account is already enabled");
        } else {
            sendValidationEmail(tokenDB.getUser());
            System.out.println("Token expired new token sent to your email");
        }

    }
}

