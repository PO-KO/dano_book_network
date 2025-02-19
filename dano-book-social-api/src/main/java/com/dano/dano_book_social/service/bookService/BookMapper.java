package com.dano.dano_book_social.service.bookService;

import org.springframework.stereotype.Service;
import com.dano.dano_book_social.DTO.BookDTO.RequestCreateBookDTO;
import com.dano.dano_book_social.DTO.BookDTO.ResponseBookDTO;
import com.dano.dano_book_social.DTO.BookDTO.ResponseBorrowedBookDTO;
import com.dano.dano_book_social.entity.BookEntity;
import com.dano.dano_book_social.entity.UserEntity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookMapper {

    public BookEntity toBook(RequestCreateBookDTO requestBody, UserEntity owner) {

        return BookEntity
                .builder()
                .title(requestBody.title())
                .author(requestBody.author())
                .cover(null)
                .owner(owner)
                .isbn(requestBody.isbn())
                .synopsis(requestBody.synopsis())
                .shareable(requestBody.shareable())
                .archived(false)
                .build();
    }

    public ResponseBookDTO toBookResponse(BookEntity book) {

        return new ResponseBookDTO(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getIsbn(),
            book.getSynopsis(), 
            null,
            book.getNote(),
            book.getFeedbacks().size(),
            book.isShareable(), 
            book.isArchived(), 
            book.getOwner().getId());
    }

    public ResponseBorrowedBookDTO toBorrowedBookResponse(BookEntity book, boolean returned, boolean returnedApproved) {

        return new ResponseBorrowedBookDTO(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getIsbn(),
            book.getSynopsis(), 
            null,
            book.getNote(),
            book.getFeedbacks().size(),
            book.getOwner().getId(),
            returned,
            returnedApproved
            );
    }
}
