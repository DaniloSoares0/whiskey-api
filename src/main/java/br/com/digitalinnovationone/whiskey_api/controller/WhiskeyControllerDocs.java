package br.com.digitalinnovationone.whiskey_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import br.com.digitalinnovationone.whiskey_api.dto.WhiskeyDTO;
import br.com.digitalinnovationone.whiskey_api.exception.WhiskeyAlreadyRegisteredException;
import br.com.digitalinnovationone.whiskey_api.exception.WhiskeyNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Manages whikey stock")
public interface WhiskeyControllerDocs {

	@ApiOperation(value = "Whiskey creation operation")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Success Whiskey creation"),
			@ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
	})
	WhiskeyDTO createWhiskey(WhiskeyDTO WhiskeyDTO) throws WhiskeyAlreadyRegisteredException;

	@ApiOperation(value = "Returns Whiskey found by a given name")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success Whiskey found in the system"),
			@ApiResponse(code = 404, message = "Whiskey with given name not found.")
	})
	WhiskeyDTO findByName(@PathVariable String name) throws WhiskeyNotFoundException;

	@ApiOperation(value = "Returns a list of all Whiskeys registered in the system")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of all Whiskeys registered in the system"),
	})
	List<WhiskeyDTO> listWhiskeys();

	@ApiOperation(value = "Delete a Whiskey found by a given valid Id")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Success Whiskey deleted in the system"),
			@ApiResponse(code = 404, message = "Whiskey with given id not found.")
	})
	void deleteById(@PathVariable Long id) throws WhiskeyNotFoundException;
}
