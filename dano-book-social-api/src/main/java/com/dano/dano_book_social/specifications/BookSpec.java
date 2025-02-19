package com.dano.dano_book_social.specifications;

import org.springframework.data.jpa.domain.Specification;
import com.dano.dano_book_social.entity.BookEntity;

public class BookSpec {
    public static Specification<BookEntity> withOwnerId(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), id);
    }

    public static Specification<BookEntity> withOwnerIdAndBorrwed(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                                                .equal(root.get("owner").get("id"), id);
    }
}
