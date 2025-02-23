package com.dano.dano_book_social.DTO.FeedbackDTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RequestFeedbackDTO(
    @NotNull
    @Max(5)
    @Positive
    Double note,
    String comment
) {
    
}
