package com.hsinpingweng.bookstore.productservice.web;


import com.hsinpingweng.bookstore.productservice.exception.NotFoundException;
import com.hsinpingweng.bookstore.productservice.model.PublisherRepository;
import com.hsinpingweng.bookstore.productservice.model.entity.Book;
import com.hsinpingweng.bookstore.productservice.model.entity.Publisher;
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
public class PublisherResource {

    @Autowired
    private PublisherRepository publisherRepo;


    @ApiOperation(value="Retrieve all publishers")
    @ApiResponses({
            @ApiResponse(responseCode="200", description="Successfully retrieve all publishers")
    })
    @GetMapping("/publishers")
    public List<Publisher> retrieveAllPublishers (){
        return publisherRepo.findAll();
    }


    @ApiOperation(value="Retrieve publisher by id")
    @ApiResponses({
            @ApiResponse(responseCode="200", description="Successfully retrieve a publisher"),
            @ApiResponse(responseCode="404", description="Publisher id is not existed")
    })
    @GetMapping("/publishers/{id}")
    public Publisher retrievePublisher (@ApiParam("Publisher id") @PathVariable int id) throws NotFoundException {
        Optional<Publisher> publisher = publisherRepo.findById(id);
        if (!publisher.isPresent())
            throw new NotFoundException("Publisher id " + id + " is not existed.");

        return publisher.get();
    }


    @ApiOperation(value="List publisher's books by publisher id")
    @ApiResponses({
            @ApiResponse(responseCode="200", description="Successfully retrieve all books by publisher id"),
            @ApiResponse(responseCode="404", description="Publisher id is not existed")
    })
    @GetMapping("/publishers/{id}/books")
    public Set<Book> retrieveBooksByPublisher (@ApiParam("Publisher id") @PathVariable int id) throws NotFoundException {
        Optional<Publisher> publisher = publisherRepo.findById(id);
        if (!publisher.isPresent())
            throw new NotFoundException("Publisher id " + id + " is not existed.");

        return publisher.get().getBooks();
    }


    @ApiOperation(value="Update publisher")
    @ApiResponses({
            @ApiResponse(responseCode="201", description="Successfully update a publisher information")
    })
    @PutMapping("/publishers/{id}")
    public ResponseEntity<Object> updatePublisher(@ApiParam("Publisher id") @PathVariable int id,
                                                  @Valid @RequestBody Publisher publisher) throws NotFoundException {
        Optional<Publisher> publisherOpt = publisherRepo.findById(id);
        if (!publisherOpt.isPresent())
            throw new NotFoundException("Publisher id " + id + " is not existed.");

        publisher.setId(id);
        Publisher savedPublisher = publisherRepo.save(publisher);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPublisher.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @ApiOperation(value="Create a publisher")
    @ApiResponses({
            @ApiResponse(responseCode="201", description="Successfully create a publisher")
    })
    @PostMapping("/publishers")
    public ResponseEntity<Object> createPublisher(@Valid @RequestBody Publisher publisher) {
        Publisher savedPublisher = publisherRepo.save(publisher);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPublisher.getId()).toUri();

        return ResponseEntity.created(location).build();
    }



    @ApiOperation(value="Delete a publisher")
    @ApiResponses({
            @ApiResponse(responseCode="200", description="Successfully delete a publisher by id")
    })
    @DeleteMapping("/publishers/{id}")
    public void deletePublisher(@ApiParam("Publisher id") @PathVariable int id) throws NotFoundException, ConstraintViolationException {

        Optional<Publisher> publisherOpt = publisherRepo.findById(id);
        if (!publisherOpt.isPresent())
            throw new NotFoundException("Publisher id " + id + " is not existed.");

        if (!publisherOpt.get().getBooks().isEmpty())
            throw new ConstraintViolationException("Can not delete publisher id " + id + ", still have some books belong to this publisher", null, "id");

        publisherRepo.deleteById(id);
    }
}
