package com.api.bookstoremanager.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {
  @NotNull
  @NotEmpty
  private String username;

  @NotNull
  @NotEmpty
  private String password;
}
