package com.api.bookstoremanager.service;

import com.api.bookstoremanager.dto.BookDTO;
import com.api.bookstoremanager.dto.MessageResponseDTO;

import com.api.bookstoremanager.entity.Book;
import com.api.bookstoremanager.exception.BookNotFoundException;
import com.api.bookstoremanager.mapper.BookMapper;
import com.api.bookstoremanager.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @PostMapping
    public MessageResponseDTO create(BookDTO bookDTO){

        Book bookToSave = bookMapper.toModel(bookDTO);

        Book savedBook = bookRepository.save(bookToSave);
        return MessageResponseDTO.builder()
                .message("Book created with ID " + savedBook.getId())
                .build();
    }

    public BookDTO findById(Long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        return bookMapper.toDTO(book);
    }
}