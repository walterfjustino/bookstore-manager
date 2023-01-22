package com.api.bookstoremanager.users.service;

import com.api.bookstoremanager.users.builder.UserDTOBuilder;
import com.api.bookstoremanager.users.exception.UserAlreadyExistsException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

  private final UserMapper mapper = UserMapper.INSTANCE;

  @Mock
  private UserRepository repository;

  @InjectMocks
  private UserServiceImpl service;

  private UserDTOBuilder userDTOBuilder;

  @BeforeEach
  void setUp() {
    userDTOBuilder = UserDTOBuilder.builder().build();
  }

  @Test
  void when_New_User_Is_Informed_Then_It_Should_Be_Created() {
   //@Given
    var expectedCreatedUserDTO = userDTOBuilder.buildUserDTO();
    var expectedCreatedUser = mapper.toModel(expectedCreatedUserDTO);
    var expectedCreationMessage = "User macgarenvillain with ID 1 successfully created";
    //@When
    Mockito.when(repository.findByEmailOrUsername(expectedCreatedUserDTO.getEmail(), expectedCreatedUserDTO.getUsername()))
            .thenReturn(Optional.empty());
    Mockito.when(repository.save(expectedCreatedUser)).thenReturn(expectedCreatedUser);
    var creationMessage = service.create(expectedCreatedUserDTO);

    //@Then
    MatcherAssert.assertThat(expectedCreationMessage, Matchers.is(Matchers.equalTo(creationMessage.getMessage())));
  }

  @Test
  void when_Existing_User_Is_Informed_Then_An_Exception_Should_Be_Thrown() {
    //@Given
    var expectedDuplicatedUserDTO = userDTOBuilder.buildUserDTO();
    var expectedDuplicatedUser = mapper.toModel(expectedDuplicatedUserDTO);
    //@When
    Mockito.when(repository.findByEmailOrUsername(expectedDuplicatedUserDTO.getEmail(), expectedDuplicatedUserDTO.getUsername()))
            .thenReturn(Optional.of(expectedDuplicatedUser));
    //@Then
    assertThrows(UserAlreadyExistsException.class, () -> service.create(expectedDuplicatedUserDTO));
  }
}
