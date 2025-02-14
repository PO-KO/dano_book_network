package com.dano.dano_book_social.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.dano.dano_book_social.entity.UserEntity;
import com.dano.dano_book_social.repos.UserRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserInit implements CommandLineRunner {
    
    private final UserRepo userRepo;

    @Override
    public void run(String... args) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        UserEntity admin = UserEntity.builder()
                            .firstName("Ayoub")
                            .lastName("Daoui")
                            .email("ayoubda18@gmail.com")
                            .password(new BCryptPasswordEncoder(12).encode("123"))
                            .birthDate(dateFormat.parse("2000-05-30"))
                            .accountLocked(false)
                            .enabled(true)
                            .build();
        
        admin.addRoles(new ArrayList<>(List.of("ADMIN")));

        userRepo.save(admin);

    }
    
}
