package com.api.bookstoremanager.users.controller;

import com.api.bookstoremanager.users.dto.JwtRequest;
import com.api.bookstoremanager.users.dto.JwtResponse;
import com.api.bookstoremanager.users.dto.MessageDTO;
import com.api.bookstoremanager.users.dto.UserDTO;
import com.api.bookstoremanager.users.service.AuthenticationService;
import com.api.bookstoremanager.users.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController implements UserControllerDocs {

  @Autowired
  private UserServiceImpl service;
  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Override
  public MessageDTO create(@RequestBody @Valid UserDTO userToCreateDTO) {
    return service.create(userToCreateDTO);
  }

  @PutMapping("/{id}")
  @Override
  public MessageDTO update(@PathVariable Long id, @RequestBody @Valid UserDTO userToUpdateDTO) {
    return service.update(id, userToUpdateDTO);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Override
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }

  //  @CrossOrigin
  @PostMapping("/authenticate")
  public JwtResponse createAuthenticationToken(@RequestBody @Valid JwtRequest jwtRequest) {
    return authenticationService.createAuthenticationToken(jwtRequest);
  }
}
