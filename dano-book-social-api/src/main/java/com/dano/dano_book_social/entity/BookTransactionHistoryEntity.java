package com.dano.dano_book_social.entity;


import com.dano.dano_book_social.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "book_transaction_history")
public class BookTransactionHistoryEntity extends BaseEntity {

    private boolean returned;
    private boolean returnApproved;

    @ManyToOne
    //@JoinColumn(name = "book_id", referencedColumnName = "id")  // Optional
    private BookEntity book;

    @ManyToOne
    //@JoinColumn(name = "user_id", referencedColumnName = "id") // Optional
    private UserEntity user;
}
