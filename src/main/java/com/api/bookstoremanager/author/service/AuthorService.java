package com.api.bookstoremanager.author.service;

import com.api.bookstoremanager.author.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {

    AuthorDTO create(AuthorDTO authorDTO);

    AuthorDTO findById(Long id);

    List<AuthorDTO> findAll();

    public void delete(Long id);

}
