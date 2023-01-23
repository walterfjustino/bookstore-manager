package com.api.bookstoremanager.users.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
  ADMIN("admin"),
  USER("user");

  private final String description;
}
