package br.com.digitalinnovationone.whiskey_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WhiskeyAlreadyRegisteredException extends Exception{

	private static final long serialVersionUID = -6066707721935408878L;

	public WhiskeyAlreadyRegisteredException(String whiskeyName) {
		super(String.format("Whiskey with name %s already registered in the system.", whiskeyName));
	}
}
