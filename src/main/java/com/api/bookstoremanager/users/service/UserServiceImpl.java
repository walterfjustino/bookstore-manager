package com.api.bookstoremanager.users.service;

import com.api.bookstoremanager.users.dto.MessageDTO;
import com.api.bookstoremanager.users.dto.UserDTO;
import com.api.bookstoremanager.users.entity.User;
import com.api.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.api.bookstoremanager.users.exception.UserNotFoundException;
import com.api.bookstoremanager.users.mapper.UserMapper;
import com.api.bookstoremanager.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService{

  private static final UserMapper mapper = UserMapper.INSTANCE;

  @Autowired
  private UserRepository repository;

  @Override
  public MessageDTO create(UserDTO userToCreateDTO) {
    verifyIfExists(userToCreateDTO.getEmail(), userToCreateDTO.getUsername());
    var userToCreate = mapper.toModel(userToCreateDTO);
    var createdUser = repository.save(userToCreate);
    return creationMessage(createdUser);
  }

  private void verifyIfExists(String email, String username) {
    repository.findByEmailOrUsername(email, username)
            .ifPresent(user -> {throw new UserAlreadyExistsException(user.getEmail(), user.getUsername());});
  }

  private MessageDTO creationMessage(User createdUser) {
    var createdUsername = createdUser.getUsername();
    var createdUserId = createdUser.getId();
    var createdUserMessage = String.format("User %s with ID %s successfully created", createdUsername, createdUserId);
    return MessageDTO.builder()
            .message(createdUserMessage)
            .build();
  }

  @Override
  public MessageDTO update(Long id, UserDTO userToUpdateDTO) {
    return null;
  }

  @Override
  public void delete(Long id) {
    verifyIfExists(id);
    repository.deleteById(id);
  }

  private void verifyIfExists(Long id) {
    repository.findById(id)
            .orElseThrow(()-> {throw new UserNotFoundException(id);});
  }
}
