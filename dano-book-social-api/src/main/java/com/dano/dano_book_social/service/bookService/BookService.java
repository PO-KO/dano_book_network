package com.dano.dano_book_social.service.bookService;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.dano.dano_book_social.DTO.BookDTO.RequestCreateBookDTO;
import com.dano.dano_book_social.DTO.BookDTO.ResponseBookDTO;
import com.dano.dano_book_social.DTO.BookDTO.ResponseBorrowedBookDTO;
import com.dano.dano_book_social.common.PageResponse;
import com.dano.dano_book_social.entity.BookEntity;
import com.dano.dano_book_social.entity.BookTransactionHistoryEntity;
import com.dano.dano_book_social.entity.UserEntity;
import com.dano.dano_book_social.exception.OperationNotPermittedException;
import com.dano.dano_book_social.repos.BookRepo;
import com.dano.dano_book_social.repos.BookTransactionHistoryRepo;
import com.dano.dano_book_social.service.FileService;
import com.dano.dano_book_social.specifications.BookSpec;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepo bookRepo;
    private final BookTransactionHistoryRepo transactionRepo;
    private final BookMapper bookMapper;
    private final FileService fileService;

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

    public ResponseBorrowedBookDTO getBorrowedBook(Integer id, Authentication connectedUser) {
        var book = bookRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No book found with ID " + id));
        var user = (UserEntity) connectedUser.getPrincipal();

        var bookTransaction = transactionRepo.findBorrowedTransactionByUser(book.getId(), user.getId()).orElseThrow(() -> new EntityNotFoundException("This book not borrowed"));

        return bookMapper.toBorrowedBookResponse(
                                        bookTransaction.getBook(),
                                        bookTransaction.isReturned(),
                                        bookTransaction.isReturnApproved());
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

    public void updateShareable(Integer id, Authentication connectedUser) {
        var book = bookRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No book found with ID " + id));
        var user = (UserEntity) connectedUser.getPrincipal();

        if(book.getOwner().getId() != user.getId()) {
            throw new OperationNotPermittedException("you can not update this book");
        }

        book.setShareable(!book.isShareable());

        bookRepo.save(book);
    }

    @Transactional
    public void updateArchived(Integer id, Authentication connectedUser) {
        var book = bookRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No book found with ID " + id));
        var user = (UserEntity) connectedUser.getPrincipal();

        if(book.getOwner().getId().intValue() != user.getId().intValue()) {
            throw new OperationNotPermittedException("you can not update this book");
        }

        book.setArchived(!book.isArchived());

        bookRepo.save(book);
    }

    @Transactional
    public void borrowBook(Integer id, Authentication connectedUser) {
        var book = bookRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No book found with ID " + id));
        var user = (UserEntity) connectedUser.getPrincipal();

        if(book.getOwner().getId().intValue() == user.getId().intValue()) {
            throw new OperationNotPermittedException("you can not borrow a book you own");
        }

        if(book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("you can not borrow this book");
        }

        book.setShareable(false);

        var transaction = BookTransactionHistoryEntity.builder()
                            .user(user)
                            .book(book)
                            .returnApproved(false)
                            .returned(false)
                            .build();
        
        transactionRepo.save(transaction);
    }

    
    @Transactional
    public void returnBorrowedBook(Integer id, Authentication connectedUser) {
        var book = bookRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No book found with ID " + id));
        var user = (UserEntity) connectedUser.getPrincipal();

        if(book.getOwner().getId().intValue() == user.getId().intValue()) {
            throw new OperationNotPermittedException("you can not borrow or return a book you own");
        }

        if(book.isArchived() || book.isShareable()) {
            throw new OperationNotPermittedException("you can not borrow or return this book");
        }

        var bookTransaction = transactionRepo.findBorrowedTransactionByUser(book.getId(), user.getId()).orElseThrow(
            () -> new OperationNotPermittedException("This book not borrowed"));

        bookTransaction.setReturned(true);

        transactionRepo.save(bookTransaction);
    }

    @Transactional
    public void returnApproveBorrowedBook(Integer id, Authentication connectedUser) {
        var book = bookRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No book found with ID " + id));
        var user = (UserEntity) connectedUser.getPrincipal();

        if(book.getOwner().getId().intValue() != user.getId().intValue()) {
            throw new OperationNotPermittedException("you can not approve a returned book you do not own");
        }

        if(book.isArchived() || book.isShareable()) {
            throw new OperationNotPermittedException("you can not approve this returned book");
        }

        var bookTransaction = transactionRepo.findBorrowedTransactionByOwner(book.getId(), user.getId()).orElseThrow(
            () -> new OperationNotPermittedException("This book not borrowed or is not returned yet"));

        bookTransaction.setReturnApproved(true);
        book.setShareable(true);
        transactionRepo.save(bookTransaction);
        bookRepo.save(book);
    }

    public void uploadBookCover(Integer id, Authentication connectedUser, MultipartFile file) {
        var book = bookRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No book found with ID " + id));
        var user = (UserEntity) connectedUser.getPrincipal();

        if(book.getOwner().getId().intValue() != user.getId().intValue()) {
            throw new OperationNotPermittedException("you can not update a book's cover that you do not own");
        }

        String bookCover = fileService.saveFile(file, user.getId());
        book.setCover(bookCover);
        bookRepo.save(book);

    }

}
