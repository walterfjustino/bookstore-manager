package com.api.bookstoremanager.books.service;

import com.api.bookstoremanager.books.dto.BookDTO;
import com.api.bookstoremanager.books.dto.BookRequestDTO;
import com.api.bookstoremanager.books.dto.BookResponseDTO;
import com.api.bookstoremanager.books.entity.Book;
import com.api.bookstoremanager.books.exception.BookNotFoundException;
import com.api.bookstoremanager.users.dto.AuthenticatedUser;

import java.util.List;

public interface BookService {

    public BookResponseDTO create(AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO);

    public BookDTO findById(Long id) throws BookNotFoundException;

    public List<Book> findAll();
}
