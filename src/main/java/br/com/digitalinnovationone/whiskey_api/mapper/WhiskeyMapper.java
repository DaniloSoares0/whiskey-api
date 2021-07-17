package br.com.digitalinnovationone.whiskey_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.digitalinnovationone.whiskey_api.dto.WhiskeyDTO;
import br.com.digitalinnovationone.whiskey_api.entity.Whiskey;

@Mapper(componentModel = "spring")
public interface WhiskeyMapper {

	WhiskeyMapper INSTANCE = Mappers.getMapper(WhiskeyMapper.class);

	Whiskey toModel(WhiskeyDTO whiskeyDTO);

	WhiskeyDTO toDTO(Whiskey whiskey);
}
