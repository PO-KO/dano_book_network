package com.dano.dano_book_social.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.dano.dano_book_social.entity.FeedbackEntity;

@Repository
public interface FeedbackRepo extends JpaRepository<FeedbackEntity, Integer>, JpaSpecificationExecutor<FeedbackEntity> {
    
}
