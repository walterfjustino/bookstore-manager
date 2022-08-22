package com.api.bookstoremanager.author.exception;

import javax.persistence.EntityExistsException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AuthorAlreadyExistsException extends EntityExistsException {
    public AuthorAlreadyExistsException(@NotBlank @Size(max = 200) String name) {
        super(String.format("User with name %s already exists!", name));
    }
}
