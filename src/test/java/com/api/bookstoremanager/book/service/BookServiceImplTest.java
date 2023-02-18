package com.api.bookstoremanager.book.service;

import com.api.bookstoremanager.BookUtils;
import com.api.bookstoremanager.author.service.AuthorServiceImpl;
import com.api.bookstoremanager.book.builder.BookRequestDTOBuilder;
import com.api.bookstoremanager.book.builder.BookResponseDTOBuilder;
import com.api.bookstoremanager.books.dto.BookDTO;
import com.api.bookstoremanager.books.entity.Book;
import com.api.bookstoremanager.books.exception.BookNotFoundException;
import com.api.bookstoremanager.books.mapper.BookMapper;
import com.api.bookstoremanager.books.repository.BookRepository;
import com.api.bookstoremanager.books.service.BookServiceImpl;
import com.api.bookstoremanager.publishers.service.PublisherServiceImpl;
import com.api.bookstoremanager.users.dto.AuthenticatedUser;
import com.api.bookstoremanager.users.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
    void name() {
    }

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