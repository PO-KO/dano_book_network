package com.dano.dano_book_social.DTO.UserDTO;

import jakarta.validation.constraints.NotEmpty;

public record RequestAddRoleDTO(
    @NotEmpty
    String name
) {
    
}
