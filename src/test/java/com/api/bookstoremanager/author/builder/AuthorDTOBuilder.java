package com.api.bookstoremanager.author.builder;


import com.api.bookstoremanager.author.dto.AuthorDTO;
import lombok.Builder;

@Builder
public class AuthorDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Sharivan";

    @Builder.Default
    private final Integer age = 30;

    public AuthorDTO buildAuthorDTO(){
        return new AuthorDTO(id, name, age);
    }

}
