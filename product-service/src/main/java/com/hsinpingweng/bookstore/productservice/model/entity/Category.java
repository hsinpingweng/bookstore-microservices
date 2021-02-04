package com.hsinpingweng.bookstore.productservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true, nullable=false)
    @Size(min=2, message="Name should have at least 2 characters")
    private String name;

    @OneToMany(mappedBy="category")
    @JsonIgnore
    private Set<Book> books;

}
