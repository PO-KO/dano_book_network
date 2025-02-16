package com.dano.dano_book_social.DTO.UserDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RequestTokenDTO(
    @NotEmpty
    @Size(min = 6, max = 6)
    String token
) {
    
}
