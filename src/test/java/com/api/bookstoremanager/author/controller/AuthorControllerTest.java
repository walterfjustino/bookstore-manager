package com.api.bookstoremanager.author.controller;

import com.api.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.api.bookstoremanager.author.service.AuthorServiceImpl;
import com.api.bookstoremanager.author.utils.JsonCoversionUtils;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

    private static final String AUTHOR_API_URL_PATH = "/api/v1/authors";
    @Mock
    private AuthorServiceImpl service;

    @InjectMocks
    private AuthorController authorController;

    private MockMvc mockMvc;

    private AuthorDTOBuilder authorDTOBuilder;

    @BeforeEach
    void setUp() {
        authorDTOBuilder = AuthorDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(authorController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    @DisplayName("criar um novo usuario, se não houver nenhum cadastrado com o mesmo nome")
    void when_POST_Is_Called_Then_Status_Created_It_Should_Be_Returned() throws Exception {
        var expectedAuthorCreatedDTO= authorDTOBuilder.buildAuthorDTO();

        when(service.create(expectedAuthorCreatedDTO)).thenReturn(expectedAuthorCreatedDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(AUTHOR_API_URL_PATH )
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonCoversionUtils.asJsonString(expectedAuthorCreatedDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(expectedAuthorCreatedDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is(expectedAuthorCreatedDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", Is.is(expectedAuthorCreatedDTO.getAge())));
    }

    @Test
    @DisplayName("Lança a exceção quando encontrado que o usuario a ser incluido já existe")
    void when_POST_Is_Called_Without_Required_Fields_Then_Bad_Request_Status_It_Should_Be_Informed() throws Exception {
        var expectedAuthorCreatedDTO= authorDTOBuilder.buildAuthorDTO();
        expectedAuthorCreatedDTO.setName(null);
        mockMvc.perform(MockMvcRequestBuilders.post(AUTHOR_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonCoversionUtils.asJsonString(expectedAuthorCreatedDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Pesquisa um Author pelo id")
    void when_GET_With_Valid_ID_Is_Called_Then_Status_OK_It_Should_Be_Returned() throws Exception {
        var expectedFoundAuthorDTO = authorDTOBuilder.buildAuthorDTO();

        when(service.findById(expectedFoundAuthorDTO.getId())).thenReturn(expectedFoundAuthorDTO);
        mockMvc.perform(MockMvcRequestBuilders.get(AUTHOR_API_URL_PATH + "/" + expectedFoundAuthorDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(expectedFoundAuthorDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is(expectedFoundAuthorDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", Is.is(expectedFoundAuthorDTO.getAge())));

    }

    @Test
    @DisplayName("Lista todos os Authores")
    void when_GET_List_Author_Is_Called_Then_A_List_Authors_Should_Be_Returned() throws Exception {
        var expectedFoundAuthorDTO = authorDTOBuilder.buildAuthorDTO();

        when(service.findAll()).thenReturn(Collections.singletonList(expectedFoundAuthorDTO));
        mockMvc.perform(MockMvcRequestBuilders.get(AUTHOR_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Is.is(expectedFoundAuthorDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Is.is(expectedFoundAuthorDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age", Is.is(expectedFoundAuthorDTO.getAge())));

    }

}

