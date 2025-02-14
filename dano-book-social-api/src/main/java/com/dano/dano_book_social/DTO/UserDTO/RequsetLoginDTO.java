package com.dano.dano_book_social.DTO.UserDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RequsetLoginDTO(
    @NotEmpty
    @Email
    String email,
    @NotEmpty
    @Size(min = 8)
    String password
) {

}
