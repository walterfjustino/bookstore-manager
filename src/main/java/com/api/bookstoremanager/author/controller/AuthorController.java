package com.api.bookstoremanager.author.controller;

import com.api.bookstoremanager.author.dto.AuthorDTO;
import com.api.bookstoremanager.author.service.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    @Autowired
    private AuthorServiceImpl service;

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public AuthorDTO create(@RequestBody @Valid AuthorDTO authorDTO) {
//        return service.create(authorDTO);
//    }



    @PostMapping
    public ResponseEntity<AuthorDTO> create(@Valid @RequestBody AuthorDTO authorDTO){
        var createAuthor = service.create(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAuthor);
    }
}
