package com.dano.dano_book_social.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.dano.dano_book_social.DTO.UserDTO.RequsetLoginDTO;
import com.dano.dano_book_social.DTO.UserDTO.ResponseLoginDTO;
import com.dano.dano_book_social.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
public class UserController {
    
    private final UserService service;
    @PostMapping("login")
    public ResponseEntity<ResponseLoginDTO> login(@RequestBody RequsetLoginDTO requestBody) {
        var reponseBody = service.login(requestBody);
        return ResponseEntity.ok(reponseBody);
    }


}
