package com.dano.dano_book_social.controllers.feedbackController;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dano.dano_book_social.DTO.FeedbackDTO.RequestFeedbackDTO;
import com.dano.dano_book_social.DTO.FeedbackDTO.ResponseFeedbackDTO;
import com.dano.dano_book_social.common.PageResponse;
import com.dano.dano_book_social.service.feedbackService.FeedbackService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("feedback")
public class FeedbackController {
    private final FeedbackService service;
    @PostMapping("/save/{book_id}")
    public ResponseEntity<?> saveFeedback(@RequestBody
                                          @Valid
                                          RequestFeedbackDTO requestBody,
                                          @PathVariable
                                          Integer bookId,
                                          Authentication authentication
                                          ) {

        service.saveFeedback(bookId, requestBody, authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{book_id}")
    public ResponseEntity<PageResponse<ResponseFeedbackDTO>> getMethodName(
                                @RequestParam(required = false, defaultValue = "0") int page,
                                @RequestParam(required = false, defaultValue = "10") int size,
                                @PathVariable Integer bookId,
                                Authentication authentication
                                ) {

        return ResponseEntity.ok(service.getAllFeedbacksByBook(bookId, page, size, authentication));
    }
    
}
