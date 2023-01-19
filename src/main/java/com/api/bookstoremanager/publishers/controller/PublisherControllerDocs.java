package com.api.bookstoremanager.publishers.controller;

import com.api.bookstoremanager.author.dto.AuthorDTO;
import com.api.bookstoremanager.publishers.dto.PublisherDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

@Api("Publishers management")
public interface PublisherControllerDocs {

  @ApiOperation(value = "Publisher creation operation")
  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "Success Publisher creation"),
          @ApiResponse(code = 400, message = "Missing required fields, wrong field range value or publisher already registered on system")
  })
  PublisherDTO create (PublisherDTO publisherDTO);

  @ApiOperation(value = "Find Publisher by id operation")
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success publisher found"),
          @ApiResponse(code = 404, message = "Publisher not found error code")
  })
  PublisherDTO findById(@PathVariable Long id);
}
