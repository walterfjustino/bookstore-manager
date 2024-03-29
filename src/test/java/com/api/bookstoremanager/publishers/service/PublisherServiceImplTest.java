package com.api.bookstoremanager.publishers.service;

import com.api.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.api.bookstoremanager.publishers.exception.PublisherAlreadyExistsException;
import com.api.bookstoremanager.publishers.exception.PublisherNotFoundException;
import com.api.bookstoremanager.publishers.mapper.PublisherMapper;
import com.api.bookstoremanager.publishers.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários em PublisherServiceImplTest")
public class PublisherServiceImplTest {

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
  @DisplayName("criar um novo Publisher, se não houver nenhum cadastrado com o mesmo nome ou código")
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
  @DisplayName("Lança exceção se já houver algum cadastrado com o mesmo nome ou código")
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

  @Test
  @DisplayName("Pesquisa um Publisher por ID")
  void when_Valid_Id_Is_Given_Then_A_Publisher_It_Should_Be_Returned() {
    //@Given
    var expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();
    var expectedPublisherFound = mapper.toModel(expectedPublisherFoundDTO);

    //@When
    when(repository.findById(expectedPublisherFoundDTO.getId())).thenReturn(Optional.of(expectedPublisherFound))
            .thenReturn(Optional.empty());

    var foundPublisherDTO = service.findById(expectedPublisherFound.getId());

    //@Then
    assertThat(foundPublisherDTO, is(equalTo(foundPublisherDTO)));
  }

  @Test
  @DisplayName("Lança exceção quando ID não for encontrado")
  void when_Invalid_Id_Is_Given_Then_An_Exception_Should_Be_Thrown() {
    //@Given
    var expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();

    var expectedPublisherFoundId = expectedPublisherFoundDTO.getId();

    //@When
    when(repository.findById(expectedPublisherFoundDTO.getId())).thenReturn(Optional.empty());

    //@Then
    assertThrows(PublisherNotFoundException.class, () -> service.findById(expectedPublisherFoundId));
  }

  @Test
  @DisplayName("Retorna uma Lista com todos os Publishers")
  void when_List_Publishers_Is_Called_Then_It_Should_Be_Returned() {
    //@Given
    var expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();
    var expectedPublisherFound = mapper.toModel(expectedPublisherFoundDTO);

    //@When
    when(repository.findAll()).thenReturn(Collections.singletonList(expectedPublisherFound));
    var foundPublishersDTO = service.findAll();

    //@Then
    assertThat(foundPublishersDTO.size(), is(1));
    assertThat(foundPublishersDTO.get(0), is(equalTo(expectedPublisherFoundDTO)));

  }

  @Test
  @DisplayName("Retorna uma Lista vazia caso não encontrar nenhum Publisher cadastrado")
  void when_List_Publishers_Is_Called_Then_An_Empty_List_It_Should_Be_Returned() {
    //@Given is empty, because the simulating an empty list

    //@When
    when(repository.findAll()).thenReturn(Collections.emptyList());
    var foundPublishersDTO = service.findAll();

    //@Then
    assertThat(foundPublishersDTO.size(), is(0));
}

  @Test
  @DisplayName("Exclui um Publisher por ID")
  void when_Valid_PublisherId_Is_Given_Then_It_Should_Be_Deleted() {
    //@Given
    var expectedPublisherDeletedDTO = publisherDTOBuilder.buildPublisherDTO();
    var expectedPublisherDeleted = mapper.toModel(expectedPublisherDeletedDTO);
    //@When
    doNothing().when(repository).deleteById(expectedPublisherDeleted.getId());
    when(repository.findById(expectedPublisherDeletedDTO.getId())).thenReturn(Optional.of(expectedPublisherDeleted));
    service.delete(expectedPublisherDeletedDTO.getId());

    //@Then
    verify(repository,times(1)).deleteById(expectedPublisherDeletedDTO.getId());

  }

  @Test
  @DisplayName("Lança uma exceção se o ID não for válido")
  void when_Invalid_PublisherId_Is_Given_Then_It_Should_Be_Deleted() {
    //@Given
    var expectedInvalidPublisherId = 2L;

    //@When
    when(repository.findById(expectedInvalidPublisherId)).thenReturn(Optional.empty());

    //@Then
   assertThrows(PublisherNotFoundException.class, () -> service.delete(expectedInvalidPublisherId));
  }
}
