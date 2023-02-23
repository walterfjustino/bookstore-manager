package com.api.bookstoremanager.book.service;

import com.api.bookstoremanager.BookUtils;
import com.api.bookstoremanager.author.entity.Author;
import com.api.bookstoremanager.author.service.AuthorServiceImpl;
import com.api.bookstoremanager.book.builder.BookRequestDTOBuilder;
import com.api.bookstoremanager.book.builder.BookResponseDTOBuilder;
import com.api.bookstoremanager.books.dto.BookDTO;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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
    void when_List_Book_Is_Called_Then_An_Empty_List_ItShould_Be_Returned() {
        //@Given

        //@When
        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findAllByUser(any(User.class))).thenReturn(Collections.emptyList());

        List<BookResponseDTO> returnedBookResponseList = bookService.findAllByUser(authenticatedUser);

        //@Then
        MatcherAssert.assertThat(returnedBookResponseList.size(), Matchers.is(0));
    }
        //@Then
    //    @Test
//    void whenGivenExistingIdThenReturnThisBook() throws BookNotFoundException {
//        Book expectedFoundBook = BookUtils.createFakeBook();
//
//        when(bookRepository.findById(expectedFoundBook.getId())).thenReturn(Optional.of(expectedFoundBook));
//
//        BookDTO bookDTO = bookService.findById(expectedFoundBook.getId());
//
//        assertEquals(expectedFoundBook.getName(),bookDTO.getName());
//        assertEquals(expectedFoundBook.getIsbn(),bookDTO.getIsbn());
//        assertEquals(expectedFoundBook.getPublisher(),bookDTO.getPublisherName());
//    }
//
//    @Test
//    void whenGivenUnexistingIdThenNotFindThrowAnException() {
//
//        var invalidId = 10L;
//
//        when(bookRepository.findById(invalidId)).thenReturn(Optional.ofNullable(any(Book.class)));
//
//        assertThrows(BookNotFoundException.class, () ->bookService.findById(invalidId));
//    }
}