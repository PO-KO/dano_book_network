package com.dano.dano_book_social.service.bookService;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dano.dano_book_social.DTO.BookDTO.RequestCreateBookDTO;
import com.dano.dano_book_social.DTO.BookDTO.ResponseBookDTO;
import com.dano.dano_book_social.DTO.BookDTO.ResponseBorrowedBookDTO;
import com.dano.dano_book_social.common.PageResponse;
import com.dano.dano_book_social.entity.BookEntity;
import com.dano.dano_book_social.entity.BookTransactionHistoryEntity;
import com.dano.dano_book_social.entity.UserEntity;
import com.dano.dano_book_social.repos.BookRepo;
import com.dano.dano_book_social.repos.BookTransactionHistoryRepo;
import com.dano.dano_book_social.specifications.BookSpec;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepo bookRepo;
    private final BookTransactionHistoryRepo transactionRepo;
    private final BookMapper bookMapper;

    @Transactional
    public void createBook(RequestCreateBookDTO requestBody, Authentication authentication) {
        UserEntity owner = ((UserEntity) authentication.getPrincipal());
        BookEntity newBook = bookMapper.toBook(requestBody, owner);

        bookRepo.save(newBook);

    }

    public ResponseBookDTO getBookById(Integer id) {

        BookEntity book = bookRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));

        return bookMapper.toBookResponse(book);
    }
    public PageResponse<ResponseBookDTO> getAllDisplayableBooks(int page, int size, Authentication connectedUser) {

        UserEntity user = (UserEntity) connectedUser.getPrincipal();
         Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<BookEntity> books = bookRepo.findAllDisplayableBooks(pageable, user.getId());

        List<ResponseBookDTO> booksData = books
                                        .stream()
                                        .map(bookMapper::toBookResponse)
                                        .toList();

        return new PageResponse<>(booksData,
                    books.getNumber(),
                    books.getSize(),
                    books.getTotalElements(),
                    books.getTotalPages(),
                    books.isFirst(),
                    books.isLast()
        );
            
        
    }

    public PageResponse<ResponseBookDTO> getAllOwnedBooks(int page, int size, Authentication connectedUser) {
        UserEntity user = (UserEntity) connectedUser.getPrincipal();
         Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<BookEntity> books = bookRepo.findAll(BookSpec.withOwnerId(user.getId()), pageable);
        
        List<ResponseBookDTO> booksData = books
                                        .stream()
                                        .map(bookMapper::toBookResponse)
                                        .toList();

        return new PageResponse<>(booksData,
                    books.getNumber(),
                    books.getSize(),
                    books.getTotalElements(),
                    books.getTotalPages(),
                    books.isFirst(),
                    books.isLast()
        );
    }

    public PageResponse<ResponseBorrowedBookDTO> getAllBorrowedBooks(int page, int size, Authentication connectedUser) {
        UserEntity user = (UserEntity) connectedUser.getPrincipal();
         Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<BookTransactionHistoryEntity> booksTransaction = transactionRepo.findAllBorrowedTransactionByUser(pageable, user.getId());

        List<ResponseBorrowedBookDTO> booksData = booksTransaction.stream()
                                                    .map(bookTrans -> 
                                                        bookMapper.toBorrowedBookResponse(
                                                            bookTrans.getBook(),
                                                            bookTrans.isReturned(),
                                                            bookTrans.isReturnApproved()))
                                                    .toList();

        return new PageResponse<>(booksData,
                    booksTransaction.getNumber(),
                    booksTransaction.getSize(),
                    booksTransaction.getTotalElements(),
                    booksTransaction.getTotalPages(),
                    booksTransaction.isFirst(),
                    booksTransaction.isLast()
        );
    }

    public PageResponse<ResponseBorrowedBookDTO> getAllReturnedBooks(int page, int size, Authentication connectedUser) {
        UserEntity user = (UserEntity) connectedUser.getPrincipal();
         Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<BookTransactionHistoryEntity> booksTransaction = transactionRepo.findAllReturnedTransactionByUser(pageable, user.getId());

        List<ResponseBorrowedBookDTO> booksData = booksTransaction.stream()
                                                    .map(bookTrans -> 
                                                        bookMapper.toBorrowedBookResponse(
                                                            bookTrans.getBook(),
                                                            bookTrans.isReturned(),
                                                            bookTrans.isReturnApproved()))
                                                    .toList();

        return new PageResponse<>(booksData,
                    booksTransaction.getNumber(),
                    booksTransaction.getSize(),
                    booksTransaction.getTotalElements(),
                    booksTransaction.getTotalPages(),
                    booksTransaction.isFirst(),
                    booksTransaction.isLast()
        );
    }




}
