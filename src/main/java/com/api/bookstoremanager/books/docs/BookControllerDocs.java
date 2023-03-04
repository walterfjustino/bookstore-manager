package com.api.bookstoremanager.books.docs;

import com.api.bookstoremanager.books.dto.BookRequestDTO;
import com.api.bookstoremanager.books.dto.BookResponseDTO;
import com.api.bookstoremanager.users.dto.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;

@Tag(name = "Books", description = "Books management")
public interface BookControllerDocs {

    @Operation(description = "Book creation operation", security = @SecurityRequirement(name = "token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success book creation"),
            @ApiResponse(responseCode = "400", description = "Missing required fields, wrong field range value or book already registered on system")
    })
    BookResponseDTO create(AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO);

    @Operation(description = "Book find by id and user operation", security = @SecurityRequirement(name = "token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success book found"),
            @ApiResponse(responseCode = "404", description = "Book not found error")
    })
    BookResponseDTO findByIdAndUser(AuthenticatedUser authenticatedUser, Long id);

    @Operation(description = "List all books by a specific authenticated user", security = @SecurityRequirement(name = "token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book list found by authenticated user informed")
    })
    List<BookResponseDTO> findAllByUser(AuthenticatedUser authenticatedUser);

    @Operation(description = "Book update operation", security = @SecurityRequirement(name = "token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book by user successfully updated"),
            @ApiResponse(responseCode = "404", description = "Book not found error"),
            @ApiResponse(responseCode = "400", description = "Missing required fields, wrong field range value or book already registered on system")
    })
    BookResponseDTO updateByUser(AuthenticatedUser authenticatedUser, Long id, BookRequestDTO bookRequestDTO);

    @Operation(description = "Book delete operation", security = @SecurityRequirement(name = "token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book by user successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Book not found error")
    })
    void deleteByIdAndUser(AuthenticatedUser authenticatedUser, Long id);
}