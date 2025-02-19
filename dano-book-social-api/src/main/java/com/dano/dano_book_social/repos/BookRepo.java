package com.dano.dano_book_social.repos;


import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dano.dano_book_social.entity.BookEntity;

@Repository
public interface BookRepo extends JpaRepository<BookEntity, Integer>, JpaSpecificationExecutor<BookEntity> {

    List<BookEntity> findByArchivedFalse();

    @Query(value = """
            SELECT b FROM BookEntity b
            WHERE b.archived = false
            AND b.shareable = true
            AND b.owner.id != :userId
            """)
    Page<BookEntity> findAllDisplayableBooks(Pageable pageable, Integer userId);
    
}
