package com.api.bookstoremanager.mapper;

import com.api.bookstoremanager.dto.BookDTO;

import com.api.bookstoremanager.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;




@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toModel(BookDTO bookDTO);

    BookDTO toDTO(Book book);
}
