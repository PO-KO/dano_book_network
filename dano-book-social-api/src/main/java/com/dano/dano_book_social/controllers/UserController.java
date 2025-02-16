package com.dano.dano_book_social.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.dano.dano_book_social.DTO.UserDTO.RequestLoginDTO;
import com.dano.dano_book_social.DTO.UserDTO.RequestRegisterDTO;
import com.dano.dano_book_social.DTO.UserDTO.RequestTokenDTO;
import com.dano.dano_book_social.DTO.UserDTO.ResponseLoginDTO;
import com.dano.dano_book_social.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("auth/")
public class UserController {
    
    private final UserService service;
    @PostMapping("login")
    public ResponseEntity<ResponseLoginDTO> login(@RequestBody @Valid RequestLoginDTO requestBody) {
        var reponseBody = service.login(requestBody);
        return ResponseEntity.ok(reponseBody);
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody @Valid RequestRegisterDTO requestBody) throws MessagingException {
        System.out.println("Hello");
        service.register(requestBody);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("activate-account")
    public ResponseEntity<?> activateAccount(@RequestBody @Valid RequestTokenDTO requestBody) throws MessagingException {
        service.activateUserAccount(requestBody);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
