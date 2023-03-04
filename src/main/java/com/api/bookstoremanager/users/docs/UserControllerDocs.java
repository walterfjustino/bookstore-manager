package com.api.bookstoremanager.users.docs;

import com.api.bookstoremanager.users.dto.JwtRequest;
import com.api.bookstoremanager.users.dto.JwtResponse;
import com.api.bookstoremanager.users.dto.MessageDTO;
import com.api.bookstoremanager.users.dto.UserDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Users", description = "System Users management")
public interface UserControllerDocs {

  @Operation(description = "User creation operation", security = @SecurityRequirement(name = "token"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Success user creation"),
          @ApiResponse(responseCode = "400", description = "Missing required fields, wrong field range description or user already registered on system")
  })
  public MessageDTO create(UserDTO userToCreateDTO);

  @Operation(description = "User update operation", security = @SecurityRequirement(name = "token"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "User by user successfully updated"),
          @ApiResponse(responseCode = "404", description = "User not found error"),
          @ApiResponse(responseCode = "400", description = "Missing required fields, wrong field range value or User already registered on system")
  })
  public MessageDTO update(Long id, UserDTO userToUpdateDTO);

  @Operation(description = "User delete operation", security = @SecurityRequirement(name = "token"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Deleted user by id"),
          @ApiResponse(responseCode = "404", description = "User with ID not found in the system")
  })
  public void delete(Long id);

  @Operation(description = "User authentication operation", security = @SecurityRequirement(name = "token"))
  @ApiResponses(value = { 
          @ApiResponse(responseCode = "200", description = "Success user authenticated"),
          @ApiResponse(responseCode = "404", description = "User not found")
  })
  public JwtResponse createAuthenticationToken(JwtRequest jwtRequest);
}
