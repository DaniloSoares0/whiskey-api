package br.com.digitalinnovationone.whiskey_api.builder;

import br.com.digitalinnovationone.whiskey_api.dto.WhiskeyDTO;
import br.com.digitalinnovationone.whiskey_api.enums.WhiskeyType;
import lombok.Builder;

@Builder
public class WhiskeyDTOBuilder {

	@Builder.Default
	private Long id = 1L;

	@Builder.Default
	private String name = "Bourbon";

	@Builder.Default
	private String brand = "Johnnie Walker";

	@Builder.Default
	private int max = 50;

	@Builder.Default
	private int quantity = 10;

	@Builder.Default
	private WhiskeyType type = WhiskeyType.BULLEIT;

	public WhiskeyDTO toWhiskeyDTO() {
		return new WhiskeyDTO(id,
				name,
				brand,
				max,
				quantity,
				type);
	}
}
