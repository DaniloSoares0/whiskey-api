package br.com.digitalinnovationone.whiskey_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WhiskeyStockExceededException  extends Exception  {

	private static final long serialVersionUID = 7950551803355129350L;

	public WhiskeyStockExceededException(Long id, int quantityToIncrement) {
		super(String.format("Whiskey with %s ID to increment informed exceeds the max stock capacity: %s", id, quantityToIncrement));
	}
}
