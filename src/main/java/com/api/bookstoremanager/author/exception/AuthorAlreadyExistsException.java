package com.api.bookstoremanager.author.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthorAlreadyExistsException extends EntityExistsException {
    public AuthorAlreadyExistsException(@NotBlank @Size(max = 200) String name) {
        super(String.format("User with name %s already exists!", name));
    }
}
