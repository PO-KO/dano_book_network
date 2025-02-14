package com.dano.dano_book_social.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    
    @ManyToMany(mappedBy = "roles")
    @Builder.Default
    private Set<UserEntity> users = new HashSet<>();
}
