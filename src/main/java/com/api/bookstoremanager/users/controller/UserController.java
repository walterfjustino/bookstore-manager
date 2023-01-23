package com.api.bookstoremanager.users.controller;

import com.api.bookstoremanager.users.dto.MessageDTO;
import com.api.bookstoremanager.users.dto.UserDTO;
import com.api.bookstoremanager.users.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController implements UserControllerDocs {

  @Autowired
  private UserServiceImpl service;

@PostMapping
@ResponseStatus(HttpStatus.CREATED)
@Override
  public MessageDTO create(@RequestBody @Valid UserDTO userToCreateDTO) {
    return service.create(userToCreateDTO);
  }

  @Override
  public MessageDTO update(Long id, UserDTO userToUpdateDTO) {
    return null;
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Override
  public void delete(@PathVariable Long id) {
  service.delete(id);
  }
}
