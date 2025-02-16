package com.dano.dano_book_social.DTO.UserDTO;

import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public record RequestRegisterDTO(
    @NotEmpty
    String firstName,
    @NotEmpty
    String lastName,
    Date birthDate,
    @NotEmpty
    @Email
    String email,
    @NotEmpty
    @Size(min = 8)
    String password
) {
    
}
