package com.api.bookstoremanager.author.exception;

import jakarta.persistence.EntityNotFoundException;

public class AuthorNotFoundException extends EntityNotFoundException {
    public AuthorNotFoundException(Long id) {
      super(String.format("Author with id %s not exists!", id));
    }
}
