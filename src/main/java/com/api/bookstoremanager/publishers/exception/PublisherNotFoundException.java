package com.api.bookstoremanager.publishers.exception;

import jakarta.persistence.EntityNotFoundException;

public class PublisherNotFoundException extends EntityNotFoundException {
  public PublisherNotFoundException(Long id) {
    super(String.format("Publisher with id %s not exists!", id));
  }
}
