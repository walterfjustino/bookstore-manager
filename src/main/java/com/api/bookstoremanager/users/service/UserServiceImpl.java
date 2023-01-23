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

import static com.api.bookstoremanager.users.utils.MessageDTOUtils.creationMessage;
import static com.api.bookstoremanager.users.utils.MessageDTOUtils.updatedMessage;

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

  @Override
  public MessageDTO update(Long id, UserDTO userToUpdateDTO) {
    var foundUser = verifyAndGetIfExists(id);
    userToUpdateDTO.setId(foundUser.getId());
    var userToUpdate = mapper.toModel(userToUpdateDTO);
    userToUpdate.setCreatedDate(foundUser.getCreatedDate());
    var updatedUser = repository.save(userToUpdate);
    return updatedMessage(updatedUser);
  }

  @Override
  public void delete(Long id) {
    verifyAndGetIfExists(id);
    repository.deleteById(id);
  }

  private void verifyIfExists(String email, String username) {
    repository.findByEmailOrUsername(email, username)
            .ifPresent(user -> {throw new UserAlreadyExistsException(user.getEmail(), user.getUsername());});
  }

  private User verifyAndGetIfExists(Long id) {
    return repository.findById(id)
            .orElseThrow(()-> {throw new UserNotFoundException(id);});
  }
}
