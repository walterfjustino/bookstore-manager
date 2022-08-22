package com.api.bookstoremanager.author.controller;

import com.api.bookstoremanager.author.dto.AuthorDTO;
import com.api.bookstoremanager.author.service.AuthorServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/authors")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorController implements AuthorControllerDocs{

    @Autowired
    private AuthorServiceImpl service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO create(@RequestBody @Valid AuthorDTO authorDTO) {
        return service.create(authorDTO);
    }

    @GetMapping("/{id}")
    public AuthorDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }
//    @PostMapping
//    public ResponseEntity<AuthorDTO> create(@Valid @RequestBody AuthorDTO authorDTO){
//        var createAuthor = service.create(authorDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createAuthor);
//    }
}
