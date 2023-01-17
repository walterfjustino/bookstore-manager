package com.api.bookstoremanager.controller;

import com.api.bookstoremanager.BookUtils;
import com.api.bookstoremanager.books.controller.BookController;
import com.api.bookstoremanager.books.dto.BookDTO;
import com.api.bookstoremanager.dto.MessageResponseDTO;
import com.api.bookstoremanager.books.service.BookService;
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

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {


    private MockMvc mockMvc; //simula às operações no nosso controlador

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) ->  new MappingJackson2JsonView())
                .build();
    }

    @Test
    void testWhenPostIsCalledThenABookShouldBeCreated() throws Exception {
        BookDTO bookDTO = BookUtils.createFakeBookDTO();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Book created with ID" + bookDTO.getId())
                .build();

        Mockito.when(bookService.create(bookDTO)).thenReturn(expectedMessageResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
        .contentType(MediaType.APPLICATION_JSON)
        .content(BookUtils.asJsonString(bookDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Is.is(expectedMessageResponse.getMessage())));
    }


    @Test
    void testWhenPostWithInvalidISBNIsCalledThenBadRequestShouldBeReturn() throws Exception {
        BookDTO bookDTO = BookUtils.createFakeBookDTO();
        bookDTO.setIsbn("Invalid ISBN");


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BookUtils.asJsonString(bookDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
}
