package com.api.bookstoremanager.author.utils;

import com.api.bookstoremanager.author.dto.AuthorDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonCoversionUtils {

    public static String asJsonString(AuthorDTO expectedAuthorCreatedDTO) {
        try {
            var objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModules(new JavaTimeModule());
            return objectMapper.writeValueAsString(expectedAuthorCreatedDTO);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
