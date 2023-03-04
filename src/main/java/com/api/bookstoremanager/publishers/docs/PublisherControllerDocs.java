package com.api.bookstoremanager.publishers.docs;

import com.api.bookstoremanager.publishers.dto.PublisherDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Publishers", description = "Publishers management")
public interface PublisherControllerDocs {

  @Operation(description = "Publisher creation operation", security = @SecurityRequirement(name = "token"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Success Publisher creation"),
          @ApiResponse(responseCode = "400", description = "Missing required fields, wrong field range value or publisher already registered on system")
  })
  PublisherDTO create (PublisherDTO publisherDTO);

  @Operation(description = "Find Publisher by id operation", security = @SecurityRequirement(name = "token"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Success publisher found"),
          @ApiResponse(responseCode = "404", description = "Publisher not found error responseCode")
  })
  PublisherDTO findById(@PathVariable Long id);

  @Operation(description = "List all registered publishers", security = @SecurityRequirement(name = "token"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Return all registered publishers")
  })
  List<PublisherDTO> findAll();

  @Operation(description = "Publisher delete operation", security = @SecurityRequirement(name = "token"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Deleted publisher by id")
  })
  void delete(Long id);
}
