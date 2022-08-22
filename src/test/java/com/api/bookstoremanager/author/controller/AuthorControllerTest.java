package com.api.bookstoremanager.author.controller;

import com.api.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.api.bookstoremanager.author.service.AuthorServiceImpl;
import com.api.bookstoremanager.author.utils.JsonCoversionUtils;
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
    void when_POST_Is_Called_Then_Status_Created_It_Should_Be_Returned() throws Exception {
        var expectedAuthorCreatedDTO= authorDTOBuilder.buildAuthorDTO();

        Mockito.when(service.create(expectedAuthorCreatedDTO)).thenReturn(expectedAuthorCreatedDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(AUTHOR_API_URL_PATH )
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonCoversionUtils.asJsonString(expectedAuthorCreatedDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(expectedAuthorCreatedDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is(expectedAuthorCreatedDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", Is.is(expectedAuthorCreatedDTO.getAge())));
    }

    @Test
    void when_POST_Is_Called_Without_Required_Fields_Then_Bad_Request_Status_It_Should_Be_Informed() throws Exception {
        var expectedAuthorCreatedDTO= authorDTOBuilder.buildAuthorDTO();
        expectedAuthorCreatedDTO.setName(null);
        mockMvc.perform(MockMvcRequestBuilders.post(AUTHOR_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonCoversionUtils.asJsonString(expectedAuthorCreatedDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}

