package com.dano.dano_book_social.repos.criteria;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.dano.dano_book_social.entity.BookEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookCriteria {
    private final EntityManager em;

    List<BookEntity> searchBook() {
        try {
            em.getTransaction().begin();
        
            // Create criteria builder
            CriteriaBuilder cBuilder = em.getCriteriaBuilder();

            // Create criteria query
            CriteriaQuery<BookEntity> cQuery = cBuilder.createQuery(BookEntity.class); // Result's type

            // Create root of the query
            Root<BookEntity> bRoot = cQuery.from(BookEntity.class); // = FROM BookEntity b

            // Do the select operation on root=bRoot 
            cQuery.select(bRoot); // = SELECT b FROM bookEntity b
            // cQuery.select(bRoot.get("title")); // = SELECT title FROM bookEntity b --> we should also change the result's type

            // Final query
            TypedQuery<BookEntity> query = em.createQuery(cQuery);

            var result = query.getResultList();
            em.getTransaction().commit();
            return result;
            
        } finally {
            em.close();
        }

    }
}
