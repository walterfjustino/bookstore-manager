package com.api.bookstoremanager.users.controller;

import com.api.bookstoremanager.users.dto.MessageDTO;
import com.api.bookstoremanager.users.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("System Users management")
public interface UserControllerDocs {

  @ApiOperation(value = "User creation operation")
  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "Success user creation"),
          @ApiResponse(code = 400, message = "Missing required fields, wrong field range value or user already registered on system")
  })
  public MessageDTO create(UserDTO userToCreateDTO);

  @ApiOperation(value = "User update operation")
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "User by user successfully updated"),
          @ApiResponse(code = 404, message = "User not found error"),
          @ApiResponse(code = 400, message = "Missing required fields, wrong field range value or User already registered on system")
  })
  public MessageDTO update(Long id, UserDTO userToUpdateDTO);

  @ApiOperation("User delete operation")
  @ApiResponses(value = {
          @ApiResponse(code = 204, message = "Deleted user by id")
  })
  public void delete(Long id);
}
