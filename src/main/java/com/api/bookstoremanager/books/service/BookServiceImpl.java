package com.api.bookstoremanager.books.service;

import com.api.bookstoremanager.author.entity.Author;
import com.api.bookstoremanager.author.service.AuthorServiceImpl;
import com.api.bookstoremanager.books.dto.BookDTO;
import com.api.bookstoremanager.books.dto.BookRequestDTO;
import com.api.bookstoremanager.books.dto.BookResponseDTO;
import com.api.bookstoremanager.books.entity.Book;
import com.api.bookstoremanager.books.exception.BookAlreadyExistsException;
import com.api.bookstoremanager.books.exception.BookNotFoundException;
import com.api.bookstoremanager.books.mapper.BookMapper;
import com.api.bookstoremanager.books.repository.BookRepository;
import com.api.bookstoremanager.dto.MessageResponseDTO;
import com.api.bookstoremanager.publishers.entity.Publisher;
import com.api.bookstoremanager.publishers.service.PublisherServiceImpl;
import com.api.bookstoremanager.users.dto.AuthenticatedUser;
import com.api.bookstoremanager.users.entity.User;
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
    public BookResponseDTO create(AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO) {
        var foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        verifyIfBookIsAlreadyRegistered(bookRequestDTO, foundAuthenticatedUser);
        var foundAuthor = authorService.verifyAndGetAuthorIfExists(bookRequestDTO.getAuthorId());
        var foundPublisher = publisherService.verifyIfPublisherExistsAndGet(bookRequestDTO.getPublisherId());
        Book bookToSave = mapper.toModel(bookRequestDTO);
        bookToSave.setUser(foundAuthenticatedUser);
        bookToSave.setAuthor(foundAuthor);
        bookToSave.setPublisher(foundPublisher);
        Book savedBook = bookRepository.save(bookToSave);
        return mapper.toDTO(savedBook);
    }

    @Override
    public BookResponseDTO findById(AuthenticatedUser authenticatedUser, Long id) throws BookNotFoundException {
        var foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        return bookRepository.findByIdAndUser(id, foundAuthenticatedUser)
                .map(mapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    private void verifyIfBookIsAlreadyRegistered(BookRequestDTO bookRequestDTO, User foundUser) {
        bookRepository.findByNameAndIsbnAndUser(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundUser)
                .ifPresent(duplicatedBook -> {
                    throw new BookAlreadyExistsException(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundUser.getUsername());});
    }

//    @Override
//    public BookDTO findById(Long id) throws BookNotFoundException {
//        var book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
//        return null;
//    }


    @Override
    public List<BookResponseDTO> findAllByUser(AuthenticatedUser authenticatedUser) {
        var foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        return bookRepository.findAllByUser(foundAuthenticatedUser)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}