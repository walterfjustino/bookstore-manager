package com.api.bookstoremanager.publishers.service;

import com.api.bookstoremanager.publishers.dto.PublisherDTO;
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
    return null;
  }

  @Override
  public PublisherDTO findById(Long id) {
    return null;
  }

  @Override
  public List<PublisherDTO> findAll() {
    return null;
  }

  @Override
  public void delete(Long id) {

  }
}
