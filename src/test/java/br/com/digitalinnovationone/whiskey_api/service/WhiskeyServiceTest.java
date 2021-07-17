package br.com.digitalinnovationone.whiskey_api.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.digitalinnovationone.whiskey_api.builder.WhiskeyDTOBuilder;
import br.com.digitalinnovationone.whiskey_api.dto.WhiskeyDTO;
import br.com.digitalinnovationone.whiskey_api.entity.Whiskey;
import br.com.digitalinnovationone.whiskey_api.exception.WhiskeyAlreadyRegisteredException;
import br.com.digitalinnovationone.whiskey_api.exception.WhiskeyNotFoundException;
import br.com.digitalinnovationone.whiskey_api.exception.WhiskeyStockExceededException;
import br.com.digitalinnovationone.whiskey_api.mapper.WhiskeyMapper;
import br.com.digitalinnovationone.whiskey_api.repository.WhiskeyRepository;

@ExtendWith(MockitoExtension.class)
public class WhiskeyServiceTest {

    private static final long INVALID_Whiskey_ID = 1L;

    @Mock
    private WhiskeyRepository whiskeyRepository;
    
    private WhiskeyMapper whiskeyMapper = WhiskeyMapper.INSTANCE;
    
    @InjectMocks
    private WhiskeyService whiskeyService;

    @Test
    void whenWhiskeyInformedThenItShouldBeCreated() throws WhiskeyAlreadyRegisteredException {
    	
    	// given
        WhiskeyDTO expectedWhiskeyDTO = WhiskeyDTOBuilder.builder().build().toWhiskeyDTO();
        Whiskey expectedSavedWhiskey = whiskeyMapper.toModel(expectedWhiskeyDTO);

        // when
        when(whiskeyRepository.save(expectedSavedWhiskey)).thenReturn(expectedSavedWhiskey);

        //then
        WhiskeyDTO createdWhiskeyDTO = whiskeyService.createWhiskey(expectedWhiskeyDTO);

        assertThat(createdWhiskeyDTO.getId(), is(equalTo(expectedWhiskeyDTO.getId())));
        assertThat(createdWhiskeyDTO.getName(), is(equalTo(expectedWhiskeyDTO.getName())));
        assertThat(createdWhiskeyDTO.getQuantity(), is(equalTo(expectedWhiskeyDTO.getQuantity())));
    }
    
    @Test
    void whenAlreadyRegisteredwhiskeyInformedThenAnExceptionShouldBeThrown() {
        // given
        WhiskeyDTO expectedWhiskeyDTO = WhiskeyDTOBuilder.builder().build().toWhiskeyDTO();
        Whiskey duplicatedWhiskey = whiskeyMapper.toModel(expectedWhiskeyDTO);

        // when
        when(whiskeyRepository.findByName(expectedWhiskeyDTO.getName())).thenReturn(Optional.of(duplicatedWhiskey));

        // then
        assertThrows(WhiskeyAlreadyRegisteredException.class, () -> whiskeyService.createWhiskey(expectedWhiskeyDTO));
    }
  
    @Test
    void whenValidWhiskeyNameIsGivenThenReturnAWhiskey() throws WhiskeyNotFoundException {
        // given
        WhiskeyDTO expectedFoundWhiskeyDTO = WhiskeyDTOBuilder.builder().build().toWhiskeyDTO();
        Whiskey expectedFoundWhiskey = whiskeyMapper.toModel(expectedFoundWhiskeyDTO);

        // when
        when(whiskeyRepository.findByName(expectedFoundWhiskey.getName())).thenReturn(Optional.of(expectedFoundWhiskey));

        // then
        WhiskeyDTO foundWhiskeyDTO = whiskeyService.findByName(expectedFoundWhiskeyDTO.getName());

        assertThat(foundWhiskeyDTO, is(equalTo(expectedFoundWhiskeyDTO)));
    }
    
    @Test
    void whenNotRegisteredWhiskeyNameIsGivenThenThrowAnException() {
        // given
        WhiskeyDTO expectedFoundWhiskeyDTO = WhiskeyDTOBuilder.builder().build().toWhiskeyDTO();

        // when
        when(whiskeyRepository.findByName(expectedFoundWhiskeyDTO.getName())).thenReturn(Optional.empty());

        // then
        assertThrows(WhiskeyNotFoundException.class, () -> whiskeyService.findByName(expectedFoundWhiskeyDTO.getName()));
    }

