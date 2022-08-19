package com.api.bookstoremanager.author.mapper;

import com.api.bookstoremanager.author.dto.AuthorDTO;
import com.api.bookstoremanager.author.entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toModel(AuthorDTO authorDTO);

    AuthorDTO toDTO(Author author);
}
