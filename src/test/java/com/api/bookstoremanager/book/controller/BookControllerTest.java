package com.api.bookstoremanager.book.controller;

import com.api.bookstoremanager.BookUtils;
import com.api.bookstoremanager.book.builder.BookRequestDTOBuilder;
import com.api.bookstoremanager.book.builder.BookResponseDTOBuilder;
import com.api.bookstoremanager.books.controller.BookController;
import com.api.bookstoremanager.books.dto.BookDTO;
import com.api.bookstoremanager.books.dto.BookRequestDTO;
import com.api.bookstoremanager.books.dto.BookResponseDTO;
import com.api.bookstoremanager.dto.MessageResponseDTO;
import com.api.bookstoremanager.books.service.BookService;
import com.api.bookstoremanager.users.dto.AuthenticatedUser;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.api.bookstoremanager.utils.JsonCoversionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    private static final String BOOKS_API_URL_PATH = "/api/v1/books";
    @Mock
    private BookService bookService;
    @InjectMocks
    private BookController bookController;
    private MockMvc mockMvc; //simula às operações no nosso controlador
    private BookRequestDTOBuilder bookRequestDTOBuilder;
    private BookResponseDTOBuilder bookResponseDTOBuilder;

    @BeforeEach
    void setUp() {
        bookRequestDTOBuilder = BookRequestDTOBuilder.builder().build();
        bookResponseDTOBuilder = BookResponseDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) ->  new MappingJackson2JsonView())
                .build();
    }


    @Test
    void whenPOSTIsCalledThenCreatedStatusShouldBeReturned() throws Exception {
        BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedCreatedBookDTO = bookResponseDTOBuilder.buildBookResponse();

        when(bookService.create(any(AuthenticatedUser.class), eq(expectedBookToCreateDTO))).thenReturn(expectedCreatedBookDTO);

        mockMvc.perform(post(BOOKS_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedBookToCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(expectedCreatedBookDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedCreatedBookDTO.getName())))
                .andExpect(jsonPath("$.isbn", is(expectedCreatedBookDTO.getIsbn())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldsThenBadRequestShouldBeReturned() throws Exception {
        BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        expectedBookToCreateDTO.setIsbn(null);

        mockMvc.perform(post(BOOKS_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedBookToCreateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void when_GET_With_Valid_ID_Is_Called_Then_Status_OK_It_Should_Be_Returned() throws Exception {
        BookRequestDTO expectedBookToFindDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedFoundBookDTO = bookResponseDTOBuilder.buildBookResponse();

        when(bookService.findById(any(AuthenticatedUser.class), eq(expectedBookToFindDTO.getId()))).thenReturn(expectedFoundBookDTO);

        mockMvc.perform(get(BOOKS_API_URL_PATH + "/" + expectedBookToFindDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedFoundBookDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedFoundBookDTO.getName())))
                .andExpect(jsonPath("$.isbn", is(expectedFoundBookDTO.getIsbn())));
    }

    @Test
    void when_GET_List_Is_Called_Then_Status_OK_It_Should_Be_Returned() throws Exception {
        BookResponseDTO expectedFoundBookDTO = bookResponseDTOBuilder.buildBookResponse();

        when(bookService.findAllByUser(any(AuthenticatedUser.class))).thenReturn(Collections.singletonList(expectedFoundBookDTO));

        mockMvc.perform(get(BOOKS_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(expectedFoundBookDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(expectedFoundBookDTO.getName())))
                .andExpect(jsonPath("$[0].isbn", is(expectedFoundBookDTO.getIsbn())));
    }

//    @Test
//    void testWhenPostIsCalledThenABookShouldBeCreated() throws Exception {
//        BookDTO bookDTO = BookUtils.createFakeBookDTO();
//        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
//                .message("Book created with ID" + bookDTO.getId())
//                .build();
//
//        Mockito.when(bookService.create(bookDTO)).thenReturn(expectedMessageResponse);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
//        .contentType(MediaType.APPLICATION_JSON)
//        .content(BookUtils.asJsonString(bookDTO)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Is.is(expectedMessageResponse.getMessage())));
//    }
//
//
//    @Test
//    void testWhenPostWithInvalidISBNIsCalledThenBadRequestShouldBeReturn() throws Exception {
//        BookDTO bookDTO = BookUtils.createFakeBookDTO();
//        bookDTO.setIsbn("Invalid ISBN");
//
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(BookUtils.asJsonString(bookDTO)))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//
//    }
}
