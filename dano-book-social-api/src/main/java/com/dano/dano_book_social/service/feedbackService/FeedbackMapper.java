package com.dano.dano_book_social.service.feedbackService;

import org.springframework.stereotype.Service;
import com.dano.dano_book_social.DTO.FeedbackDTO.RequestFeedbackDTO;
import com.dano.dano_book_social.DTO.FeedbackDTO.ResponseFeedbackDTO;
import com.dano.dano_book_social.entity.FeedbackEntity;

@Service
public class FeedbackMapper {
    public FeedbackEntity toFeedback(RequestFeedbackDTO requestBody) {

        return FeedbackEntity.builder()
                .note(requestBody.note())
                .comment(requestBody.comment())
                .build();
    }

    public ResponseFeedbackDTO toFeedbackResponse(FeedbackEntity feedbackEntity) {
        return new ResponseFeedbackDTO(feedbackEntity.getNote(),
                                       feedbackEntity.getComment(),
                                       feedbackEntity.getCreatedBy(),
                                       feedbackEntity.getCreatedAt());
    }
}
