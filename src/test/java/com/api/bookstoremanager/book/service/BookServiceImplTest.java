package com.api.bookstoremanager.book.service;

import com.api.bookstoremanager.author.entity.Author;
import com.api.bookstoremanager.author.service.AuthorServiceImpl;
import com.api.bookstoremanager.book.builder.BookRequestDTOBuilder;
import com.api.bookstoremanager.book.builder.BookResponseDTOBuilder;
import com.api.bookstoremanager.books.dto.BookRequestDTO;
import com.api.bookstoremanager.books.dto.BookResponseDTO;
import com.api.bookstoremanager.books.entity.Book;
import com.api.bookstoremanager.books.exception.BookAlreadyExistsException;
import com.api.bookstoremanager.books.exception.BookNotFoundException;
import com.api.bookstoremanager.books.mapper.BookMapper;
import com.api.bookstoremanager.books.repository.BookRepository;
import com.api.bookstoremanager.books.service.BookServiceImpl;
import com.api.bookstoremanager.publishers.entity.Publisher;
import com.api.bookstoremanager.publishers.service.PublisherServiceImpl;
import com.api.bookstoremanager.users.dto.AuthenticatedUser;
import com.api.bookstoremanager.users.entity.User;
import com.api.bookstoremanager.users.service.UserServiceImpl;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários em BookServiceImplTest")
public class BookServiceImplTest {

    private final BookMapper mapper = BookMapper.INSTANCE;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private AuthorServiceImpl authorService;
    @Mock
    private PublisherServiceImpl publisherService;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookRequestDTOBuilder bookRequestDTOBuilder;

    private BookResponseDTOBuilder bookResponseDTOBuilder;

    private AuthenticatedUser authenticatedUser;

    @BeforeEach
    void setUp() {
        bookRequestDTOBuilder = BookRequestDTOBuilder.builder().build();
        bookResponseDTOBuilder = BookResponseDTOBuilder.builder().build();
        authenticatedUser = new AuthenticatedUser("jiraya", "AB123456789", "ADMIN");
    }

    @Test
    @DisplayName("Cria um novo Livro, se não houver nenhum cadastrado com o NOME e ISBN e se estiver authenticado com o Username")
    void when_New_Book_Is_Informed_Then_It_Should_Be_Created() {
        //@Given
        BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedCreatedBookDTO = bookResponseDTOBuilder.buildBookResponse();

        //@When
        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByNameAndIsbnAndUser(
                eq(expectedBookToCreateDTO.getName()),
                eq(expectedBookToCreateDTO.getIsbn()),
                any(User.class))).thenReturn(Optional.empty());
        when(authorService.verifyAndGetAuthorIfExists(expectedBookToCreateDTO.getAuthorId())).thenReturn(new Author());
        when(publisherService.verifyIfPublisherExistsAndGet(expectedBookToCreateDTO.getPublisherId())).thenReturn(new Publisher());
        when(bookRepository.save(any(Book.class))).thenReturn(mapper.toModel(expectedCreatedBookDTO));

        BookResponseDTO createdBookResponseDTO = bookService.create(authenticatedUser, expectedBookToCreateDTO);

