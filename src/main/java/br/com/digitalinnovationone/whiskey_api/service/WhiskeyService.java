package br.com.digitalinnovationone.whiskey_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digitalinnovationone.whiskey_api.dto.WhiskeyDTO;
import br.com.digitalinnovationone.whiskey_api.entity.Whiskey;
import br.com.digitalinnovationone.whiskey_api.exception.WhiskeyAlreadyRegisteredException;
import br.com.digitalinnovationone.whiskey_api.exception.WhiskeyNotFoundException;
import br.com.digitalinnovationone.whiskey_api.exception.WhiskeyStockExceededException;
import br.com.digitalinnovationone.whiskey_api.mapper.WhiskeyMapper;
import br.com.digitalinnovationone.whiskey_api.repository.WhiskeyRepository;

@Service
public class WhiskeyService {

	@Autowired
	private WhiskeyRepository whiskeyRepository;
	
	private WhiskeyMapper whiskeyMapper = WhiskeyMapper.INSTANCE;


	public WhiskeyDTO createWhiskey(WhiskeyDTO whiskeyDTO) throws WhiskeyAlreadyRegisteredException {
		verifyIfIsAlreadyRegistered(whiskeyDTO.getName());
		return whiskeyMapper.toDTO(whiskeyRepository.save(whiskeyMapper.toModel(whiskeyDTO)));
	}


	public WhiskeyDTO findByName(String name) throws WhiskeyNotFoundException {
		return whiskeyRepository.findByName(name).map(whiskeyMapper::toDTO)
				.orElseThrow(() -> new WhiskeyNotFoundException(name));
	}

	public List<WhiskeyDTO> listAll() {
		return whiskeyRepository.findAll()
				.stream()
				.map(whiskeyMapper::toDTO)
				.collect(Collectors.toList());
	}

	public void deleteById(Long id) throws WhiskeyNotFoundException {
		verifyIfExists(id);
		whiskeyRepository.deleteById(id);
	}

	private void verifyIfIsAlreadyRegistered(String name) throws WhiskeyAlreadyRegisteredException {
		if(whiskeyRepository.findByName(name).isPresent())
			throw new WhiskeyAlreadyRegisteredException(name);
	}

	private Whiskey verifyIfExists(Long id) throws WhiskeyNotFoundException {
		return whiskeyRepository.findById(id)
				.orElseThrow(() -> new WhiskeyNotFoundException(id));
	}

	public WhiskeyDTO increment(Long id, int quantityToIncrement) throws WhiskeyNotFoundException, WhiskeyStockExceededException {
		Whiskey WhiskeyToIncrementStock = verifyIfExists(id);
		int quantityAfterIncrement = quantityToIncrement + WhiskeyToIncrementStock.getQuantity();
		
		if (quantityAfterIncrement <= WhiskeyToIncrementStock.getMax()) {
			WhiskeyToIncrementStock.setQuantity(WhiskeyToIncrementStock.getQuantity() + quantityToIncrement);
			Whiskey incrementedWhiskeyStock = whiskeyRepository.save(WhiskeyToIncrementStock);
			return whiskeyMapper.toDTO(incrementedWhiskeyStock);
		}
		
		throw new WhiskeyStockExceededException(id, quantityToIncrement);
	}


}
