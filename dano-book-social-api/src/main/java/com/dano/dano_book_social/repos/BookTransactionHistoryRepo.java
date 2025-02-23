package com.dano.dano_book_social.repos;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.dano.dano_book_social.entity.BookTransactionHistoryEntity;

@Repository
public interface BookTransactionHistoryRepo extends JpaRepository<BookTransactionHistoryEntity, Integer> {
    
    @Query("""
        SELECT bth FROM BookTransactionHistoryEntity bth
        WHERE bth.user.id = :userId
        """)
    Page <BookTransactionHistoryEntity> findAllBorrowedTransactionByUser(Pageable pageable, Integer userId);

    @Query("""
        SELECT bth FROM BookTransactionHistoryEntity bth
        WHERE bth.user.id = :userId
        AND bth.book.id = :bookId
        AND bth.returned = false
        """)
    Optional<BookTransactionHistoryEntity> findBorrowedTransactionByUser(Integer bookId, Integer userId);
    
    @Query("""
        SELECT bth FROM BookTransactionHistoryEntity bth
        WHERE bth.book.owner.id = :userId
        AND bth.returnApproved = true
        """)
    Page <BookTransactionHistoryEntity> findAllReturnedTransactionByUser(Pageable pageable, Integer userId);

    @Query("""
        SELECT bth FROM BookTransactionHistoryEntity bth
        WHERE bth.owner.id = :ownerId
        AND bth.book.id = :bookId
        AND bth.returned = true
        AND bth.returnApproved = false
        """)
    Optional<BookTransactionHistoryEntity> findBorrowedTransactionByOwner(Integer bookId, Integer ownerId);

}
