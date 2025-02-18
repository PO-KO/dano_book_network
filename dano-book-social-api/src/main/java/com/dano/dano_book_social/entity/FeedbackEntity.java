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
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedback")
public class FeedbackEntity extends BaseEntity{

    private Double note;
    private String comment;

    @ManyToOne
    // @JoinColumn(name = "book_id", referencedColumnName = "id")  // Optional
    private BookEntity book;

}
