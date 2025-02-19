package com.dano.dano_book_social.repos.userRepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dano.dano_book_social.entity.RoleEntity;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByName(String name);
}
