package com.api.bookstoremanager.publishers.mapper;

import com.api.bookstoremanager.publishers.dto.PublisherDTO;
import com.api.bookstoremanager.publishers.entity.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface PublisherMapper {

  PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

  Publisher toModel (PublisherDTO publisherDTO);

  PublisherDTO toDTO(Publisher publisher);
}
