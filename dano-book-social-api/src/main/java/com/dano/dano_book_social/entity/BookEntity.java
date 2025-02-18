package com.dano.dano_book_social.entity;

import java.util.Set;


import com.dano.dano_book_social.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class BookEntity extends BaseEntity {
    
    @Column(nullable = false)
    private String title;
    private String author;
    @Column(unique = true)
    private String isbn;
    private String synopsis;
    private String cover;
    private boolean archived;
    private boolean shareable;

    @OneToMany(mappedBy = "book")
    private Set<FeedbackEntity> feedbacks;
    
    @OneToMany(mappedBy = "book")
    private Set<BookTransactionHistoryEntity> transactions;

    @ManyToOne
    // @JoinColumn(referencedColumnName = "id") // Optional
    private UserEntity owner;

    // @ManyToMany(mappedBy = "books")
    // private Set<UserEntity> users;


}
