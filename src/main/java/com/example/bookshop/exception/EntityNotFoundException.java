package com.example.bookshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
	public EntityNotFoundException(String entity, Object id) {
		super(String.format("%s with id=%s not found", entity, id));
	}
}
