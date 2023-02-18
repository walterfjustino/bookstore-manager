package com.api.bookstoremanager.books.mapper;

import com.api.bookstoremanager.author.mapper.AuthorMapper;
import com.api.bookstoremanager.books.dto.BookDTO;

import com.api.bookstoremanager.books.dto.BookRequestDTO;
import com.api.bookstoremanager.books.dto.BookResponseDTO;
import com.api.bookstoremanager.books.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

//    Book toModel(BookDTO bookDTO);

    Book toModel(BookRequestDTO bookRequestDTO);

    Book toModel(BookResponseDTO bookResponseDTO);

    BookResponseDTO toDTO(Book book);


//    BookDTO toDTO(Book book);
}
