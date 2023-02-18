package com.api.bookstoremanager.book.builder;

import com.api.bookstoremanager.books.dto.BookRequestDTO;
import com.api.bookstoremanager.users.builder.UserDTOBuilder;
import com.api.bookstoremanager.users.dto.UserDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.ISBN;

@Builder
public class BookRequestDTOBuilder {
  @Builder.Default
  private final Long id = 1L;

  @Builder.Default
  private final String name = "Spring Boot Pro";

  @Builder.Default
  private final String isbn = "978-3-16-148410-0";

  @Builder.Default
  private final Long publisherId = 2L;

  @Builder.Default
  private final Long authorId = 3L;

  @Builder.Default
  private final Integer pages = 200;

  @Builder.Default
  private final Integer chapters = 10;

  private final UserDTO userDTO = UserDTOBuilder.builder().build().buildUserDTO();

  public BookRequestDTO buildRequestBookDTO() {
    return new BookRequestDTO(id,
            name,
            isbn,
            pages,
            chapters,
            publisherId,
            authorId);
  }
}
