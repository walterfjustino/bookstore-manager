package com.api.bookstoremanager.author.service;

import com.api.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.api.bookstoremanager.author.mapper.AuthorMapper;
import com.api.bookstoremanager.author.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Autowired
    private AuthorMapper mapper;

    @Mock
    private AuthorRepository repository;

    @InjectMocks
    private AuthorService service;

    private AuthorDTOBuilder authorDTOBuilder;

    @BeforeEach
    void setUp(){
        authorDTOBuilder = AuthorDTOBuilder.builder().build();
        var authorDTO = authorDTOBuilder.buildAuthorDTO();

    }

}
