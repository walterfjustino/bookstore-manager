package com.api.bookstoremanager.books.service;

import com.api.bookstoremanager.books.dto.BookRequestDTO;
import com.api.bookstoremanager.books.dto.BookResponseDTO;
import com.api.bookstoremanager.users.dto.AuthenticatedUser;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BookService {

    public BookResponseDTO create(AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO);

    public BookResponseDTO findById(AuthenticatedUser authenticatedUser, Long id);

    public List<BookResponseDTO> findAllByUser(AuthenticatedUser authenticatedUser);

    public void deleteByIdAndUser(AuthenticatedUser authenticatedUser, Long id);

    public BookResponseDTO updateByUser(AuthenticatedUser authenticatedUser, Long id, BookRequestDTO bookRequestDTO);
}
