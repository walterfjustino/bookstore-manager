package com.api.bookstoremanager.users.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class JwtResponse {

  private final String jwtToken;
}
