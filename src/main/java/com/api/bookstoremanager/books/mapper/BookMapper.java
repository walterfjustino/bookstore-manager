package com.api.bookstoremanager.books.mapper;

import com.api.bookstoremanager.author.mapper.AuthorMapper;
import com.api.bookstoremanager.books.dto.BookDTO;

import com.api.bookstoremanager.books.entity.Book;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toModel(BookDTO bookDTO);

    BookDTO toDTO(Book book);
}
