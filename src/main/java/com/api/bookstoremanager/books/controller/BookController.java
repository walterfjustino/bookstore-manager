package com.api.bookstoremanager.books.controller;

import com.api.bookstoremanager.books.docs.BookControllerDocs;
import com.api.bookstoremanager.books.dto.BookRequestDTO;
import com.api.bookstoremanager.books.dto.BookResponseDTO;
import com.api.bookstoremanager.books.service.BookService;
import com.api.bookstoremanager.users.dto.AuthenticatedUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController implements BookControllerDocs {

  @Autowired
  private BookService bookService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Override
  public BookResponseDTO create(@AuthenticationPrincipal AuthenticatedUser authenticatedUser,
                                @RequestBody @Valid BookRequestDTO bookRequestDTO) {
    return bookService.create(authenticatedUser, bookRequestDTO);
  }

  @GetMapping("/{id}")
  @Override
  public BookResponseDTO findByIdAndUser(@AuthenticationPrincipal AuthenticatedUser authenticatedUser,
                                         @PathVariable Long id) {
    return bookService.findById(authenticatedUser, id);
  }

  @GetMapping
  @Override
  public List<BookResponseDTO> findAllByUser(AuthenticatedUser authenticatedUser) {
    return bookService.findAllByUser(authenticatedUser);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Override
  public void deleteByIdAndUser(@AuthenticationPrincipal AuthenticatedUser authenticatedUser,
                                @PathVariable Long id) {
    bookService.deleteByIdAndUser(authenticatedUser,id);
  }

  @PutMapping("/{id}")
  @Override
  public BookResponseDTO updateByUser(@AuthenticationPrincipal AuthenticatedUser authenticatedUser,
                                      @PathVariable Long id,
                                      @RequestBody @Valid BookRequestDTO bookRequestDTO) {
    return bookService.updateByUser(authenticatedUser,id, bookRequestDTO);
  }
}
