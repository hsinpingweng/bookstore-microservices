package com.hsinpingweng.bookstore.productservice.model;

import com.hsinpingweng.bookstore.productservice.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findByName(String name);
}
