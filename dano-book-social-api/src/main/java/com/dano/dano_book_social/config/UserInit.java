package com.dano.dano_book_social.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.dano.dano_book_social.entity.RoleEntity;
import com.dano.dano_book_social.entity.UserEntity;
import com.dano.dano_book_social.repos.userRepo.RoleRepo;
import com.dano.dano_book_social.repos.userRepo.UserRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserInit implements CommandLineRunner {
    
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

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
        
        // Add Roles
        Set<RoleEntity> roles = new HashSet<>();
        
        var roleAdmin = roleRepo.findByName("ADMIN").orElseThrow(
            () -> new IllegalArgumentException("This role does not exist")
            );
        var roleUser = roleRepo.findByName("USER").orElseThrow(
            () -> new IllegalArgumentException("This role does not exist")
            );

        roles.addAll(List.of(roleAdmin, roleUser));
        admin.addRoles(roles);

        userRepo.save(admin);

    }
    
}
