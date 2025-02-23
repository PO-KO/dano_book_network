package com.dano.dano_book_social.service.feedbackService;

import com.dano.dano_book_social.DTO.FeedbackDTO.RequestFeedbackDTO;
import com.dano.dano_book_social.entity.FeedbackEntity;

public class FeedbackMapper {
    public FeedbackEntity toFeedback(RequestFeedbackDTO requestBody) {

        return FeedbackEntity.builder()
                .note(requestBody.note())
                .comment(requestBody.comment())
                .build();
    }
}
