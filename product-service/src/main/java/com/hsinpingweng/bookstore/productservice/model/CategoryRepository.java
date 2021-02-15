package com.hsinpingweng.bookstore.productservice.model;

import com.hsinpingweng.bookstore.productservice.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByName(String name);

}
