package com.dano.dano_book_social.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HelloController {
    

    @GetMapping("greeting")
    public String greeting() {
        return "Hello";
    }
    

}
