package com.dano.dano_book_social.DTO.BookDTO;

public record ResponseBookDTO(
    Integer id,
    String title,
    String author,
    String isbn,
    String synopsis,
    byte[] cover,
    Double rate,
    Integer rate_count,
    boolean shareable,
    boolean archived,
    Integer owner_id
) {
    
}
