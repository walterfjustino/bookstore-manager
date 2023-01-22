package com.api.bookstoremanager.users.controller;

import com.api.bookstoremanager.users.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController implements UserControllerDocs {

  @Autowired
  private UserServiceImpl service;
}
