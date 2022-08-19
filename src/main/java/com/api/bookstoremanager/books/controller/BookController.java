package com.api.bookstoremanager.books.controller;

import com.api.bookstoremanager.books.dto.BookDTO;
import com.api.bookstoremanager.books.entity.Book;
import com.api.bookstoremanager.dto.MessageResponseDTO;
import com.api.bookstoremanager.books.exception.BookNotFoundException;
import com.api.bookstoremanager.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public MessageResponseDTO create(@RequestBody @Valid BookDTO bookDTO){

        return bookService.create(bookDTO) ;
    }

    @GetMapping("/{id}")
    public BookDTO findById(@PathVariable Long id) throws BookNotFoundException {
        return bookService. findById(id);

    }
    @GetMapping("/")
    public List<Book> findAll(){
        return bookService.findAll();
    }
}
