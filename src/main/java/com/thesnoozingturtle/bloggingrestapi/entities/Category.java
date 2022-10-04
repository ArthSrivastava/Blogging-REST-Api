package com.thesnoozingturtle.bloggingrestapi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @Column(name = "title", nullable = false)
    private String categoryTitle;

    @Column(name = "description")
    private String categoryDescription;

}
