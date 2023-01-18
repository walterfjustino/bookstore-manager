package com.api.bookstoremanager.publishers.service;

import com.api.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.api.bookstoremanager.publishers.exception.PublisherAlreadyExistsException;
import com.api.bookstoremanager.publishers.mapper.PublisherMapper;
import com.api.bookstoremanager.publishers.repository.PublisherRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@DisplayName("PublisherServiceImplTest")
public class PublisherServiceTest {

  private static final PublisherMapper mapper = PublisherMapper.INSTANCE;

  @Mock
  private PublisherRepository repository;

  @InjectMocks
  private PublisherServiceImpl service;

 private PublisherDTOBuilder publisherDTOBuilder;

  @BeforeEach
  void setUp() {
    publisherDTOBuilder = PublisherDTOBuilder.builder().build();
  }

  @Test
  void when_New_Publisher_Is_Informed_Then_It_Should_Be_Created() {
    //@Given
    var expectedPublisherToCreatedDTO = publisherDTOBuilder.buildPublisherDTO();
    var expectedtCreatedPublisher = mapper.toModel(expectedPublisherToCreatedDTO);

    //@When
    when(repository.findByNameOrCode(expectedPublisherToCreatedDTO.getName(), expectedPublisherToCreatedDTO.getCode()))
            .thenReturn(Optional.empty());
    when(repository.save(expectedtCreatedPublisher)).thenReturn(expectedtCreatedPublisher);
    var createdPublisherDTO = service.create(expectedPublisherToCreatedDTO);

    //@Then
    assertThat(createdPublisherDTO, is(equalTo(expectedPublisherToCreatedDTO)));
  }

  @Test
  void when_Existing_Publisher_Is_Informed_Then_An_Exception_Should_Be_Thrown() {
    //@Given
    var expectedPublisherToCreatedDTO = publisherDTOBuilder.buildPublisherDTO();
    var expectedtPublisherDuplicated = mapper.toModel(expectedPublisherToCreatedDTO);

    //@When
    when(repository.findByNameOrCode(expectedPublisherToCreatedDTO.getName(), expectedPublisherToCreatedDTO.getCode()))
            .thenReturn(Optional.of(expectedtPublisherDuplicated));

    //@Then
    assertThrows(PublisherAlreadyExistsException.class, () -> service.create(expectedPublisherToCreatedDTO));
  }
}