        //@Then
        MatcherAssert.assertThat(createdBookResponseDTO, Matchers.is(Matchers.equalTo(expectedCreatedBookDTO)));
    }

    @Test
    @DisplayName("Lança uma Exceção se não estiver authenticado com o Username ou não houver nenhum cadastrado com o NOME e ISBN ")
    void when_Existing_Book_Is_Informed_To_Create_Then_An_Exception_Should_Be_Thrown() {
        //@Given
        BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedCreatedBookDTO = bookResponseDTOBuilder.buildBookResponse();
        Book expectedDuplicatedBook = mapper.toModel(expectedCreatedBookDTO);

        //@When
        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByNameAndIsbnAndUser(
                eq(expectedBookToCreateDTO.getName()),
                eq(expectedBookToCreateDTO.getIsbn()),
                any(User.class))).thenReturn(Optional.of(expectedDuplicatedBook));

        //@Then
        assertThrows(BookAlreadyExistsException.class, () -> bookService.create(authenticatedUser, expectedBookToCreateDTO));
    }

    @Test
    @DisplayName("Pesquisa um livro por ID, se estiver Authenticado com o Username")
    void when_Existing_Book_Is_Informed_Then_A_Book_Should_Be_Returned() {
        //@Given
        BookRequestDTO expectedBookToFindDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedFoundBookDTO = bookResponseDTOBuilder.buildBookResponse();
        Book expectedFoundBook = mapper.toModel(expectedFoundBookDTO);

        //@When
        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByIdAndUser(
                eq(expectedBookToFindDTO.getId()),
                any(User.class))).thenReturn(Optional.of(expectedFoundBook));

        BookResponseDTO foundBookDTO = bookService.findById(authenticatedUser, expectedFoundBook.getId());
        //@Then
        MatcherAssert.assertThat(foundBookDTO, Matchers.is(Matchers.equalTo(expectedFoundBookDTO)));

    }

    @Test
    @DisplayName("Lança uma exceção se não encontrar o ID se estiver Authenticado")
    void when_Not_Existing_Book_Is_Informed_Then_An_Exception_Should_Be_Thrown() {
        //@Given
        BookRequestDTO expectedBookToFindDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedFoundBookDTO = bookResponseDTOBuilder.buildBookResponse();
        Book expectedFoundBook = mapper.toModel(expectedFoundBookDTO);

        //@When
        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByIdAndUser(
                eq(expectedBookToFindDTO.getId()),
                any(User.class))).thenReturn(Optional.empty());

        //@Then
        assertThrows(BookNotFoundException.class, () -> bookService.findById(authenticatedUser, expectedFoundBook.getId()));
    }

    @Test
    @DisplayName("Retorna Lista com todos os livros se estiver Authenticado com o Username")
    void when_List_Book_Is_Called_Then_ItShould_Be_Returned() {
        //@Given
        BookRequestDTO expectedBookToFindDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedFoundBookDTO = bookResponseDTOBuilder.buildBookResponse();
        Book expectedFoundBook = mapper.toModel(expectedFoundBookDTO);

        //@When
        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findAllByUser(any(User.class))).thenReturn(Collections.singletonList(expectedFoundBook));

        List<BookResponseDTO> returnedBookResponseList = bookService.findAllByUser(authenticatedUser);

        //@Then
        MatcherAssert.assertThat(returnedBookResponseList.size(), Matchers.is(1));
        MatcherAssert.assertThat(returnedBookResponseList.get(0), Matchers.is(Matchers.equalTo(expectedFoundBookDTO)));
    }

    @Test
    @DisplayName("Retorna Lista vazia se não houver livros cadastrados se estiver Authenticado com o Username")
    void when_List_Book_Is_Called_Then_An_Empty_List_It_Should_Be_Returned() {
        //@Given

        //@When
        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findAllByUser(any(User.class))).thenReturn(Collections.emptyList());

        List<BookResponseDTO> returnedBookResponseList = bookService.findAllByUser(authenticatedUser);

        //@Then
        MatcherAssert.assertThat(returnedBookResponseList.size(), Matchers.is(0));
    }

    @Test
    @DisplayName("Exclui um livro por ID se estiver Authenticado com o Username")
    void when_Existing_Book_Is_Informed_Then_It_Should_Be_Deleted() {
        //@Given
        BookResponseDTO expectedBookToDeleteDTO = bookResponseDTOBuilder.buildBookResponse();
        Book expectedBookToDelete = mapper.toModel(expectedBookToDeleteDTO);

        //@When
        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByIdAndUser(eq(expectedBookToDeleteDTO.getId()), any(User.class)))
                .thenReturn(Optional.of(expectedBookToDelete));

        doNothing().when(bookRepository).deleteByIdAndUser(eq(expectedBookToDeleteDTO.getId()), any(User.class));
        bookService.deleteByIdAndUser(authenticatedUser, expectedBookToDeleteDTO.getId());

        //@Then
        verify(bookRepository, times(1)).deleteByIdAndUser(eq(expectedBookToDeleteDTO.getId()),any(User.class));
    }

    @Test
    @DisplayName("Lança uma exceção se o ID do livro for inválido e se estiver Authenticado com o Username")
    void when_Not_Existing_Book_Is_Informed_Then_An_Exception_It_Should_Be_Thrown() {
        //@Given
        BookResponseDTO expectedBookToDeleteDTO = bookResponseDTOBuilder.buildBookResponse();
        Book expectedBookToDelete = mapper.toModel(expectedBookToDeleteDTO);

        //@When
        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByIdAndUser(eq(expectedBookToDeleteDTO.getId()), any(User.class)))
                .thenReturn(Optional.empty());

        //@Then
        assertThrows(BookNotFoundException.class, () -> bookService.deleteByIdAndUser(authenticatedUser, expectedBookToDelete.getId()));
    }

    @Test
    @DisplayName("Atualiza um Livro se o ID for válido e se estiver authenticado com o Username")
    void when_Existing_Book_Is_Informed_Then_It_Should_Be_Updated() {
        //@Given
        BookRequestDTO expectedBookToUpdateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedUpdatedBookDTO = bookResponseDTOBuilder.buildBookResponse();
        Book expectedUpdatedBook = mapper.toModel(expectedUpdatedBookDTO);

        //@When
        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByIdAndUser(eq(expectedBookToUpdateDTO.getId()), any(User.class)))
                .thenReturn(Optional.of(expectedUpdatedBook));
        when(authorService.verifyAndGetAuthorIfExists(expectedBookToUpdateDTO.getAuthorId())).thenReturn(new Author());
        when(publisherService.verifyIfPublisherExistsAndGet(expectedBookToUpdateDTO.getPublisherId())).thenReturn(new Publisher());
        when(bookRepository.save(any(Book.class))).thenReturn(expectedUpdatedBook);

        BookResponseDTO updatedBookResponseDTO = bookService.updateByUser(authenticatedUser, expectedBookToUpdateDTO.getId(), expectedBookToUpdateDTO);
        //@Then
        MatcherAssert.assertThat(updatedBookResponseDTO, Matchers.is(Matchers.equalTo(expectedUpdatedBookDTO)));
    }

    @Test
    @DisplayName("Lança uma exceção se o ID for inválido e se estiver authenticado com o Username")
    void when_Not_Existing_Book_Is_Informed_Then_An_Exception_It_Should_Be_Throw() {
        //@Given
        BookRequestDTO expectedBookToUpdateDTO = bookRequestDTOBuilder.buildRequestBookDTO();

        //@When
        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByIdAndUser(eq(expectedBookToUpdateDTO.getId()), any(User.class)))
                .thenReturn(Optional.empty());

        //@Then
        assertThrows(BookNotFoundException.class, () -> bookService.updateByUser(authenticatedUser, expectedBookToUpdateDTO.getId(), expectedBookToUpdateDTO));
    }
}
