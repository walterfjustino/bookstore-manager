package com.api.bookstoremanager.books.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {

    private Long id;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String name;

    @NotNull
    @ISBN
    private String isbn;

    @NotNull
    @Max(3000)
    private Integer pages;

    @NotNull
    @Max(100)
    private Integer chapters;

    @NotNull
    private Long publisherId;

    @NotNull
    private Long authorId;
}