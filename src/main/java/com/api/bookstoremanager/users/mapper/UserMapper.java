package com.api.bookstoremanager.users.mapper;

import com.api.bookstoremanager.users.dto.UserDTO;
import com.api.bookstoremanager.users.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
   UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  User toModel(UserDTO userDTO);
  UserDTO toDTO(User user);
}
