package com.api.bookstoremanager.books.dto;

import com.api.bookstoremanager.author.dto.AuthorDTO;
import com.api.bookstoremanager.publishers.dto.PublisherDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {

    private Long id;

    private String name;

    private String isbn;

    private Integer pages;

    private Integer chapters;

    private AuthorDTO author;
    private PublisherDTO publisher;
}
