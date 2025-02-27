package com.dano.dano_book_social.specifications;

import org.springframework.data.jpa.domain.Specification;
import com.dano.dano_book_social.entity.FeedbackEntity;

public class FeedbackSpec {
    public static Specification<FeedbackEntity> withBookId(Integer bookId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("book").get("id"), bookId);
    }
}
