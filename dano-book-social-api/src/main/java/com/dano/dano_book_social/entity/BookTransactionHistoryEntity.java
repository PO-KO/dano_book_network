package com.dano.dano_book_social.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "book_transaction_history")
@EntityListeners(AuditingEntityListener.class)
public class BookTransactionHistoryEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private boolean returned;
    private boolean returnApproved;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdBy;
    @LastModifiedBy
    @Column(insertable = false)
    private LocalDateTime updatedBy;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id") 
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id") 
    private UserEntity user;
}
