package com.hsinpingweng.bookstore.productservice.web;


import com.hsinpingweng.bookstore.productservice.exception.NotFoundException;
import com.hsinpingweng.bookstore.productservice.model.AuthorRepository;
import com.hsinpingweng.bookstore.productservice.model.entity.Author;
import com.hsinpingweng.bookstore.productservice.model.entity.Book;
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
public class AuthorResource {

    @Autowired
    private AuthorRepository authorRepo;


    @ApiOperation(value="Retrieve all authors")
    @ApiResponses({
            @ApiResponse(responseCode="200", description="Successfully retrieve all authors")
    })
    @GetMapping("/authors")
    public List<Author> retrieveAllAuthors (){
        return authorRepo.findAll();
    }


    @ApiOperation(value="Retrieve author by id")
    @ApiResponses({
            @ApiResponse(responseCode="200", description="Successfully retrieve a author"),
            @ApiResponse(responseCode="404", description="Author id is not existed")
    })
    @GetMapping("/authors/{id}")
    public Author retrieveAuthor(@ApiParam("Author id") @PathVariable int id) throws NotFoundException {
        Optional<Author> author = authorRepo.findById(id);
        if (!author.isPresent())
            throw new NotFoundException("Author id " + id + " is not existed.");

        return author.get();
    }


    @ApiOperation(value="List author's books by author id")
    @ApiResponses({
            @ApiResponse(responseCode="200", description="Successfully retrieve all books by author id"),
            @ApiResponse(responseCode="404", description="Author id is not existed")
    })
    @GetMapping("/authors/{id}/books")
    public Set<Book> retrieveBooksByAuthor(@ApiParam("Author id") @PathVariable int id) throws NotFoundException {
        Optional<Author> author = authorRepo.findById(id);
        if (!author.isPresent())
            throw new NotFoundException("Author id " + id + " is not existed.");

        return authorRepo.findById(id).get().getBooks();
    }


    @ApiOperation(value="Create a author")
    @ApiResponses({
            @ApiResponse(responseCode="201", description="Successfully create a author")
    })
    @PostMapping("/authors")
    public ResponseEntity<Object> createAuthor(@Valid @RequestBody Author author) {
        Author savedAuthor = authorRepo.save(author);

        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                .buildAndExpand(savedAuthor.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @ApiOperation(value="Update author")
    @ApiResponses({
            @ApiResponse(responseCode="201", description="Successfully update a author information")
    })
    @PutMapping("/authors/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable int id, @Valid @RequestBody Author author) throws NotFoundException {
        Optional<Author> authorOpt = authorRepo.findById(id);
        if (!authorOpt.isPresent())
            throw new NotFoundException("Author id " + id + " is not existed.");

        author.setId(id);
        Author savedAuthor = authorRepo.save(author);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAuthor.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @ApiOperation(value="Delete a author")
    @ApiResponses({
            @ApiResponse(responseCode="200", description="Successfully delete a author by id")
    })
    @DeleteMapping("/authors/{id}")
    public void deleteAuthor(@PathVariable int id) throws NotFoundException, ConstraintViolationException {

        Optional<Author> authorOpt = authorRepo.findById(id);
        if (!authorOpt.isPresent())
            throw new NotFoundException("Author id " + id + " is not existed.");

        if (!authorOpt.get().getBooks().isEmpty())
            throw new ConstraintViolationException("Can not delete Author id " + id + ", still have some books belong to this author", null, "id");

        authorRepo.deleteById(id);
    }

}
