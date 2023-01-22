package com.api.bookstoremanager.users.service;

import com.api.bookstoremanager.users.dto.MessageDTO;
import com.api.bookstoremanager.users.dto.UserDTO;

public interface UserService {

   public MessageDTO create(UserDTO userToCreateDTO);

  public MessageDTO update(Long id, UserDTO userToUpdateDTO);

  public void delete(Long id);
}
