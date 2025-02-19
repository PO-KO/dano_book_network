package com.dano.dano_book_social.repos;

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
        WHERE bth.user.id != :userId
        """)
    Page <BookTransactionHistoryEntity> findAllBorrowedTransactionByUser(Pageable pageable, Integer userId);
    
    @Query("""
        SELECT bth FROM BookTransactionHistoryEntity bth
        WHERE bth.book.owner.id = :userId
        AND bth.returnApproved = true
        """)
    Page <BookTransactionHistoryEntity> findAllReturnedTransactionByUser(Pageable pageable, Integer userId);

}
