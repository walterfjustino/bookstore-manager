package com.api.bookstoremanager.author.service;

import com.api.bookstoremanager.author.mapper.AuthorMapper;
import com.api.bookstoremanager.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private AuthorMapper mapper;


}
