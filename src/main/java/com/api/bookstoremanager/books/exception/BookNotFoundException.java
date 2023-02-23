package com.api.bookstoremanager.books.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BookNotFoundException extends EntityNotFoundException {

    public BookNotFoundException(Long id) {
        super(String.format("Book with ID: %s not found", id));
    }
}
