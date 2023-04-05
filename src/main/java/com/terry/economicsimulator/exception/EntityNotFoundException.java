package com.terry.economicsimulator.exception;

public class EntityNotFoundException extends RuntimeException {
	public static EntityNotFoundException entityNotFound(String message) {
		if (message == null) return new EntityNotFoundException();
		return new EntityNotFoundException(message);
	}

	public EntityNotFoundException() {
		super("Requested entity not found");
	}

	public EntityNotFoundException(String message) {
		super(message);
	}

	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
