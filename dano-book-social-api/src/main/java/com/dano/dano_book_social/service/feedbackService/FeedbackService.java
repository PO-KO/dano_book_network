package com.dano.dano_book_social.service.feedbackService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dano.dano_book_social.DTO.FeedbackDTO.RequestFeedbackDTO;
import com.dano.dano_book_social.entity.UserEntity;
import com.dano.dano_book_social.exception.OperationNotPermittedException;
import com.dano.dano_book_social.repos.BookRepo;
import com.dano.dano_book_social.repos.FeedbackRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepo feedbackRepo;
    private final BookRepo bookRepo;
    private final FeedbackMapper feedbackMapper;

    @Transactional
    public void saveFeedback(Integer bookId, RequestFeedbackDTO requestBody, Authentication connectedUser) {
        var book = bookRepo.findById(bookId).orElseThrow(() -> new EntityNotFoundException("No book found with ID " + bookId));
        var user = (UserEntity) connectedUser.getPrincipal();  

        if(book.getOwner().getId().intValue() == user.getId().intValue()) {
            throw new OperationNotPermittedException("you can not feedback a book that you own");
        }

        if(book.isArchived()) {
            throw new OperationNotPermittedException("You can not feedback this book");
        }

        

        var feedback = feedbackMapper.toFeedback(requestBody);
        feedback.setBook(book);

        feedbackRepo.save(feedback);
    }
}
