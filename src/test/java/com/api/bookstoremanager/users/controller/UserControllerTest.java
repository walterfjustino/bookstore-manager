package com.api.bookstoremanager.users.controller;

import com.api.bookstoremanager.users.builder.UserDTOBuilder;
import com.api.bookstoremanager.users.dto.MessageDTO;
import com.api.bookstoremanager.users.service.UserServiceImpl;
import com.api.bookstoremanager.utils.JsonCoversionUtils;
import org.hamcrest.Matchers;
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
public class UserControllerTest {

  private static final String URL_PATH_USERS = "/api/v1/users";
  private MockMvc mockMvc;

  @Mock
  private UserServiceImpl service;

  @InjectMocks
  private UserController controller;

  private UserDTOBuilder userDTOBuilder;

  @BeforeEach
  void setUp() {
    userDTOBuilder = UserDTOBuilder.builder().build();
    mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
            .build();
  }

  @Test
  void when_POST_Is_Called_Then_Created_Status_It_Should_Be_Returned() throws Exception {
    //@Given
    var expectedUserToCreateDTO = userDTOBuilder.buildUserDTO();
    var expectedCreationMessage = "User macgarenvillain with ID 1 successfully created";
    var expectedCreationMessageDTO = MessageDTO.builder().message(expectedCreationMessage).build();
    //@When
    Mockito.when(service.create(expectedUserToCreateDTO)).thenReturn(expectedCreationMessageDTO);
    //@Then
    mockMvc.perform(MockMvcRequestBuilders.post(URL_PATH_USERS)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonCoversionUtils.asJsonString(expectedUserToCreateDTO)))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(expectedCreationMessage)));
  }

  @Test
  void when_POST_Is_Called_Without_Required_Field_Then_BadRequesT_Status_It_Should_Be_Returned() throws Exception {
    //@Given
    var expectedUserToCreateDTO = userDTOBuilder.buildUserDTO();
    expectedUserToCreateDTO.setUsername(null);
    //@When is not implemented, because throw an exception and don't enter the service layer.
    //@Then
    mockMvc.perform(MockMvcRequestBuilders.post(URL_PATH_USERS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonCoversionUtils.asJsonString(expectedUserToCreateDTO)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void when_Delete_Is_Called_Then_No_Content_It_Should_Be_Informed() throws Exception {
    //@Given
    var expectedUserToCreateDTO = userDTOBuilder.buildUserDTO();
    //@When
    Mockito.doNothing().when(service).delete(expectedUserToCreateDTO.getId());
    //@Then
    mockMvc.perform(MockMvcRequestBuilders.delete(URL_PATH_USERS +"/"+expectedUserToCreateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
  }
}
