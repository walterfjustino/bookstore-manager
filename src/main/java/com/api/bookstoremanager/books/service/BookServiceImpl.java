package com.api.bookstoremanager.books.service;

import com.api.bookstoremanager.author.service.AuthorServiceImpl;
import com.api.bookstoremanager.books.dto.BookDTO;
import com.api.bookstoremanager.books.dto.BookRequestDTO;
import com.api.bookstoremanager.books.entity.Book;
import com.api.bookstoremanager.books.exception.BookNotFoundException;
import com.api.bookstoremanager.books.mapper.BookMapper;
import com.api.bookstoremanager.books.repository.BookRepository;
import com.api.bookstoremanager.dto.MessageResponseDTO;
import com.api.bookstoremanager.publishers.service.PublisherServiceImpl;
import com.api.bookstoremanager.users.dto.AuthenticatedUser;
import com.api.bookstoremanager.users.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))

public class BookServiceImpl implements BookService {

    private final BookMapper mapper = BookMapper.INSTANCE;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private AuthorServiceImpl authorService;
    @Autowired
    private PublisherServiceImpl publisherService;



//    @Override
//    public MessageResponseDTO create(,BookDTO bookDTO){
//
//        Book bookToSave = mapper.toModel(bookDTO);
//
//        Book savedBook = bookRepository.save(bookToSave);
//        return MessageResponseDTO.builder()
//                .message("Book created with ID " + savedBook.getId())
//                .build();
//    }


    @Override
    public MessageResponseDTO create(AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO) {
        Book bookToSave = mapper.toModel(bookDTO);

        Book savedBook = bookRepository.save(bookToSave);
        return MessageResponseDTO.builder()
                .message("Book created with ID " + savedBook.getId())
                .build();
        return null;
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