package com.api.bookstoremanager.author.controller;

import com.api.bookstoremanager.author.service.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController implements AuthorControllerDocs{

    @Autowired
    private AuthorServiceImpl service;
}
