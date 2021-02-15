package com.hsinpingweng.bookstore.productservice.model;

import com.hsinpingweng.bookstore.productservice.model.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
}
