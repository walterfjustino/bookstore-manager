package com.api.bookstoremanager.users.service;

import com.api.bookstoremanager.users.builder.UserDTOBuilder;
import com.api.bookstoremanager.users.mapper.UserMapper;
import com.api.bookstoremanager.users.repository.UserRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

  private final UserMapper mapper = UserMapper.INSTANCE;

  @Mock
  private UserRepository repository;

  @InjectMocks
  private AuthenticationService authenticationService;

  private UserDTOBuilder userDTOBuilder;

  @BeforeEach
  void setUp() {
    userDTOBuilder = UserDTOBuilder.builder().build();
  }

  @Test
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
    MatcherAssert.assertThat(userDetails.getUsername(), Matchers.is(Matchers.equalTo(expectedFoundUser.getUsername())));
    MatcherAssert.assertThat(userDetails.getPassword(), Matchers.is(Matchers.equalTo(expectedFoundUser.getPassword())));
    Assertions.assertTrue(userDetails.getAuthorities().contains(expectedUserRole));
  }

  @Test
  void when_Invalid_Username_Is_Informed_Then_An_Exception_Should_Be_Thrown() {
    //@Given
    var expectedFoundUserDTO = userDTOBuilder.buildUserDTO();

    //@When
    Mockito.when(repository.findByUsername(expectedFoundUserDTO.getUsername())).thenReturn(Optional.empty());

    //@Then
    Assertions.assertThrows(UsernameNotFoundException.class, () -> authenticationService.loadUserByUsername(expectedFoundUserDTO.getUsername()));
  }
}
