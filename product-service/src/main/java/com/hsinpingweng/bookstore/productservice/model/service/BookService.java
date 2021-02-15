package com.hsinpingweng.bookstore.productservice.model.service;


import com.hsinpingweng.bookstore.productservice.model.entity.Book;

import java.util.Date;
import java.util.List;

public  interface BookService {

    List<Book> findAll(Integer id, String isbn, String name,
                       Date publishedDate, Integer authorId,
                       Integer categoryId, Integer publisher);

}

