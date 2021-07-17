package br.com.digitalinnovationone.whiskey_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WhiskeyNotFoundException extends Exception {

	private static final long serialVersionUID = 873923879127713769L;

	public WhiskeyNotFoundException(String whiskeyName) {
		super(String.format("Whiskey with name %s not found in the system.", whiskeyName));
	}

	public WhiskeyNotFoundException(Long id) {
		super(String.format("Whiskey with id %s not found in the system.", id));
	}
}
