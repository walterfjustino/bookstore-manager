package com.api.bookstoremanager.publishers.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PublisherAlreadyExistsException extends EntityExistsException {
  public PublisherAlreadyExistsException(String name, String code) {
    super(String.format("Publisher with name %s or code %s already exists!", name, code));
  }
}
