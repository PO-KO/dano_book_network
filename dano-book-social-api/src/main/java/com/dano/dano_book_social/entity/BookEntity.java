package com.dano.dano_book_social.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "book")
@EntityListeners(AuditingEntityListener.class)
public class BookEntity {
    
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String title;
    private String author;
    @Column(unique = true)
    private String isbn;
    private String synopsis;
    private String cover;
    private boolean archived;
    private boolean shareable;
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

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FeedbackEntity> feedbacks;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookTransactionHistoryEntity> transactions;

    @ManyToMany(mappedBy = "books")
    private Set<UserEntity> users;


}
