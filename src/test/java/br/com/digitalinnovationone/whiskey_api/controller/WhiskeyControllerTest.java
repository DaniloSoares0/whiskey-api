package br.com.digitalinnovationone.whiskey_api.controller;

import static br.com.digitalinnovationone.whiskey_api.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import br.com.digitalinnovationone.whiskey_api.builder.WhiskeyDTOBuilder;
import br.com.digitalinnovationone.whiskey_api.dto.QuantityDTO;
import br.com.digitalinnovationone.whiskey_api.dto.WhiskeyDTO;
import br.com.digitalinnovationone.whiskey_api.exception.WhiskeyNotFoundException;
import br.com.digitalinnovationone.whiskey_api.service.WhiskeyService;

@ExtendWith(MockitoExtension.class)
public class WhiskeyControllerTest {


	private static final String WHISKEY_API_URL_PATH = "/api/v1/whiskey";
	private static final long VALID_WHISKEY_ID = 1L;
	private static final long INVALID_WHISKEY_ID = 2l;
	private static final String WHISKEY_API_SUBPATH_INCREMENT_URL = "/increment";
	private static final String WHISKEY_API_SUBPATH_DECREMENT_URL = "/decrement";

	private MockMvc mockMvc;

	@Mock
	private WhiskeyService whiskeyService;

	@InjectMocks
	private WhiskeyController whiskeyController;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(whiskeyController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.setViewResolvers((s, locale) -> new MappingJackson2JsonView())
				.build();
	}

	@Test
	void whenPOSTIsCalledThenAWhiskeyIsCreated() throws Exception {
		// given
		WhiskeyDTO whiskeyDTO = WhiskeyDTOBuilder.builder().build().toWhiskeyDTO();

		// when
		when(whiskeyService.createWhiskey(whiskeyDTO)).thenReturn(whiskeyDTO);

		// then
		mockMvc.perform(post(WHISKEY_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(whiskeyDTO)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.name", is(whiskeyDTO.getName())))
		.andExpect(jsonPath("$.brand", is(whiskeyDTO.getBrand())))
		.andExpect(jsonPath("$.type", is(whiskeyDTO.getType().toString())));
	}

	@Test
	void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
		// given
		WhiskeyDTO whiskeyDTO = WhiskeyDTOBuilder.builder().build().toWhiskeyDTO();
		whiskeyDTO.setBrand(null);

		// then
		mockMvc.perform(post(WHISKEY_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(whiskeyDTO)))
		.andExpect(status().isBadRequest());
	}

	@Test
	void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
		// given
		WhiskeyDTO whiskeyDTO = WhiskeyDTOBuilder.builder().build().toWhiskeyDTO();

		//when
		when(whiskeyService.findByName(whiskeyDTO.getName())).thenReturn(whiskeyDTO);

		// then
		mockMvc.perform(MockMvcRequestBuilders.get(WHISKEY_API_URL_PATH + "/" + whiskeyDTO.getName())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", is(whiskeyDTO.getName())))
		.andExpect(jsonPath("$.brand", is(whiskeyDTO.getBrand())))
		.andExpect(jsonPath("$.type", is(whiskeyDTO.getType().toString())));
	}

	@Test
	void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception {
		// given
		WhiskeyDTO whiskeyDTO = WhiskeyDTOBuilder.builder().build().toWhiskeyDTO();

		//when
		when(whiskeyService.findByName(whiskeyDTO.getName())).thenThrow(WhiskeyNotFoundException.class);

		// then
		mockMvc.perform(MockMvcRequestBuilders.get(WHISKEY_API_URL_PATH + "/" + whiskeyDTO.getName())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}

	@Test
	void whenGETListWithWhiskeysIsCalledThenOkStatusIsReturned() throws Exception {
		// given
		WhiskeyDTO whiskeyDTO = WhiskeyDTOBuilder.builder().build().toWhiskeyDTO();

		//when
		when(whiskeyService.listAll()).thenReturn(Collections.singletonList(whiskeyDTO));

		// then
		mockMvc.perform(MockMvcRequestBuilders.get(WHISKEY_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].name", is(whiskeyDTO.getName())))
		.andExpect(jsonPath("$[0].brand", is(whiskeyDTO.getBrand())))
		.andExpect(jsonPath("$[0].type", is(whiskeyDTO.getType().toString())));
	}

	@Test
	void whenGETListWithoutWhiskeysIsCalledThenOkStatusIsReturned() throws Exception {
		// given
		WhiskeyDTO whiskeyDTO = WhiskeyDTOBuilder.builder().build().toWhiskeyDTO();

		//when
		when(whiskeyService.listAll()).thenReturn(Collections.singletonList(whiskeyDTO));

		// then
		mockMvc.perform(MockMvcRequestBuilders.get(WHISKEY_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	@Test
	void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
		// given
		WhiskeyDTO whiskeyDTO = WhiskeyDTOBuilder.builder().build().toWhiskeyDTO();

		//when
		doNothing().when(whiskeyService).deleteById(whiskeyDTO.getId());

		// then
		mockMvc.perform(MockMvcRequestBuilders.delete(WHISKEY_API_URL_PATH + "/" + whiskeyDTO.getId())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}

	@Test
	void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
		//when
		doThrow(WhiskeyNotFoundException.class).when(whiskeyService).deleteById(INVALID_WHISKEY_ID);

		// then
		mockMvc.perform(MockMvcRequestBuilders.delete(WHISKEY_API_URL_PATH + "/" + INVALID_WHISKEY_ID)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}

	@Test
	void whenPATCHIsCalledToIncrementDiscountThenOKstatusIsReturned() throws Exception {
		QuantityDTO quantityDTO = QuantityDTO.builder()
				.quantity(10)
				.build();

		WhiskeyDTO whiskeyDTO = WhiskeyDTOBuilder.builder().build().toWhiskeyDTO();
		whiskeyDTO.setQuantity(whiskeyDTO.getQuantity() + quantityDTO.getQuantity());

		when(whiskeyService.increment(VALID_WHISKEY_ID, quantityDTO.getQuantity())).thenReturn(whiskeyDTO);

		mockMvc.perform(MockMvcRequestBuilders.patch(WHISKEY_API_URL_PATH + "/" + VALID_WHISKEY_ID + WHISKEY_API_SUBPATH_INCREMENT_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(quantityDTO))).andExpect(status().isOk())
		.andExpect(jsonPath("$.name", is(whiskeyDTO.getName())))
		.andExpect(jsonPath("$.brand", is(whiskeyDTO.getBrand())))
		.andExpect(jsonPath("$.type", is(whiskeyDTO.getType().toString())))
		.andExpect(jsonPath("$.quantity", is(whiskeyDTO.getQuantity())));
	}
}
