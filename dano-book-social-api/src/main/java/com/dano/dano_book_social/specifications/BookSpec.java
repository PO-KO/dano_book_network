package com.dano.dano_book_social.specifications;

import org.springframework.data.jpa.domain.Specification;
import com.dano.dano_book_social.entity.BookEntity;

public class BookSpec {
    public static Specification<BookEntity> withOwnerId(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), id);
    }

    public static Specification<BookEntity> withBorrwed(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                                                .equal(root.get("owner").get("id"), id);
    }

    public static Specification<BookEntity> withTitle(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                                                .like(root.get("title"), title);
    }

    public static Specification<BookEntity> withAuthor(String author) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                                                .like(root.get("author"), author);
    }

    public static Specification<BookEntity> withKeyword(String keyword) {
        return Specification.where(withTitle(keyword))
                            .or(withAuthor(keyword));
    }
    
    // public static Specification<BookEntity> withFilter(String keyword) {
    //     return Specification.where(withTitle(keyword))
    //                         .or(withAuthor(keyword));
    // }

}
