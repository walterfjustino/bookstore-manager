package com.api.bookstoremanager.service;

import com.api.bookstoremanager.BookUtils;
import com.api.bookstoremanager.dto.BookDTO;
import com.api.bookstoremanager.entity.Book;
import com.api.bookstoremanager.exception.BookNotFoundException;
import com.api.bookstoremanager.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void whenGivenExistingIdThenReturnThisBook() throws BookNotFoundException {
        Book expectedFoundBook = BookUtils.createFakeBook();

        Mockito.when(bookRepository.findById(expectedFoundBook.getId())).thenReturn(Optional.of(expectedFoundBook));

        BookDTO bookDTO = bookService.findById(expectedFoundBook.getId());

        assertEquals(expectedFoundBook.getName(),bookDTO.getName());
        assertEquals(expectedFoundBook.getIsbn(),bookDTO.getIsbn());
        assertEquals(expectedFoundBook.getPublisherName(),bookDTO.getPublisherName());
    }
}