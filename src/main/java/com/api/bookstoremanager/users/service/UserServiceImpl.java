package com.api.bookstoremanager.users.service;

import com.api.bookstoremanager.users.mapper.UserMapper;
import com.api.bookstoremanager.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl {

  private static final UserMapper mapper = UserMapper.INSTANCE;

  @Autowired
  private UserRepository repository;
}
