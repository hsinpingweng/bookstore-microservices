package com.hsinpingweng.bookstore.productservice.web;


import com.hsinpingweng.bookstore.productservice.exception.NotFoundException;
import com.hsinpingweng.bookstore.productservice.model.CategoryRepository;
import com.hsinpingweng.bookstore.productservice.model.entity.Book;
import com.hsinpingweng.bookstore.productservice.model.entity.Category;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class CategoryResource {

    @Autowired
    private CategoryRepository categoryRepo;


    @ApiOperation(value="Retrieve all categories")
    @ApiResponses({
            @ApiResponse(responseCode="200", description="Successfully retrieve all categories")
    })
    @GetMapping("/categories")
    public List<Category> retrieveAllCategories (){
        return categoryRepo.findAll();
    }


    @ApiOperation(value="Retrieve category by id")
    @ApiResponses({
            @ApiResponse(responseCode="200", description="Successfully get the category"),
            @ApiResponse(responseCode="404", description="Category id is not existed")
    })
    @GetMapping("/categories/{id}")
    public Category retrieveCategory (@ApiParam("Category id") @PathVariable int id) throws NotFoundException {
        Optional<Category> category = categoryRepo.findById(id);
        if (!category.isPresent())
            throw new NotFoundException("Category id " + id + " is not existed.");

        return category.get();
    }


    @ApiOperation(value="List books belongs to the category id")
    @ApiResponses({
            @ApiResponse(responseCode="200", description="Successfully retrieve all books by category id"),
            @ApiResponse(responseCode="404", description="category id is not existed")
    })
    @GetMapping("/categories/{id}/books")
    public Set<Book> retrieveBooksByCategory (@ApiParam("Category id") @PathVariable int id) throws NotFoundException {
        Optional<Category> category = categoryRepo.findById(id);
        if (!category.isPresent())
            throw new NotFoundException("Category id " + id + " is not existed.");

        return category.get().getBooks();
    }


    @ApiOperation(value="Update category")
    @ApiResponses({
            @ApiResponse(responseCode="201", description="Successfully update a category information")
    })
    @PutMapping("/categories/{id}")
    public ResponseEntity<Object> updateCategory(@ApiParam("Category id") @PathVariable int id,
                                                 @Valid @RequestBody Category category) throws NotFoundException {
        Optional<Category> categoryOpt = categoryRepo.findById(id);
        if (!categoryOpt.isPresent())
            throw new NotFoundException("Category id " + id + " is not existed.");

        category.setId(id);
        Category savedCategory = categoryRepo.save(category);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCategory.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @ApiOperation(value="Create a category")
    @ApiResponses({
            @ApiResponse(responseCode="201", description="Successfully create a category")
    })
    @PostMapping("/categories")
    public ResponseEntity<Object> createCategory(@Valid @RequestBody Category category) {
        Category savedCategory = categoryRepo.save(category);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCategory.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @ApiOperation(value="Delete a category")
    @ApiResponses({
            @ApiResponse(responseCode="200", description="Successfully delete a category by id")
    })
    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@ApiParam("Category id") @PathVariable int id) throws NotFoundException, ConstraintViolationException {

        Optional<Category> category = categoryRepo.findById(id);
        if (!category.isPresent())
            throw new NotFoundException("Category id " + id + " is not existed.");

        if (!category.get().getBooks().isEmpty())
            throw new ConstraintViolationException("Can not delete category id " + id + ", still have some books belong to this category", null, "id");

        categoryRepo.deleteById(id);
    }

}
