package com.api.bookstoremanager.users.builder;

import com.api.bookstoremanager.users.dto.JwtRequest;
import lombok.Builder;

@Builder
public class JwtRequestBuilder {

  @Builder.Default
  private String username = "Daileon";
  @Builder.Default
  private String password = "123456";

  public JwtRequest buildJwtRequest(){
    return new JwtRequest(username, password);
  }
}
