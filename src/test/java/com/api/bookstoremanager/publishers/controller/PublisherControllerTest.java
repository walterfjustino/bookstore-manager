package com.api.bookstoremanager.publishers.controller;

import com.api.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.api.bookstoremanager.publishers.service.PublisherServiceImpl;
import com.api.bookstoremanager.utils.JsonCoversionUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PublisherControllerTest {

  private final static String PUBLISHERS_API_URL_PATH = "/api/v1/publishers";

  private MockMvc mockMvc;

  @Mock
  private PublisherServiceImpl service;

  @InjectMocks
  private PublisherController publisherController;

  private PublisherDTOBuilder publisherDTOBuilder;

  @BeforeEach
  void setUp() {
    publisherDTOBuilder = PublisherDTOBuilder.builder().build();
    mockMvc = MockMvcBuilders.standaloneSetup(publisherController)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setViewResolvers(((s, locale) -> new MappingJackson2JsonView()))
            .build();
  }

  @Test
  @DisplayName("cria um novo Publisher, se não houver nenhum cadastrado com o mesmo nome")
  void when_Post_Is_Called_Then_Status_Created_It_Should_Be_Returned() throws Exception {
    //@Given
    var expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

    //@When
    when(service.create(expectedCreatedPublisherDTO)).thenReturn(expectedCreatedPublisherDTO);

    //@Then

    mockMvc.perform(post(PUBLISHERS_API_URL_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonCoversionUtils.asJsonString(expectedCreatedPublisherDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", Matchers.is(expectedCreatedPublisherDTO.getId().intValue())))
            .andExpect(jsonPath("$.name", Matchers.is(expectedCreatedPublisherDTO.getName())))
            .andExpect(jsonPath("$.code", Matchers.is(expectedCreatedPublisherDTO.getCode())));
  }

  @Test
  @DisplayName("Lança a exceção quando não preenchido os campos corretamente")
  void when_Post_Is_Called_Without_Required_Fields_Then_Bad_Request_Status_It_Should_Be_Returned() throws Exception {
    //@Given
    var expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
    expectedCreatedPublisherDTO.setName(null);
    //@When is not implemented, because throw an exception and don't enter the service layer.
    //@Then
    mockMvc.perform(post(PUBLISHERS_API_URL_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonCoversionUtils.asJsonString(expectedCreatedPublisherDTO)))
            .andExpect(status().isBadRequest());
  }


  @Test
  @DisplayName("Pesquisa um Publisher pelo id")
  void when_Get_By_Id_Is_Called_Then_Status_Ok_It_Should_Be_Returned() throws Exception {
    //@Given
    var expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

    //@When
    when(service.findById(expectedCreatedPublisherDTO.getId())).thenReturn(expectedCreatedPublisherDTO);

    //@Then
    mockMvc.perform(get(PUBLISHERS_API_URL_PATH + "/" + expectedCreatedPublisherDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", Matchers.is(expectedCreatedPublisherDTO.getId().intValue())))
            .andExpect(jsonPath("$.name", Matchers.is(expectedCreatedPublisherDTO.getName())))
            .andExpect(jsonPath("$.code", Matchers.is(expectedCreatedPublisherDTO.getCode())));
  }

  @Test
  @DisplayName("Lista todos os Publishers")
  void when_Get_List_Is_Called_Then_Status_Ok_It_Should_Be_Returned() throws Exception {
    //@Given
    var expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

    //@When
    when(service.findAll()).thenReturn(Collections.singletonList(expectedCreatedPublisherDTO));

    //@Then
    mockMvc.perform(get(PUBLISHERS_API_URL_PATH)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", Matchers.is(expectedCreatedPublisherDTO.getId().intValue())))
            .andExpect(jsonPath("$[0].name", Matchers.is(expectedCreatedPublisherDTO.getName())))
            .andExpect(jsonPath("$[0].code", Matchers.is(expectedCreatedPublisherDTO.getCode())));
  }

  @Test
  @DisplayName("Exclui Publisher passando o id")
  void when_Delete_Is_Called_Then_Status_NoContent_It_Should_Be_Returned() throws Exception {
    //@Given
    var expectedPublisherToDelete = publisherDTOBuilder.buildPublisherDTO();
    var expectedPublisherIdToDelete = expectedPublisherToDelete.getId();

    //@When
    doNothing().when(service).delete(expectedPublisherIdToDelete);

    //@Then
    mockMvc.perform(delete(PUBLISHERS_API_URL_PATH + "/" + expectedPublisherIdToDelete)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
  }
}
