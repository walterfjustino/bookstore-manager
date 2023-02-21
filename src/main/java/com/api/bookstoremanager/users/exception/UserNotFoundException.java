package com.api.bookstoremanager.users.exception;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
  public UserNotFoundException(Long id) {
    super(String.format("User with id %s not exists!", id));
  }

  public UserNotFoundException(String username) {
    super(String.format("User with username %s not exists!", username));
  }
}
