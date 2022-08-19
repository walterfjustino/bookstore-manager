package com.api.bookstoremanager.books.service;

import com.api.bookstoremanager.books.dto.BookDTO;
import com.api.bookstoremanager.books.entity.Book;
import com.api.bookstoremanager.books.exception.BookNotFoundException;
import com.api.bookstoremanager.dto.MessageResponseDTO;

import java.util.List;

public interface BookService {

    public MessageResponseDTO create(BookDTO bookDTO);

    public BookDTO findById(Long id) throws BookNotFoundException;

    public List<Book> findAll();
}
