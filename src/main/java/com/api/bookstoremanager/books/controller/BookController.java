package com.api.bookstoremanager.books.controller;

import com.api.bookstoremanager.books.docs.BookControllerDocs;
import com.api.bookstoremanager.books.dto.BookDTO;
import com.api.bookstoremanager.books.dto.BookRequestDTO;
import com.api.bookstoremanager.books.dto.BookResponseDTO;
import com.api.bookstoremanager.books.entity.Book;
import com.api.bookstoremanager.dto.MessageResponseDTO;
import com.api.bookstoremanager.books.exception.BookNotFoundException;
import com.api.bookstoremanager.books.service.BookService;
import com.api.bookstoremanager.users.dto.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController implements BookControllerDocs {

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

    @Override
    public BookResponseDTO create(AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO) {
        return null;
    }

    @Override
    public BookResponseDTO findByIdAndUser(AuthenticatedUser authenticatedUser, Long id) {
        return null;
    }

    @Override
    public List<BookResponseDTO> findAllByUser(AuthenticatedUser authenticatedUser) {
        return null;
    }

    @Override
    public BookResponseDTO updateByUser(AuthenticatedUser authenticatedUser, Long bookId, BookRequestDTO bookRequestDTO) {
        return null;
    }

    @Override
    public void deleteByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId) {

    }
}
