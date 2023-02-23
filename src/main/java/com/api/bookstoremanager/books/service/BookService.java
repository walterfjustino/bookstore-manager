package com.api.bookstoremanager.books.service;

import com.api.bookstoremanager.books.dto.BookRequestDTO;
import com.api.bookstoremanager.books.dto.BookResponseDTO;
import com.api.bookstoremanager.users.dto.AuthenticatedUser;

import java.util.List;

public interface BookService {

    public BookResponseDTO create(AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO);

    public BookResponseDTO findById(AuthenticatedUser authenticatedUser, Long id);

    public List<BookResponseDTO> findAll();
}
