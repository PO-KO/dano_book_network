package com.dano.dano_book_social.service.feedbackService;

import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dano.dano_book_social.DTO.FeedbackDTO.RequestFeedbackDTO;
import com.dano.dano_book_social.DTO.FeedbackDTO.ResponseFeedbackDTO;
import com.dano.dano_book_social.common.PageResponse;
import com.dano.dano_book_social.entity.FeedbackEntity;
import com.dano.dano_book_social.entity.UserEntity;
import com.dano.dano_book_social.exception.OperationNotPermittedException;
import com.dano.dano_book_social.repos.BookRepo;
import com.dano.dano_book_social.repos.FeedbackRepo;
import com.dano.dano_book_social.specifications.FeedbackSpec;
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

    public PageResponse<ResponseFeedbackDTO> getAllFeedbacksByBook(Integer bookId, int page, int size, Authentication connectedUser) {
        var book = bookRepo.findById(bookId).orElseThrow(() -> new EntityNotFoundException("No book found with ID " + bookId));
        var user = (UserEntity) connectedUser.getPrincipal();

        if(book.isArchived() && !Objects.equals(user.getId(), book.getOwner().getId())) {
            throw new OperationNotPermittedException("You can not see the feedback of an archived book");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<FeedbackEntity> feedbacks = feedbackRepo.findAll(FeedbackSpec.withBookId(bookId), pageable);

        List<ResponseFeedbackDTO> feedbacksData = feedbacks
                                                  .map(feedbackMapper::toFeedbackResponse)
                                                  .toList();

        return new PageResponse<>(
            feedbacksData,
            feedbacks.getNumber(),
            feedbacks.getSize(),
            feedbacks.getTotalElements(),
            feedbacks.getTotalPages(),
            feedbacks.isFirst(),
            feedbacks.isLast()
        );
    }
}
