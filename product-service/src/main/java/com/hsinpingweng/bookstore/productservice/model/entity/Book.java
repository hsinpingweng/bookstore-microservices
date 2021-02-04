package com.hsinpingweng.bookstore.productservice.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min=10, max=13, message="Name should have between 10 to 13 characters")
    private String isbn;

    @Size(min=1, max=100, message="Name should have 1 to 100 characters")
    private String name;


    @Size(max=1000, message="Description should have at most 1000 characters")
    private String description;


    @NotNull(message = "publishedDate may not be null (Format: dd-MM-yyyy)")
    @Column(name = "published_date")
    @DateTimeFormat(pattern="dd-MM-yyyy")
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date publishedDate;

    @ManyToOne
    @JoinColumn(name="author_id")
    @NotNull(message = "Author may not be null")
    private Author author;

    @ManyToOne
    @JoinColumn(name="category_id")
    @NotNull(message = "Category may not be null")
    private Category category;

    @ManyToOne
    @JoinColumn(name="publisher_id")
    @NotNull(message = "Publisher may not be null")
    private Publisher publisher;


}
