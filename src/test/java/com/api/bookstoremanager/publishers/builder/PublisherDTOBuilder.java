package com.api.bookstoremanager.publishers.builder;

import com.api.bookstoremanager.publishers.dto.PublisherDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Builder
public class PublisherDTOBuilder {
  @Builder.Default
  private final Long id = 1L;
  @Builder.Default
  private final String name = "Editora Tokusatsu";
  @Builder.Default
  private final String code = "daileon0123";
  @Builder.Default
  private final LocalDate foundationDate = LocalDate.of(2023, 9, 15);

  public PublisherDTO buildPublisherDTO(){
    return new PublisherDTO(id, name, code, foundationDate);
  }
}
