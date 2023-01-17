package com.api.bookstoremanager.publishers.service;

import com.api.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.api.bookstoremanager.publishers.mapper.PublisherMapper;
import com.api.bookstoremanager.publishers.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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
}
