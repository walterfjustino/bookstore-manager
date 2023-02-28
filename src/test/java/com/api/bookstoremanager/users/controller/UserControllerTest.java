package com.api.bookstoremanager.users.controller;

import com.api.bookstoremanager.users.builder.JwtRequestBuilder;
import com.api.bookstoremanager.users.builder.UserDTOBuilder;
import com.api.bookstoremanager.users.dto.JwtRequest;
import com.api.bookstoremanager.users.dto.JwtResponse;
import com.api.bookstoremanager.users.dto.MessageDTO;
import com.api.bookstoremanager.users.service.AuthenticationService;
import com.api.bookstoremanager.users.service.UserServiceImpl;
import com.api.bookstoremanager.utils.JsonCoversionUtils;
import org.hamcrest.Matchers;
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


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  private static final String URL_PATH_USERS = "/api/v1/users";
  private MockMvc mockMvc;

  @Mock
  private UserServiceImpl service;

  @Mock
  private AuthenticationService authenticationService;

  @InjectMocks
  private UserController controller;

  private UserDTOBuilder userDTOBuilder;

  private JwtRequestBuilder jwtRequestBuilder;

  @BeforeEach
  void setUp() {
    userDTOBuilder = UserDTOBuilder.builder().build();
    jwtRequestBuilder = JwtRequestBuilder.builder().build();
    mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
            .build();
  }

  @Test
  @DisplayName("cria um novo User, se não houver nenhum cadastrado com o mesmo nome")
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
  @DisplayName("Lança a exceção quando encontrado que o User a ser incluido já existe")
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
  @DisplayName("Excluí um User pelo id")
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

  @Test
  @DisplayName("Atualiza um User")
  void when_PUT_Is_Called_Then_OK_Status_It_Should_Be_Returned() throws Exception {
    //@Given
    var expectedUserToUpdateDTO = userDTOBuilder.buildUserDTO();
    expectedUserToUpdateDTO.setUsername("satan-goss");
    var expectedUpdatedMessage = "User satan-goss with ID 1 successfully updated";
    var expectedUpdateMessageDTO = MessageDTO.builder().message(expectedUpdatedMessage).build();
    //@When
    Mockito.when(service.update(expectedUserToUpdateDTO.getId(), expectedUserToUpdateDTO)).thenReturn(expectedUpdateMessageDTO);
    //@Then
    mockMvc.perform(MockMvcRequestBuilders.put(URL_PATH_USERS + "/" + expectedUserToUpdateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonCoversionUtils.asJsonString(expectedUserToUpdateDTO)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(expectedUpdatedMessage)));
  }

  @Test
  @DisplayName("Autêntica um usuario para gerar o token")
  void when_Post_Is_Called_To_Authenticate_User_Then_OK_Should_Be_Returned() throws Exception {
    //@Given
    JwtRequest jwtRequest = jwtRequestBuilder.buildJwtRequest();
    JwtResponse expectedJwtToken = JwtResponse.builder().jwtToken("fakeToken").build();
    //@When
    Mockito.when(authenticationService.createAuthenticationToken(jwtRequest)).thenReturn(expectedJwtToken);
    //@Then
    mockMvc.perform(MockMvcRequestBuilders.post(URL_PATH_USERS + "/authenticate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonCoversionUtils.asJsonString(jwtRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.jwtToken", Matchers.is(expectedJwtToken.getJwtToken())));;
  }

  @Test
  @DisplayName("Lança exceção se o User ou o password estiver errado ou não preenchido")
  void when_Post_Is_Called_To_Authenticate_User_Without_Password_Then_Bad_Request_Should_Be_Returned() throws Exception {
    //@Given
    JwtRequest jwtRequest = jwtRequestBuilder.buildJwtRequest();
    jwtRequest.setPassword(null);
    //@When is not implemented, because throw an exception and don't enter the service layer.
    //@Then
    mockMvc.perform(MockMvcRequestBuilders.post(URL_PATH_USERS + "/authenticate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonCoversionUtils.asJsonString(jwtRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}
