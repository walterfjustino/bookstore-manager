package com.api.bookstoremanager.books.service;

import com.api.bookstoremanager.books.dto.BookDTO;
import com.api.bookstoremanager.books.entity.Book;
import com.api.bookstoremanager.books.exception.BookNotFoundException;
import com.api.bookstoremanager.books.mapper.BookMapper;
import com.api.bookstoremanager.books.repository.BookRepository;
import com.api.bookstoremanager.dto.MessageResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper mapper;

    @Override
    public MessageResponseDTO create(BookDTO bookDTO){

        Book bookToSave = mapper.toModel(bookDTO);

        Book savedBook = bookRepository.save(bookToSave);
        return MessageResponseDTO.builder()
                .message("Book created with ID " + savedBook.getId())
                .build();
    }


    @Override
    public BookDTO findById(Long id) throws BookNotFoundException {
        var book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        return mapper.toDTO(book);
    }


    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}