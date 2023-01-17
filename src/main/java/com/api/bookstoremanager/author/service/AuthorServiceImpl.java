package com.api.bookstoremanager.author.service;

import com.api.bookstoremanager.author.dto.AuthorDTO;
import com.api.bookstoremanager.author.entity.Author;
import com.api.bookstoremanager.author.exception.AuthorAlreadyExistsException;
import com.api.bookstoremanager.author.exception.AuthorNotFoundException;
import com.api.bookstoremanager.author.mapper.AuthorMapper;
import com.api.bookstoremanager.author.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorServiceImpl implements AuthorService {

    public static final AuthorMapper mapper = AuthorMapper.INSTANCE;
    @Autowired
    private AuthorRepository repository;

    @Override
    public AuthorDTO create(AuthorDTO authorDTO) {
        verifyIfExists(authorDTO.getName());
        var authorToCreate = mapper.toModel(authorDTO);
        var createdAuthor = repository.save(authorToCreate);
        return mapper.toDTO(createdAuthor);
    }

    @Override
    public AuthorDTO findById(Long id) {
        var foundAuthor = verifyAndgetAuthor(id);
        return mapper.toDTO(foundAuthor);
    }


    @Override
    public List<AuthorDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public void delete(Long id) {
        verifyAndgetAuthor(id);
        repository.deleteById(id);
    }

    private void verifyIfExists(String authorName) {
        repository.findByName(authorName)
                .ifPresent(author -> {throw new AuthorAlreadyExistsException(authorName);});
    }
    private Author verifyAndgetAuthor(Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new AuthorNotFoundException(id));
    }
}