    @Test
    void whenListWhiskeyIsCalledThenReturnAListOfWhiskeys() {
        // given
        WhiskeyDTO expectedFoundWhiskeyDTO = WhiskeyDTOBuilder.builder().build().toWhiskeyDTO();
        Whiskey expectedFoundWhiskey = whiskeyMapper.toModel(expectedFoundWhiskeyDTO);

        //when
        when(whiskeyRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundWhiskey));

        //then
        List<WhiskeyDTO> foundListWhiskeysDTO = whiskeyService.listAll();

        assertThat(foundListWhiskeysDTO, is(not(empty())));
        assertThat(foundListWhiskeysDTO.get(0), is(equalTo(expectedFoundWhiskeyDTO)));
    }

    @Test
    void whenListWhiskeyIsCalledThenReturnAnEmptyListOfWhiskeys() {
        //when
        when(whiskeyRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //then
        List<WhiskeyDTO> foundListWhiskeysDTO = whiskeyService.listAll();

        assertThat(foundListWhiskeysDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenAWhiskeyShouldBeDeleted() throws WhiskeyNotFoundException{
        // given
        WhiskeyDTO expectedDeletedWhiskeyDTO = WhiskeyDTOBuilder.builder().build().toWhiskeyDTO();
        Whiskey expectedDeletedWhiskey = whiskeyMapper.toModel(expectedDeletedWhiskeyDTO);

        // when
        when(whiskeyRepository.findById(expectedDeletedWhiskeyDTO.getId())).thenReturn(Optional.of(expectedDeletedWhiskey));
        doNothing().when(whiskeyRepository).deleteById(expectedDeletedWhiskeyDTO.getId());

        // then
        whiskeyService.deleteById(expectedDeletedWhiskeyDTO.getId());

        verify(whiskeyRepository, times(1)).findById(expectedDeletedWhiskeyDTO.getId());
        verify(whiskeyRepository, times(1)).deleteById(expectedDeletedWhiskeyDTO.getId());
    }

    @Test
    void whenIncrementIsCalledThenIncrementWhiskeyStock() throws WhiskeyNotFoundException, WhiskeyStockExceededException {
        //given
        WhiskeyDTO expectedWhiskeyDTO = WhiskeyDTOBuilder.builder().build().toWhiskeyDTO();
        Whiskey expectedWhiskey = whiskeyMapper.toModel(expectedWhiskeyDTO);

        //when
        when(whiskeyRepository.findById(expectedWhiskeyDTO.getId())).thenReturn(Optional.of(expectedWhiskey));
        when(whiskeyRepository.save(expectedWhiskey)).thenReturn(expectedWhiskey);

        int quantityToIncrement = 10;
        int expectedQuantityAfterIncrement = expectedWhiskeyDTO.getQuantity() + quantityToIncrement;

        // then
        WhiskeyDTO incrementedWhiskeyDTO = whiskeyService.increment(expectedWhiskeyDTO.getId(), quantityToIncrement);

        assertThat(expectedQuantityAfterIncrement, equalTo(incrementedWhiskeyDTO.getQuantity()));
        assertThat(expectedQuantityAfterIncrement, lessThan(expectedWhiskeyDTO.getMax()));
    }

    @Test
    void whenIncrementIsGreatherThanMaxThenThrowException() {
        WhiskeyDTO expectedWhiskeyDTO = WhiskeyDTOBuilder.builder().build().toWhiskeyDTO();
        Whiskey expectedWhiskey = whiskeyMapper.toModel(expectedWhiskeyDTO);

        when(whiskeyRepository.findById(expectedWhiskeyDTO.getId())).thenReturn(Optional.of(expectedWhiskey));

        int quantityToIncrement = 80;
        assertThrows(WhiskeyStockExceededException.class, () -> whiskeyService.increment(expectedWhiskeyDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementAfterSumIsGreatherThanMaxThenThrowException() {
        WhiskeyDTO expectedWhiskeyDTO = WhiskeyDTOBuilder.builder().build().toWhiskeyDTO();
        Whiskey expectedWhiskey = whiskeyMapper.toModel(expectedWhiskeyDTO);

        when(whiskeyRepository.findById(expectedWhiskeyDTO.getId())).thenReturn(Optional.of(expectedWhiskey));

        int quantityToIncrement = 45;
        assertThrows(WhiskeyStockExceededException.class, () -> whiskeyService.increment(expectedWhiskeyDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementIsCalledWithInvalidIdThenThrowException() {
        int quantityToIncrement = 10;

        when(whiskeyRepository.findById(INVALID_Whiskey_ID)).thenReturn(Optional.empty());

        assertThrows(WhiskeyNotFoundException.class, () -> whiskeyService.increment(INVALID_Whiskey_ID, quantityToIncrement));
    }
}
