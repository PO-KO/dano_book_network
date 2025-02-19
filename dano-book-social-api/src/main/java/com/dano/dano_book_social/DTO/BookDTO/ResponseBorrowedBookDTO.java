package com.dano.dano_book_social.DTO.BookDTO;

public record ResponseBorrowedBookDTO(
    Integer id,
    String title,
    String author,
    String isbn,
    String synopsis,
    byte[] cover,
    Double rate,
    Integer rate_count,
    Integer owner_id,
    boolean returned,
    boolean returnedApproved

) {
    
}
