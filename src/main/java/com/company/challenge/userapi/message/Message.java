package com.company.challenge.userapi.message;

public class Message {

	public static final String EMAIL_ALREADY_TAKEN = "Email already exists";
	public static final String INVALID_USERNAME_PASSWORD = "Invalid username and/or password";
	public static final String NOT_AUTHORIZED = "Not authorized";
	public static final String RESOURCE_NOT_FOUND = "Resource not found";
	public static final String INVALID_SESSION = "Invalid session";

	private String message;
	
	public Message(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String toString() {
		return String.format("[message=%s]", this.message);
	}
	
	
}
