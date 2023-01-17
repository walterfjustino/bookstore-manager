package com.api.bookstoremanager.publishers.service;

import com.api.bookstoremanager.author.dto.AuthorDTO;
import com.api.bookstoremanager.publishers.dto.PublisherDTO;

import java.util.List;

public interface PublisherService {

  PublisherDTO create(PublisherDTO publisherDTO);

  PublisherDTO findById(Long id);

  List<PublisherDTO> findAll();

  public void delete(Long id);
}
