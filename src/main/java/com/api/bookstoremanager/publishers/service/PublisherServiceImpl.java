package com.api.bookstoremanager.publishers.service;

import com.api.bookstoremanager.publishers.dto.PublisherDTO;
import com.api.bookstoremanager.publishers.entity.Publisher;
import com.api.bookstoremanager.publishers.exception.PublisherAlreadyExistsException;
import com.api.bookstoremanager.publishers.exception.PublisherNotFoundException;
import com.api.bookstoremanager.publishers.mapper.PublisherMapper;
import com.api.bookstoremanager.publishers.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PublisherServiceImpl implements PublisherService{

  public static final PublisherMapper mapper = PublisherMapper.INSTANCE;

  @Autowired
  private PublisherRepository repository;


  @Override
  public PublisherDTO create(PublisherDTO publisherDTO) {
    verifyIfPublisherExists(publisherDTO.getName(), publisherDTO.getCode());
    Publisher publisherToCreate = mapper.toModel(publisherDTO);
    Publisher createdPublisher = repository.save(publisherToCreate);
    return mapper.toDTO(createdPublisher);
  }

@Override
public PublisherDTO findById(Long id) {
  return repository.findById(id)
          .map(mapper::toDTO)
          .orElseThrow(() -> new PublisherNotFoundException(id));
}
  @Override
  public List<PublisherDTO> findAll() {
    return repository.findAll()
            .stream()
            .map(mapper::toDTO)
            .toList();
  }

  @Override
  public void delete(Long id) {
    verifyIfPublisherExistsAndGet(id);
    repository.deleteById(id);
  }

  public Publisher verifyIfPublisherExistsAndGet(Long id) {
    return repository.findById(id)
            .orElseThrow(()-> new PublisherNotFoundException(id));
  }

  private void verifyIfPublisherExists(String name, String code ) {
    repository.findByNameOrCode(name, code)
            .ifPresent(publisher -> { throw new PublisherAlreadyExistsException(name, code);});
  }
}
