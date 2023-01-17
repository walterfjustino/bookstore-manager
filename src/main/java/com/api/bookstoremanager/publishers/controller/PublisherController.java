package com.api.bookstoremanager.publishers.controller;

import com.api.bookstoremanager.publishers.service.PublisherServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/publishers")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PublisherController {

  @Autowired
  private PublisherServiceImpl service;

}
