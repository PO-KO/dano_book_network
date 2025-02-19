package com.dano.dano_book_social.controllers.bookController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dano.dano_book_social.DTO.BookDTO.RequestCreateBookDTO;
import com.dano.dano_book_social.DTO.BookDTO.ResponseBookDTO;
import com.dano.dano_book_social.DTO.BookDTO.ResponseBorrowedBookDTO;
import com.dano.dano_book_social.common.PageResponse;
import com.dano.dano_book_social.service.bookService.BookService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("books")
public class BookController {

    private final BookService service;

    @PostMapping("/create")
    public ResponseEntity<?> createBook(@RequestBody @Valid RequestCreateBookDTO requestBody, Authentication authentication) {
        service.createBook(requestBody, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBookDTO> getBookById(@PathVariable @NotNull Integer id) {

        return ResponseEntity.ok(service.getBookById(id));
    }

    @GetMapping("/displayable")
    public ResponseEntity<PageResponse<ResponseBookDTO>>getAllDisplayableBooks(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        Authentication authentication
        ) {

        return ResponseEntity.ok(service.getAllDisplayableBooks(page, size, authentication));
    }

    @GetMapping("/owned")
    public ResponseEntity<PageResponse<ResponseBookDTO>>getAllOwnedBooks(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        Authentication authentication
        ) {

        return ResponseEntity.ok(service.getAllOwnedBooks(page, size, authentication));
    }

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<ResponseBorrowedBookDTO>>getAllBorrowedBooks(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        Authentication authentication
        ) {

        return ResponseEntity.ok(service.getAllBorrowedBooks(page, size, authentication));
    }

    @GetMapping("/returned")
    public ResponseEntity<PageResponse<ResponseBorrowedBookDTO>>getAllRetrunedBooks(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        Authentication authentication
        ) {

        return ResponseEntity.ok(service.getAllReturnedBooks(page, size, authentication));
    }
}
