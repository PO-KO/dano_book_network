package com.dano.dano_book_social.DTO.BookDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record RequestCreateBookDTO(
    @NotEmpty
    @NotBlank
    String title,
    @NotEmpty
    @NotBlank
    String author,
    @NotEmpty
    @NotBlank
    String isbn,
    String synopsis,
    boolean shareable,
    Integer owner_id
) {
    
}
