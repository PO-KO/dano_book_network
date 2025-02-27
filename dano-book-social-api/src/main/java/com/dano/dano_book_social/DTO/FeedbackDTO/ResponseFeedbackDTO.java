package com.dano.dano_book_social.DTO.FeedbackDTO;

import java.time.LocalDateTime;

public record ResponseFeedbackDTO(
    Double note,
    String comment,
    Integer createdBy,
    LocalDateTime createdAt

) {
    
}
