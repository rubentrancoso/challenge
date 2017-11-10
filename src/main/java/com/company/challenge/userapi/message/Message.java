package com.company.challenge.userapi.message;

public class Message {

	public static final String EMAIL_ALREADY_TAKEN = "E-mail já existente";
	public static final String INVALID_USERNAME_PASSWORD = "Usuário e/ou senha inválidos";
	
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
