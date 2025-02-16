package com.dano.dano_book_social.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dano.dano_book_social.entity.TokenEntity;

@Repository
public interface TokenRepo extends JpaRepository<TokenEntity, Integer> {

    Optional<TokenEntity> findByToken(String token);
    
} 