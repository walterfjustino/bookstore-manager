package com.api.bookstoremanager.author.docs;

import com.api.bookstoremanager.author.dto.AuthorDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Authors", description = "Authors management")
public interface AuthorControllerDocs {

    @Operation(description = "Author creation operation",
                security = @SecurityRequirement(name = "token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success author creation"),
            @ApiResponse(responseCode = "400", description = "Missing required fields, wrong field range value or author already registered on system")
    })
    AuthorDTO create(AuthorDTO authorDTO);

    @Operation(description = "Find author by id operation", security = @SecurityRequirement(name = "token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success author found"),
            @ApiResponse(responseCode = "404", description = "Author not found error responseCode")
    })
    AuthorDTO findById(@PathVariable Long id);

    @Operation(description = "List all registered authors", security = @SecurityRequirement(name = "token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return all registered authors")
    })
    List<AuthorDTO> findAll();
@Operation(description = "Author delete operation", security = @SecurityRequirement(name = "token"))
@ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Deleted author by id")
})
    void delete(Long id);
}
