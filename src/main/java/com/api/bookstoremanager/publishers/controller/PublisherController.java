package com.api.bookstoremanager.publishers.controller;

import com.api.bookstoremanager.publishers.dto.PublisherDTO;
import com.api.bookstoremanager.publishers.service.PublisherServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

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

  @GetMapping("/{id}")
  @Override
  public PublisherDTO findById(@PathVariable Long id) {
    return service.findById(id);
  }

  @GetMapping
  @Override
  public List<PublisherDTO> findAll() {
    return service.findAll();
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Override
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }

}
