package com.api.bookstoremanager.users.service;

import com.api.bookstoremanager.users.builder.JwtRequestBuilder;
import com.api.bookstoremanager.users.builder.UserDTOBuilder;
import com.api.bookstoremanager.users.dto.JwtRequest;
import com.api.bookstoremanager.users.dto.JwtResponse;
import com.api.bookstoremanager.users.dto.UserDTO;
import com.api.bookstoremanager.users.entity.User;
import com.api.bookstoremanager.users.mapper.UserMapper;
import com.api.bookstoremanager.users.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários em AuthenticationService")
public class AuthenticationServiceTest {

  private final UserMapper mapper = UserMapper.INSTANCE;

  @Mock
  private UserRepository repository;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private JwtTokenManager jwtTokenManager;

  @InjectMocks
  private AuthenticationService authenticationService;

  private UserDTOBuilder userDTOBuilder;

  private JwtRequestBuilder jwtRequestBuilder;

  @BeforeEach
  void setUp() {
    userDTOBuilder = UserDTOBuilder.builder().build();
    jwtRequestBuilder = JwtRequestBuilder.builder().build();
  }

  @Test
  @DisplayName("Gera o token se o user estiver cadastrado")
  void when_Username_And_Password_Is_Informed_Then_A_Token_Should_Be_Generated() {
    //@Given
    JwtRequest jwtRequest = jwtRequestBuilder.buildJwtRequest();
    UserDTO expectedFoundUserDTO = userDTOBuilder.buildUserDTO();
    User expectedFoundUser = mapper.toModel(expectedFoundUserDTO);
    var expectedGeneratedToken = "fakeToken";
    //@When
    Mockito.when(repository.findByUsername(jwtRequest.getUsername())).thenReturn(Optional.of(expectedFoundUser));
    Mockito.when(jwtTokenManager.generateToken(any(UserDetails.class))).thenReturn(expectedGeneratedToken);

    JwtResponse generatedTokenResponse = authenticationService.createAuthenticationToken(jwtRequest);
    //@Then
    assertThat(generatedTokenResponse.getJwtToken(),is(equalTo(expectedGeneratedToken)));

  }

  @Test
  @DisplayName("Pesquisa um user se estiver cadastrado")
  void when_Username_Is_Informed_Then_User_Should_Be_Returned() {
    //@Given
    var expectedFoundUserDTO = userDTOBuilder.buildUserDTO();
    var expectedFoundUser = mapper.toModel(expectedFoundUserDTO);
    SimpleGrantedAuthority expectedUserRole = new SimpleGrantedAuthority("ROLE_" + expectedFoundUserDTO.getRole().getDescription());
    String expectedUsername = expectedFoundUserDTO.getUsername();

    //@When
    Mockito.when(repository.findByUsername(expectedUsername)).thenReturn(Optional.of(expectedFoundUser));
    UserDetails userDetails = authenticationService.loadUserByUsername(expectedUsername);

    //@Then
    assertThat(userDetails.getUsername(), is(Matchers.equalTo(expectedFoundUser.getUsername())));
    assertThat(userDetails.getPassword(), is(Matchers.equalTo(expectedFoundUser.getPassword())));
    Assertions.assertTrue(userDetails.getAuthorities().contains(expectedUserRole));
  }

  @Test
  @DisplayName("Lança exceção se o User não estiver cadastrado")
  void when_Invalid_Username_Is_Informed_Then_An_Exception_Should_Be_Thrown() {
    //@Given
    var expectedFoundUserDTO = userDTOBuilder.buildUserDTO();

    //@When
    Mockito.when(repository.findByUsername(expectedFoundUserDTO.getUsername())).thenReturn(Optional.empty());

    //@Then
    Assertions.assertThrows(UsernameNotFoundException.class, () -> authenticationService.loadUserByUsername(expectedFoundUserDTO.getUsername()));
  }
}
