package com.dano.dano_book_social.controllers.userController;

import org.springframework.web.bind.annotation.RestController;

import com.dano.dano_book_social.DTO.UserDTO.RequestAddRoleDTO;
import com.dano.dano_book_social.service.userService.RoleService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("auth/role/")
public class RoleController {

    private final RoleService service;
    
    @PostMapping("add")
    public ResponseEntity<?> addRole(@RequestBody RequestAddRoleDTO requestBody) {

        service.addRole(requestBody);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
}
