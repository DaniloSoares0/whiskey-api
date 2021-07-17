package br.com.digitalinnovationone.whiskey_api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.digitalinnovationone.whiskey_api.dto.QuantityDTO;
import br.com.digitalinnovationone.whiskey_api.dto.WhiskeyDTO;
import br.com.digitalinnovationone.whiskey_api.exception.WhiskeyAlreadyRegisteredException;
import br.com.digitalinnovationone.whiskey_api.exception.WhiskeyNotFoundException;
import br.com.digitalinnovationone.whiskey_api.exception.WhiskeyStockExceededException;
import br.com.digitalinnovationone.whiskey_api.service.WhiskeyService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/whiskey")
public class WhiskeyController implements WhiskeyControllerDocs {

	private WhiskeyService whiskeyService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public WhiskeyDTO createWhiskey(@RequestBody @Valid WhiskeyDTO WhiskeyDTO) throws WhiskeyAlreadyRegisteredException {
		return whiskeyService.createWhiskey(WhiskeyDTO);
	}

	@GetMapping("/{name}")
	public WhiskeyDTO findByName(@PathVariable String name) throws WhiskeyNotFoundException {
		return whiskeyService.findByName(name);
	}

	@GetMapping
	public List<WhiskeyDTO> listWhiskeys() {
		return whiskeyService.listAll();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) throws WhiskeyNotFoundException {
		whiskeyService.deleteById(id);
	}

	@PatchMapping("/{id}/increment")
	public WhiskeyDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws WhiskeyNotFoundException, WhiskeyStockExceededException {
		return whiskeyService.increment(id, quantityDTO.getQuantity());
	}
}
