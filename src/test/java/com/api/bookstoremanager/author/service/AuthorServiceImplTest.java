package com.api.bookstoremanager.author.service;

import com.api.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.api.bookstoremanager.author.dto.AuthorDTO;
import com.api.bookstoremanager.author.entity.Author;
import com.api.bookstoremanager.author.exception.AuthorAlreadyExistsException;
import com.api.bookstoremanager.author.mapper.AuthorMapper;
import com.api.bookstoremanager.author.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários em AuthorServiceImplTest")
public class AuthorServiceImplTest {

    public static final AuthorMapper mapper = AuthorMapper.INSTANCE;

    @Mock
    private AuthorRepository repository;

    @InjectMocks
    private AuthorServiceImpl service;

    private AuthorDTOBuilder authorDtoBuilder;

    @BeforeEach
    void setUp() {
        authorDtoBuilder = AuthorDTOBuilder.builder().build();
    }

    @Test
    @DisplayName("criar um novo Author, se não houver nenhum cadastrado com o mesmo nome")
    void when_New_Author_Is_Informed_Then_It_Should_Be_Created() {
        // @Given
        AuthorDTO expectedAuthorToCreateDTO = authorDtoBuilder.buildAuthorDTO();
        Author expectedCreatedAuthor = mapper.toModel(expectedAuthorToCreateDTO);

        // @When
        when(repository.save(expectedCreatedAuthor)).thenReturn(expectedCreatedAuthor);
        when(repository.findByName(expectedAuthorToCreateDTO.getName())).thenReturn(Optional.empty());

        AuthorDTO createdAuthorDTO = service.create(expectedAuthorToCreateDTO);
        // @Then
        assertThat(createdAuthorDTO, is(equalTo(expectedAuthorToCreateDTO)));
    }

    @Test
    @DisplayName("Lança a exceção quando encontrado que o author a ser incluido já existe")
    void when_Existing_Author_Is_Informed_Then_An_Exception_It_Should_Be_Throw() {
        // @Given
        AuthorDTO expectedAuthorToCreateDTO = authorDtoBuilder.buildAuthorDTO();
        Author expectedCreatedAuthor = mapper.toModel(expectedAuthorToCreateDTO);

        // @When
        when(repository.findByName(expectedAuthorToCreateDTO.getName())).thenReturn(Optional.of(expectedCreatedAuthor));

         // @Then
        assertThrows(AuthorAlreadyExistsException.class, ()-> service.create(expectedAuthorToCreateDTO));
    }

    @Test
    @DisplayName("Pesquisa um Author pelo id")
    void when_Valid_ID_Is_Given_Then_An_Author_Should_Be_Returned() {
        // @Given
        AuthorDTO expectedFoundAuthorDTO = authorDtoBuilder.buildAuthorDTO();
        Author expectedFoundAuthor = mapper.toModel(expectedFoundAuthorDTO);

        // @When
        when(repository.findById(expectedFoundAuthorDTO.getId())).thenReturn(Optional.of(expectedFoundAuthor));
       var foundAuthorDTO= service.findById(expectedFoundAuthorDTO.getId());

        // @Then
        assertThat(foundAuthorDTO, is(equalTo(expectedFoundAuthorDTO)));
    }

    @Test
    @DisplayName("Lista todos os Authores")
    void when_List_Author_Is_Called_Then_A_List_Authors_Should_Be_Returned() {
        // @Given
        AuthorDTO expectedFoundAuthorDTO = authorDtoBuilder.buildAuthorDTO();
        Author expectedFoundAuthor = mapper.toModel(expectedFoundAuthorDTO);

        // @When
        when(repository.findAll()).thenReturn(Collections.singletonList(expectedFoundAuthor));
        var foundAuthorsDTO= service.findAll();

        // @Then
        assertThat(foundAuthorsDTO.size(), is(1));
        assertThat(foundAuthorsDTO.get(0), is(equalTo(expectedFoundAuthorDTO)));
    }

    @Test
    @DisplayName("Lista vazia se não houver authores cadastrados")
    void when_List_Author_Ia_Called_Then_An_Empty_List_Authors_Should_Be_Returned() {
        // @Given

        // @When
        when(repository.findAll()).thenReturn(Collections.EMPTY_LIST);
        var foundAuthorsDTO= service.findAll();

        // @Then
        assertThat(foundAuthorsDTO.size(), is(0));
    }

    @Test
    @DisplayName("Exclui authores cadastrados pelo ID")
    void when_Valid_Author_Id_Is_Given_Then_It_Should_Be_Deleted() {
        // @Given
        AuthorDTO expectedDeletedAuthorDTO = authorDtoBuilder.buildAuthorDTO();
        Author expectedDeletedAuthor = mapper.toModel(expectedDeletedAuthorDTO);

        // @When
        var expectedDeleteAuthorId = expectedDeletedAuthorDTO.getId();
        doNothing().when(repository).deleteById(expectedDeleteAuthorId);
        when(repository.findById(expectedDeleteAuthorId)).thenReturn(Optional.of(expectedDeletedAuthor));
        service.delete(expectedDeleteAuthorId);

        // @Then
        verify(repository,times(1)).deleteById(expectedDeleteAuthorId);
        verify(repository,times(1)).findById(expectedDeleteAuthorId);
    }
}
