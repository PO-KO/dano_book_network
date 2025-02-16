package com.dano.dano_book_social.service;

import org.springframework.stereotype.Service;

import com.dano.dano_book_social.DTO.UserDTO.RequestAddRoleDTO;
import com.dano.dano_book_social.entity.RoleEntity;
import com.dano.dano_book_social.repos.RoleRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepo roleRepo;

    @Transactional
    public void addRole(RequestAddRoleDTO requestBody) {
       boolean isRoleExist = roleRepo.findByName(requestBody.name()).isPresent();
       if(isRoleExist) {
        throw new IllegalArgumentException("This role already exist");
       } 

       var newRole = RoleEntity.builder().name(requestBody.name().toUpperCase().trim()).build();

       roleRepo.save(newRole);
    }

}
