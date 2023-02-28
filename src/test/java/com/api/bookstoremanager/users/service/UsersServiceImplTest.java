package com.api.bookstoremanager.users.service;

import com.api.bookstoremanager.users.builder.UserDTOBuilder;
import com.api.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.api.bookstoremanager.users.exception.UserNotFoundException;
import com.api.bookstoremanager.users.mapper.UserMapper;
import com.api.bookstoremanager.users.repository.UserRepository;
import org.hamcrest.MatcherAssert;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários em UsersServiceImplTest")
public class UsersServiceImplTest {

  private final UserMapper mapper = UserMapper.INSTANCE;

  @Mock
  private UserRepository repository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @InjectMocks
  private UserServiceImpl service;

  private UserDTOBuilder userDTOBuilder;

  @BeforeEach
  void setUp() {
    userDTOBuilder = UserDTOBuilder.builder().build();
  }

  @Test
  @DisplayName("criar um novo User, se não houver nenhum cadastrado com o mesmo Email ou Username")
  void when_New_User_Is_Informed_Then_It_Should_Be_Created() {
   //@Given
    var expectedCreatedUserDTO = userDTOBuilder.buildUserDTO();
    var expectedCreatedUser = mapper.toModel(expectedCreatedUserDTO);
    var expectedCreationMessage = "User macgarenvillain with ID 1 successfully created";
    //@When
    Mockito.when(repository.findByEmailOrUsername(expectedCreatedUserDTO.getEmail(), expectedCreatedUserDTO.getUsername()))
            .thenReturn(Optional.empty());
    Mockito.when(passwordEncoder.encode(expectedCreatedUser.getPassword())).thenReturn(expectedCreatedUser.getPassword());
    Mockito.when(repository.save(expectedCreatedUser)).thenReturn(expectedCreatedUser);
    var creationMessage = service.create(expectedCreatedUserDTO);

    //@Then
    MatcherAssert.assertThat(expectedCreationMessage, Matchers.is(Matchers.equalTo(creationMessage.getMessage())));
  }

  @Test
  @DisplayName("Lança uma exceção se houver um User cadastrado com o mesmo Email ou Username")
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

  @Test
  @DisplayName("Exclui um User pelo ID")
  void when_Valid_User_Is_Informed_Then_It_Should_Be_Deleted() {
    //@Given
    var expectedDeletedUserDTO = userDTOBuilder.buildUserDTO();
    var expectedDeletedUser = mapper.toModel(expectedDeletedUserDTO);
    var expectedDeletedUserId = expectedDeletedUserDTO.getId();
    //@When
    Mockito.when(repository.findById(expectedDeletedUserId)).thenReturn(Optional.of(expectedDeletedUser));
    Mockito.doNothing().when(repository).deleteById(expectedDeletedUserId);
    service.delete(expectedDeletedUserId);
    //@Then
    Mockito.verify(repository, Mockito.times(1)).deleteById(expectedDeletedUserId);
  }

  @Test
  @DisplayName("Lança exceção se o ID for inválido")
  void when_InValid_User_ID_Is_Informed_Then_It_Should_Be_Thrown() {
    //@Given
    var expectedDeletedUserDTO = userDTOBuilder.buildUserDTO();
    var expectedDeletedUserId = expectedDeletedUserDTO.getId();
    //@When
    Mockito.when(repository.findById(expectedDeletedUserId)).thenReturn(Optional.empty());
    //@Then
    Assertions.assertThrows(UserNotFoundException.class, ()->  service.delete(expectedDeletedUserId));
  }

  @Test
  @DisplayName("Atualiza um User se estiver cadastrado")
  void when_Existing_User_Is_Informed_Then_It_Should_Be_Updated() {
    //@Given
    var expectedUpdatedUserDTO = userDTOBuilder.buildUserDTO();
    expectedUpdatedUserDTO.setUsername("satan-goss");
    var expectedUpdatedUser = mapper.toModel(expectedUpdatedUserDTO);
    var expectedUpdatedMessage = "User satan-goss with ID 1 successfully updated";

    //@When
    Mockito.when(repository.findById(expectedUpdatedUserDTO.getId())).thenReturn(Optional.of(expectedUpdatedUser));
    Mockito.when(passwordEncoder.encode(expectedUpdatedUser.getPassword())).thenReturn(expectedUpdatedUser.getPassword());
    Mockito.when(repository.save(expectedUpdatedUser)).thenReturn(expectedUpdatedUser);
    var sucessUpdatedMessage = service.update(expectedUpdatedUserDTO.getId(), expectedUpdatedUserDTO);
    //@Then
    MatcherAssert.assertThat(expectedUpdatedMessage, Matchers.is(Matchers.equalTo(sucessUpdatedMessage.getMessage())));
  }

  @Test
  @DisplayName("Lança exceção se o User não estiver cadastrado")
  void when_Not_Existing_User_Is_Informed_Then_It_Should_Be_Thrown() {
    //@Given
    var expectedDeletedUserDTO = userDTOBuilder.buildUserDTO();
    //@When
    Mockito.when(repository.findById(expectedDeletedUserDTO.getId())).thenReturn(Optional.empty());
    //@Then
    Assertions.assertThrows(UserNotFoundException.class, ()-> service.update(expectedDeletedUserDTO.getId(),expectedDeletedUserDTO));
  }
}
