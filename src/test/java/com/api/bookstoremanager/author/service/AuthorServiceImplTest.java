package com.api.bookstoremanager.author.service;

import com.api.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.api.bookstoremanager.author.dto.AuthorDTO;
import com.api.bookstoremanager.author.entity.Author;
import com.api.bookstoremanager.author.mapper.AuthorMapper;
import com.api.bookstoremanager.author.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@DisplayName("AuthorServiceImplTest")
public class AuthorServiceImplTest {

    public static final AuthorMapper mapper = AuthorMapper.INSTANCE;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl service;

    private AuthorDTOBuilder authorDtoBuilder;

    @BeforeEach
    void setUp() {
        authorDtoBuilder = AuthorDTOBuilder.builder().build();
    }

    @Test
    void when_New_Author_Is_Informed_Then_It_Should_Be_Created() {
        // @Given
        AuthorDTO expectedAuthorToCreateDTO = authorDtoBuilder.buildAuthorDTO();
        Author expectedCreatedAuthor = mapper.toModel(expectedAuthorToCreateDTO);

        // @When
        when(authorRepository.save(expectedCreatedAuthor)).thenReturn(expectedCreatedAuthor);
        when(authorRepository.findByName(expectedAuthorToCreateDTO.getName())).thenReturn(Optional.empty());

        AuthorDTO createdAuthorDTO = service.create(expectedAuthorToCreateDTO);
        // @Then
        assertThat(createdAuthorDTO, is(equalTo(expectedAuthorToCreateDTO)));
    }
}