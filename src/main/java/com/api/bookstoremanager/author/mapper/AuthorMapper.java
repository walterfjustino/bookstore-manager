package com.api.bookstoremanager.author.mapper;

import com.api.bookstoremanager.author.dto.AuthorDTO;
import com.api.bookstoremanager.author.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toModel(AuthorDTO authorDTO);

    AuthorDTO toDTO(Author author);


}
