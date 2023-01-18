package com.api.bookstoremanager.publishers.controller;

import com.api.bookstoremanager.publishers.dto.PublisherDTO;
import com.api.bookstoremanager.publishers.service.PublisherServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/publishers")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PublisherController implements PublisherControllerDocs {

  @Autowired
  private PublisherServiceImpl service;


  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Override
  public PublisherDTO create(@RequestBody @Valid PublisherDTO publisherDTO) {
    return service.create(publisherDTO);
  }
}
