package com.api.bookstoremanager.service;

import com.api.bookstoremanager.dto.BookDTO;
import com.api.bookstoremanager.dto.MessageResponseDTO;

import com.api.bookstoremanager.entity.Book;
import com.api.bookstoremanager.mapper.BookMapper;
import com.api.bookstoremanager.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;


@Service
public class BookService {

    private BookRepository bookRepository;

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    @Autowired
    public BookService(BookRepository bookRepository) {

        this.bookRepository = bookRepository;
    }

    @PostMapping
    public MessageResponseDTO create(BookDTO bookDTO){

        Book bookToSave = bookMapper.toModel(bookDTO);

        Book savedBook = bookRepository.save(bookToSave);
        return MessageResponseDTO.builder()
                .message("Book created with ID " + savedBook.getId())
                .build();
    }

    public BookDTO findById(Long id) {
        Optional <Book> optionalBook = bookRepository.findById(id);
        return bookMapper.toDTO(optionalBook.get());
    }
}