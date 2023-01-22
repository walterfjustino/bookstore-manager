package com.api.bookstoremanager.users.builder;

import com.api.bookstoremanager.users.dto.UserDTO;
import com.api.bookstoremanager.users.enums.Gender;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class UserDTOBuilder {

  @Builder.Default
  private Long id = 1L;
  @Builder.Default
  private String name = "Macgaren";
  @Builder.Default
  private int age = 33;
  @Builder.Default
  private Gender gender = Gender.MALE;
  @Builder.Default
  private String email = "macgaren_villain@gmail.com";
  @Builder.Default
  private String username = "macgarenvillain";
  @Builder.Default
  private String password = "123456";
  @Builder.Default
  private LocalDate birthDate = LocalDate.of(1989, 05, 02);

  public UserDTO buildUserDTO(){
    return new UserDTO(id,
            name,
            age,
            gender,
            email,
            username,
            password,
            birthDate);
  }
}
